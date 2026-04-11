package dynastxu.noita.datagen

import dynastxu.noita.Noita
import dynastxu.noita.item.ModItems
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class ModItemModelProvider(output: PackOutput, modid: String, existingFileHelper: ExistingFileHelper) :
    ItemModelProvider(output, modid, existingFileHelper) {
    override fun registerModels() {
        withExistingParent(ModItems.WAND.id.path, ResourceLocation.parse("item/handheld"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Noita.ID, "item/wand"))
        withExistingParent(ModItems.STARTER_WAND.id.path, ResourceLocation.parse("item/handheld"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Noita.ID, "item/starter_wand"))
    }
}
