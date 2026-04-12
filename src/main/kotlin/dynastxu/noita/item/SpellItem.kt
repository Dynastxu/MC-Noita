package dynastxu.noita.item

import dynastxu.noita.data.Spell
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class SpellItem(properties: Properties, val spell: Spell) : Item(properties) {
    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        tooltipComponents.add(Component.literal("§b法力消耗: §f${spell.manaDrain}"))
    }
}
