package dynastxu.noita.item

import dynastxu.noita.component.ModDataComponents.WAND_DATA
import dynastxu.noita.component.WandData
import dynastxu.noita.utils.MathHelper.getRandomFloatInRange
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack

class StarterWand(properties: Properties) : Wand(properties) {
    override fun getDefaultInstance(): ItemStack {
        val stack = ItemStack(this)

        // 使用 Minecraft 的随机源 (同步种子，适合客户端显示)
        val random = RandomSource.create()

        // 设置自定义数据组件
        stack.set(WAND_DATA.get(), WandData(
            false,
            1,
            getRandomFloatInRange(Pair(0.15f, 0.25f)) * 60,
            getRandomFloatInRange(Pair(0.33f, 0.47f)) * 60,
            random.nextIntBetweenInclusive(80, 130),
            random.nextIntBetweenInclusive(25, 40),
            random.nextIntBetweenInclusive(2, 3),
            0f,
            listOf(),
            1f
        ))

        return stack
    }
}
