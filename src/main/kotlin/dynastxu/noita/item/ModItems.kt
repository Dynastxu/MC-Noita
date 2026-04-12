package dynastxu.noita.item

import dynastxu.noita.Noita
import dynastxu.noita.data.Spells
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModItems {
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(Noita.ID)

    // 法杖
    val WAND: DeferredHolder<Item, Wand> = ITEMS.registerItem("wand") { properties -> Wand(properties.stacksTo(1)) }
    val STARTER_WAND: DeferredHolder<Item, StarterWand> =
        ITEMS.registerItem("starter_wand") { properties -> StarterWand(properties.stacksTo(1)) }

    // 法术
    val RUBBER_BALL: DeferredHolder<Item, SpellItem> =
        ITEMS.registerItem("spell_rubber_ball") { properties -> SpellItem(properties.stacksTo(1), Spells.rubberBall) }

    fun register() {
    }
}
