package dynastxu.noita.datagen

import dynastxu.noita.Noita
import dynastxu.noita.item.ModItems
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.registries.DeferredHolder

class ModItemModelProvider(output: PackOutput, modid: String, existingFileHelper: ExistingFileHelper) :
    ItemModelProvider(output, modid, existingFileHelper) {
    override fun registerModels() {
        registerHandheldModel(ModItems.WAND, "item/wand")
        registerHandheldModel(ModItems.STARTER_WAND, "item/starter_wand")
        registerHandheldModel(ModItems.RUBBER_BALL, "item/spell_rubber_ball")
    }

    fun <T : Item> registerHandheldModel(item: DeferredHolder<Item, T>, namespaceAndPath: String) {
        withExistingParent(item.id.path, ResourceLocation.parse("item/handheld"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Noita.ID, namespaceAndPath))
    }
}
