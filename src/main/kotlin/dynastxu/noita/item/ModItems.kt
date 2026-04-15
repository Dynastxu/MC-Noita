package dynastxu.noita.item

import dynastxu.noita.Noita
import dynastxu.noita.block.ModBlocks
import dynastxu.noita.data.Spells
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModItems {
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(Noita.ID)
    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Noita.ID)

    // 创造模式标签页
    val NOITA_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> = CREATIVE_MODE_TABS.register("noita_tab") { ->
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.noita"))
            .icon { ItemStack(WAND.get()) }
            .displayItems { _, output ->
                // 法杖
                output.accept(WAND.get())
                output.accept(STARTER_WAND.get())

                // 法术
                output.accept(RUBBER_BALL.get())

                // 方块
                output.accept(WAND_WORKBENCH_ITEM.get())
            }
            .build()
    }

    // 法杖
    val WAND: DeferredHolder<Item, Wand> = ITEMS.registerItem("wand") { properties -> Wand(properties.stacksTo(1)) }
    val STARTER_WAND: DeferredHolder<Item, StarterWand> =
        ITEMS.registerItem("starter_wand") { properties -> StarterWand(properties.stacksTo(1)) }

    // 法术
    val RUBBER_BALL: DeferredHolder<Item, SpellItem> =
        ITEMS.registerItem("spell_rubber_ball") { properties -> SpellItem(properties.stacksTo(1), Spells.rubberBall) }

    // 方块
    val WAND_WORKBENCH_ITEM: DeferredHolder<Item, BlockItem> = ITEMS.registerSimpleBlockItem(ModBlocks.WAND_WORKBENCH)

    fun register() {
    }
}
