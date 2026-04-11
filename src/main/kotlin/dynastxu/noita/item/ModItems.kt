package dynastxu.noita.item

import dynastxu.noita.Noita
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModItems {
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(Noita.ID)


    val WAND: DeferredHolder<Item, Wand> = ITEMS.registerItem("wand") { properties ->
        Wand(properties.stacksTo(1))
    }

    val STARTER_WAND: DeferredHolder<Item, StarterWand> = ITEMS.registerItem("starter_wand") { properties ->
        StarterWand(properties.stacksTo(1))
    }

    fun register() {
    }
}
