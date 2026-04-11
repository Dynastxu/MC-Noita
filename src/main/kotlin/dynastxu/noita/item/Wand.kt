package dynastxu.noita.item

import com.mojang.logging.LogUtils
import dynastxu.noita.component.ModDataComponents.WAND_DATA
import dynastxu.noita.component.WandData
import dynastxu.noita.data.Spells
import dynastxu.noita.entity.ModEntities.RUBBER_BALL
import dynastxu.noita.entity.RubberBallEntity
import dynastxu.noita.utils.MathHelper.getRandomDoubleInRange
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import org.slf4j.Logger

open class Wand(properties: Properties) : Item(properties) {
    // 当物品堆叠被创建时，填入随机数据
    override fun getDefaultInstance(): ItemStack {
        val stack = ItemStack(this)

        // 使用 Minecraft 的随机源 (同步种子，适合客户端显示)
        val random = RandomSource.create()

        // 设置自定义数据组件
        stack.set<WandData?>(
            WAND_DATA.get(), WandData(
                random.nextBoolean(),
                random.nextIntBetweenInclusive(1, 3),
                random.nextFloat(),
                random.nextFloat(),
                random.nextIntBetweenInclusive(100, 200),
                random.nextIntBetweenInclusive(1, 20),
                random.nextIntBetweenInclusive(1, 20),
                0f,
                mutableListOf(),
                1f
            )
        )

        return stack
    }

    // 定义最大使用时长 (单位：tick，72000 代表近乎无限长按)
    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int {
        return 72000
    }

    // 右键开始使用时触发
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack?> {
        val stack = player.getItemInHand(hand)
        player.startUsingItem(hand)
        return InteractionResultHolder.consume<ItemStack?>(stack)
    }

    // 每 tick 执行的核心函数 (客户端和服务端均会调用)
    override fun onUseTick(level: Level, user: LivingEntity, stack: ItemStack, remainingUseTicks: Int) {
        if (level.isClientSide) {
            // 客户端特效或声音
        } else {
            // 服务端逻辑：这里放置每 tick 执行的内容

            // 创建实体实例

            val eyePos = user.getEyePosition(1.0f)
            val ball = RubberBallEntity(
                RUBBER_BALL.get(),
                eyePos.x,
                eyePos.y,
                eyePos.z,
                level,
                Spells.rubberBall
            )

            // 设置发射方向和速度
            val lookVec = user.lookAngle
            ball.shoot(
                lookVec.x,
                lookVec.y,
                lookVec.z,
                getRandomDoubleInRange(
                    Spells.rubberBall.initialSpeed.first.toMcDistance(),
                    Spells.rubberBall.initialSpeed.second.toMcDistance()
                ).toFloat(),
                0.0f
            )

            // 设置发射者
            ball.owner = user

            // 添加到世界
            level.addFreshEntity(ball)
        }
    }

    companion object {
        private val LOGGER: Logger = LogUtils.getLogger()
    }
}
