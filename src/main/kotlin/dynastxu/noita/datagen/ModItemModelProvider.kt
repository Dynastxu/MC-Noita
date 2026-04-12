package dynastxu.noita.datagen

import dynastxu.noita.Noita
import dynastxu.noita.data.Spell
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
        registerSpellModel(ModItems.RUBBER_BALL, "item/spell_rubber_ball", Spell.Type.Projectile)
    }

    fun <T : Item> registerHandheldModel(item: DeferredHolder<Item, T>, namespaceAndPath: String) {
        registerModel(item, "item/handheld", "layer0", namespaceAndPath)
    }

    fun <T : Item> registerGeneratedModel(item: DeferredHolder<Item, T>, namespaceAndPath: String) {
        withExistingParent(item.id.path, ResourceLocation.parse("item/generated"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Noita.ID, namespaceAndPath))
    }

    fun <T : Item> registerSpellModel(item: DeferredHolder<Item, T>, foregroundTexture: String, spellType: Spell.Type) {
        withExistingParent(item.id.path, ResourceLocation.parse("item/generated"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(Noita.ID, when(spellType) {
                Spell.Type.Projectile -> "item/projectile_bg"
            }))
            .texture("layer1", ResourceLocation.fromNamespaceAndPath(Noita.ID, foregroundTexture))
    }

    fun <T : Item> registerModel(item: DeferredHolder<Item, T>, resourceLocation: String, textureKey: String, namespaceAndPath: String) {
        withExistingParent(item.id.path, ResourceLocation.parse(resourceLocation))
            .texture(textureKey, ResourceLocation.fromNamespaceAndPath(Noita.ID, namespaceAndPath))
    }
}
