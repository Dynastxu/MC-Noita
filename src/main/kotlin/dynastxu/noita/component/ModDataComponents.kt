package dynastxu.noita.component

import dynastxu.noita.Noita
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModDataComponents {
    val DATA_COMPONENTS: DeferredRegister.DataComponents =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Noita.ID)

    val WAND_DATA: DeferredHolder<DataComponentType<*>, DataComponentType<WandData>> =
        DATA_COMPONENTS.registerComponentType(
            "wand_data"
        ) { builder: DataComponentType.Builder<WandData> ->
            builder
                .persistent(WandData.CODEC)
                .networkSynchronized(WandData.STREAM_CODEC)
        }

    fun register() {
    }
}
