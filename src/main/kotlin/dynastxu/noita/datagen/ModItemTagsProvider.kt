package dynastxu.noita.datagen

import dynastxu.noita.Noita
import dynastxu.noita.item.ModItems
import dynastxu.noita.tag.ModItemTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

class ModItemTagsProvider(
    output: PackOutput,
    lookupProvider: CompletableFuture<HolderLookup.Provider>,
    blockTags: CompletableFuture<TagLookup<Block>>,
    existingFileHelper: ExistingFileHelper?
) : ItemTagsProvider(output, lookupProvider, blockTags, Noita.ID, existingFileHelper) {
    override fun addTags(provider: HolderLookup.Provider) {
        // 法杖标签
        tag(ModItemTags.WAND)
            .add(ModItems.WAND.get())
            .add(ModItems.STARTER_WAND.get())

        // 法术标签
        tag(ModItemTags.SPELL)
            .add(ModItems.RUBBER_BALL.get())

        tag(ModItemTags.SPELL_PROJECTILE)
            .add(ModItems.RUBBER_BALL.get())
    }
}
