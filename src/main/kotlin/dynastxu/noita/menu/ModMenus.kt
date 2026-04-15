package dynastxu.noita.menu

import dynastxu.noita.Noita
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModMenus {
    val MENUS: DeferredRegister<MenuType<*>> = DeferredRegister.create(Registries.MENU, Noita.ID)

    val WAND_WORKBENCH_MENU: DeferredHolder<MenuType<*>, MenuType<WandWorkbenchMenu>> =
        MENUS.register("wand_workbench") { ->
            MenuType(
                { containerId: Int, inventory: Inventory ->
                    WandWorkbenchMenu(containerId, inventory, net.minecraft.world.inventory.ContainerLevelAccess.NULL)
                },
                FeatureFlags.VANILLA_SET
            )
        }

    fun register() {
    }
}
