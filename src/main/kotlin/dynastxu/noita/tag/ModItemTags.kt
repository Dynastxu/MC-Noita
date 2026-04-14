package dynastxu.noita.tag

import dynastxu.noita.Noita
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object ModItemTags {
    val WAND: TagKey<Item> = TagKey.create(
        net.minecraft.core.registries.Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Noita.ID, "wand")
    )

    val SPELL: TagKey<Item> = TagKey.create(
        net.minecraft.core.registries.Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Noita.ID, "spell")
    )

    val SPELL_PROJECTILE: TagKey<Item> = TagKey.create(
        net.minecraft.core.registries.Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath(Noita.ID, "spell_projectile")
    )
}
