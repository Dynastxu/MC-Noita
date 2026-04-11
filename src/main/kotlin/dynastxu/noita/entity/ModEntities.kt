package dynastxu.noita.entity

import dynastxu.noita.Noita
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.Level
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModEntities {
    val ENTITY_TYPES: DeferredRegister<EntityType<*>?> =
        DeferredRegister.create(Registries.ENTITY_TYPE, Noita.ID)

    val RUBBER_BALL: DeferredHolder<EntityType<*>?, EntityType<RubberBallEntity?>> =
        ENTITY_TYPES.register(
            "rubber_ball",
            Supplier {
                EntityType.Builder.of<RubberBallEntity?>({ rubberBallEntityEntityType: EntityType<RubberBallEntity?>?, level: Level? ->
                    RubberBallEntity(
                        rubberBallEntityEntityType!!,
                        level!!
                    )
                }, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(1) // TODO 过于频繁，试使客户端预测弹道以解决同步问题
                    .build("rubber_ball")
            }
        )

    fun register() {
    }
}
