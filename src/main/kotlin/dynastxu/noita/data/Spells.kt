package dynastxu.noita.data

import dynastxu.noita.utils.UnitConversion.*

object Spells {
    @JvmField
    val rubberBall = Spell(
        type = Spell.Type.Projectile,
        manaDrain = 5,
        castDelay = NoitaFrame(-2),
        spreadModification = -1f,
        projectile = 3,
        explosionRadius = NoitaPx(1),
        lifetime = Pair(NoitaFrame(743), NoitaFrame(757)),
        initialSpeed = Pair(NoitaPx(650), NoitaPx(750)),
        diesIfSpeedFallsBelow = NoitaPx(5),
        bounces = 10,
        spread = 0.6f,
        gravity = NoitaGravity(250),
        frictionAir = NoitaFriction(0.6f)
    )
}
