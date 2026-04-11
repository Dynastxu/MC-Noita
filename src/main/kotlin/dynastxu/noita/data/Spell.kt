package dynastxu.noita.data

import dynastxu.noita.utils.UnitConversion.NoitaFrame
import dynastxu.noita.utils.UnitConversion.NoitaPx
import dynastxu.noita.utils.UnitConversion.NoitaGravity
import dynastxu.noita.utils.UnitConversion.NoitaFriction
import dynastxu.noita.utils.UnitConversion.NoitaMass

/**
 * 法术
 *
 * @param type                  法术类型
 * @param manaDrain             法力消耗
 * @param castDelay             释放延迟
 * @param spreadModification   散射角度修正
 * @param projectile            投射物伤害
 * @param explosionRadius       爆炸半径
 * @param lifetime              存在时间
 * @param initialSpeed          初始速度（ px/s ）
 * @param diesIfSpeedFallsBelow 销毁速度阈值（ px/s ）
 * @param bounces               弹跳次数
 * @param spread                散射角度
 * @param gravity               重力
 * @param frictionAir          摩擦力（空气）
 * @param mass                  质量
 * @param basePrice             基础价值
 * @param tags                  标签
 * @param draws                 抽取数
 * @param selfHarm              自伤
 */
data class Spell(
    val type: Type,
    val manaDrain: Int = 0,
    val castDelay: NoitaFrame = NoitaFrame(0),
    val spreadModification: Float = 0f,
    val projectile: Int = 0,
    val explosionRadius: NoitaPx = NoitaPx(0),
    val lifetime: Pair<NoitaFrame, NoitaFrame> = Pair(NoitaFrame(0), NoitaFrame(0)),
    val initialSpeed: Pair<NoitaPx, NoitaPx> = Pair(NoitaPx(0), NoitaPx(0)),
    val diesIfSpeedFallsBelow: NoitaPx = NoitaPx(0),
    val bounces: Int = 0,
    val spread: Float = 0f,
    val gravity: NoitaGravity = NoitaGravity(0),
    val frictionAir: NoitaFriction = NoitaFriction(0f),
    val mass: NoitaMass = NoitaMass(0f),
    val basePrice: Int = 0,
    val tags: List<Tag> = listOf(),
    val draws: Int = 0,
    val selfHarm: Boolean = true
) {
    enum class Type {
        Projectile
    }
    enum class Tag {
        Starter,
        SpeedDamage
    }
}
