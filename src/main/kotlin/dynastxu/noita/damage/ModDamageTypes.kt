package dynastxu.noita.damage

import dynastxu.noita.Noita
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.damagesource.DamageType

object ModDamageTypes {
    val SLICE_DAMAGE = registerDamageType("slice_damage")

    val TOXIC_DAMAGE = registerDamageType("toxic_damage")

    val DRILL_DAMAGE = registerDamageType("drill_damage")

    val HOLY_DAMAGE = registerDamageType("holy_damage")

    private fun registerDamageType(path: String) : ResourceKey<DamageType> {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Noita.ID, path))
    }

    fun register() {
    }
}
