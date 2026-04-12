package dynastxu.noita.entity

import com.mojang.logging.LogUtils
import dynastxu.noita.data.Spell
import dynastxu.noita.utils.NbtHelper
import dynastxu.noita.utils.UnitConversion
import dynastxu.noita.utils.UnitConversion.NoitaFrame
import dynastxu.noita.utils.UnitConversion.NoitaFriction
import dynastxu.noita.utils.UnitConversion.NoitaGravity
import dynastxu.noita.utils.UnitConversion.NoitaPx
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.ThrowableProjectile
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import org.slf4j.Logger
import kotlin.math.atan2

abstract class NoitaThrowableProjectile : ThrowableProjectile {
    protected var selfHarm: Boolean = true // 自伤
    protected var projectile: Int = 0 // 伤害
    protected var explosionRadius: NoitaPx = NoitaPx(0) // 爆炸半径
    protected var lifetime: NoitaFrame = NoitaFrame(0) // 生存时间
    protected var initialSpeed: NoitaPx = NoitaPx(0) // 初始速度
    protected var diesIfSpeedFallsBelow: NoitaPx = NoitaPx(0) // 速度低于多少时死亡
    protected var bounces: Int = 0 // 弹跳次数
    protected var gravity: NoitaGravity = NoitaGravity(0) // 重力
    protected var frictionAir: NoitaFriction = NoitaFriction(0f) // 空气阻力
    protected var nextSpells: MutableList<Spell>? = null // 触发法术链
    protected var bounceSpells: MutableList<Spell>? = null // 弹跳触发修正

    protected constructor(
        entityType: EntityType<out ThrowableProjectile?>,
        x: Double,
        y: Double,
        z: Double,
        level: Level,
        spell: Spell
    ) : super(entityType, x, y, z, level) {
        selfHarm = spell.selfHarm
        projectile = spell.projectile
        explosionRadius = spell.explosionRadius
        lifetime = NoitaFrame(
            level().random.nextIntBetweenInclusive(
                spell.lifetime.first.value,
                spell.lifetime.second.value
            )
        )
        initialSpeed = NoitaPx(
            level.random.nextIntBetweenInclusive(
                spell.initialSpeed.first.value,
                spell.initialSpeed.second.value
            )
        )
        diesIfSpeedFallsBelow = spell.diesIfSpeedFallsBelow
        bounces = spell.bounces
        gravity = spell.gravity
        frictionAir = spell.frictionAir
    }

    protected constructor(rubberBallEntityEntityType: EntityType<RubberBallEntity?>, level: Level) : super(
        rubberBallEntityEntityType,
        level
    )

    override fun getDefaultGravity(): Double {
        return gravity.toMcG()
    }

    protected fun bounce(motion: Vec3, normal: Vec3): Vec3 {
        // 计算反弹向量: R = V - 2 * (V · N) * N
        val dot = motion.dot(normal)
        val reflected = motion.subtract(normal.scale(2 * dot))
        // 应用反弹衰减
        return reflected.scale(DEFAULT_BOUNCE_FACTOR)
    }

    override fun onHitBlock(result: BlockHitResult) {
        super.onHitBlock(result)

        val motion = this.deltaMovement
        val normal = Vec3.atLowerCornerOf(result.direction.normal)

        val canBounce = bounces > 0
        if (canBounce) {
            // 反弹
            val reflected = bounce(motion, normal)
            // 设置新的运动向量
            this.deltaMovement = reflected
            // 更新朝向，使其“面朝”运动方向
            this.yRot = Math.toDegrees(atan2(reflected.z, reflected.x)).toFloat()
            bounces--
            onBounced(result)
        } else {
            if (normal.y != 0.0) {
                this.deltaMovement = Vec3(motion.x, 0.0, motion.z)
            } else {
                val newMotion = motion.subtract(normal.scale(motion.dot(normal)))
                this.deltaMovement = newMotion
            }
        }

        val pushDistance = 0.01
        val pushDirection = motion.normalize().scale(-1.0)
        this.setPos(
            this.x + pushDirection.x * pushDistance,
            this.y + pushDirection.y * pushDistance,
            this.z + pushDirection.z * pushDistance
        )
    }

    override fun onHitEntity(result: EntityHitResult) {
        super.onHitEntity(result)
    }

    protected open fun onBounced(result: HitResult) {
    }

    override fun tick() {
        super.tick()

        // 生命周期检查: 超过最大存活时间则移除
        if (lifetime.value != 0 && this.tickCount > lifetime.toTick()) {
            onDiscard()
            this.discard()
            return
        }

        // 检查速度
        val speedPerTick: Double = diesIfSpeedFallsBelow.toMcDistance() / 20 * UnitConversion.BASE_TIME // 换算成米每秒
        if (diesIfSpeedFallsBelow.value != 0 && this.deltaMovement.lengthSqr() < speedPerTick * speedPerTick) {
            onDiscard()
            this.discard()
            return
        }

        // 应用摩擦
        val motion = this.deltaMovement
        val currentSpeed = motion.length()

        if (currentSpeed > 0) {
            val newSpeed: Double = frictionAir.nextTickSpeed(currentSpeed)
            val newMotion = motion.normalize().scale(newSpeed)
            this.deltaMovement = newMotion
        }
    }

    protected fun onDiscard() {
    }

    public override fun readAdditionalSaveData(tag: CompoundTag) {
        super.readAdditionalSaveData(tag)

        this.selfHarm = tag.getBoolean("selfHarm")
        this.projectile = tag.getInt("projectile")
        this.explosionRadius = NoitaPx(tag.getInt("explosionRadius"))
        this.lifetime = NoitaFrame(tag.getInt("lifetime"))
        this.initialSpeed = NoitaPx(tag.getInt("initialSpeed"))
        this.diesIfSpeedFallsBelow = NoitaPx(tag.getInt("diesIfSpeedFallsBelow"))
        this.bounces = tag.getInt("bounces")
        this.gravity = NoitaGravity(tag.getInt("gravity"))
        this.frictionAir = NoitaFriction(tag.getFloat("friction_air"))

        this.nextSpells = NbtHelper.getSpellsList(tag, "nextSpells")
        this.bounceSpells = NbtHelper.getSpellsList(tag, "bounceSpells")
    }

    public override fun addAdditionalSaveData(tag: CompoundTag) {
        super.addAdditionalSaveData(tag)

        tag.putBoolean("selfHarm", this.selfHarm)
        tag.putInt("projectile", this.projectile)
        tag.putInt("explosionRadius", this.explosionRadius.value)
        tag.putInt("lifetime", this.lifetime.value)
        tag.putInt("initialSpeed", this.initialSpeed.value)
        tag.putInt("diesIfSpeedFallsBelow", this.diesIfSpeedFallsBelow.value)
        tag.putInt("bounces", this.bounces)
        tag.putInt("gravity", this.gravity.value)
        tag.putFloat("friction_air", this.frictionAir.value)

        NbtHelper.setSpellsList(tag, "nextSpells", this.nextSpells)
        NbtHelper.setSpellsList(tag, "bounceSpells", this.bounceSpells)
    }

    companion object {
        private val LOGGER: Logger = LogUtils.getLogger()
        private const val DEFAULT_BOUNCE_FACTOR = 0.9 // 每次反弹保留的速度
    }
}
