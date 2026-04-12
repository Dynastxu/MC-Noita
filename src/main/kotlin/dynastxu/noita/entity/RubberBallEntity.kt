package dynastxu.noita.entity

import dynastxu.noita.data.Spell
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.ThrowableProjectile
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import kotlin.math.abs

/**
 * [Wiki](https://noita.wiki.gg/wiki/Bouncing_Burst)
 * [中文 Wiki](https://noita.wiki.gg/zh/wiki/%E5%BC%B9%E8%B7%B3%E7%88%86%E5%8F%91)
 */
class RubberBallEntity : NoitaThrowableProjectile {
    constructor(
        entityType: EntityType<out ThrowableProjectile?>,
        x: Double,
        y: Double,
        z: Double,
        level: Level,
        spell: Spell
    ) : super(entityType, x, y, z, level, spell)

    constructor(rubberBallEntityEntityType: EntityType<RubberBallEntity?>, level: Level) : super(
        rubberBallEntityEntityType,
        level
    )

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
    }

    override fun onBounced(result: HitResult) {
        if (!level().isClientSide && level() is ServerLevel) {
            val serverLevel = level() as ServerLevel

            val packet = ClientboundLevelParticlesPacket(
                ParticleTypes.CRIT,
                true,
                result.location.x,
                result.location.y,
                result.location.z,
                0.3f,
                0.3f,
                0.3f,
                0.1f,
                20
            )

            serverLevel.players().forEach { player ->
                player.connection.send(packet)
            }
        }
    }
}
