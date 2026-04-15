package dynastxu.noita.block

import dynastxu.noita.Noita
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModBlocks {
    val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(Noita.ID)

    val WAND_WORKBENCH: DeferredHolder<Block, WandWorkbenchBlock> = BLOCKS.register("wand_workbench") { ->
        WandWorkbenchBlock(BlockBehaviour.Properties.of()
            .strength(2.5f)
            .sound(SoundType.WOOD))
    }

    fun register() {
    }
}
