package dynastxu.noita.item

import dynastxu.noita.component.ModDataComponents.WAND_DATA
import dynastxu.noita.component.WandData
import dynastxu.noita.utils.MathHelper.nextRandomFloat
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack

class StarterWand(properties: Properties) : Wand(properties) {
    override fun initializeWandData(stack: ItemStack) {
        if (stack.get(WAND_DATA.get()) != null) return

        val random = RandomSource.create()
        stack.set(WAND_DATA.get(), WandData(
            false,
            1,
            nextRandomFloat(0.15f, 0.25f) * 60,
            nextRandomFloat(0.33f, 0.47f) * 60,
            random.nextIntBetweenInclusive(80, 130),
            random.nextIntBetweenInclusive(25, 40),
            random.nextIntBetweenInclusive(2, 3),
            0f,
            listOf(),
            1f
        ))
    }
}
