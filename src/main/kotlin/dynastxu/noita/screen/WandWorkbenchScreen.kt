package dynastxu.noita.screen

import com.mojang.blaze3d.systems.RenderSystem
import dynastxu.noita.menu.WandWorkbenchMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class WandWorkbenchScreen(
    menu: WandWorkbenchMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<WandWorkbenchMenu>(menu, playerInventory, title) {
    private companion object {
        val TEXTURE: ResourceLocation = ResourceLocation.withDefaultNamespace("textures/gui/container/dispenser.png")
        val SLOT_TEXTURE: ResourceLocation = ResourceLocation.withDefaultNamespace("container/slot")
    }

    init {
        imageWidth = 176
        imageHeight = 166
        inventoryLabelY = imageHeight - 94
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
//        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight)
        menu.slots.forEach { slot ->
            if (slot.isActive) guiGraphics.blitSprite(SLOT_TEXTURE, leftPos + slot.x - 1, topPos + slot.y - 1, 18, 18)
        }
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick)
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        renderTooltip(guiGraphics, mouseX, mouseY)
    }
}
