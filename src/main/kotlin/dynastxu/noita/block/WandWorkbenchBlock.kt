package dynastxu.noita.block

import dynastxu.noita.menu.WandWorkbenchMenu
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class WandWorkbenchBlock(properties: Properties) : Block(properties) {
    override fun useWithoutItem(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hitResult: BlockHitResult
    ): InteractionResult {
        if (level.isClientSide) return InteractionResult.SUCCESS

        player.openMenu(state.getMenuProvider(level, pos))
        return InteractionResult.CONSUME
    }

    override fun getMenuProvider(state: BlockState, level: Level, pos: BlockPos): MenuProvider {
        return object : MenuProvider {
            override fun getDisplayName(): Component {
                return Component.translatable("block.noita.wand_workbench")
            }

            override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu =
                WandWorkbenchMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos))
        }
    }
}
