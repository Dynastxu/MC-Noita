package dynastxu.noita

import dynastxu.noita.client.renderer.RubberBallRenderer
import dynastxu.noita.component.ModDataComponents
import dynastxu.noita.damage.ModDamageTypes
import dynastxu.noita.entity.ModEntities
import dynastxu.noita.item.ModItems
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.runForDist

/**
 * Main mod class.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(Noita.ID)
@EventBusSubscriber
object Noita {
    const val ID = "noita"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        MOD_BUS.addListener { event: RegisterRenderers? -> this.registerRenderers(event!!) }

        ModItems.ITEMS.register(MOD_BUS)
        ModItems.CREATIVE_MODE_TABS.register(MOD_BUS)
        ModDataComponents.DATA_COMPONENTS.register(MOD_BUS)
        ModEntities.ENTITY_TYPES.register(MOD_BUS)
        ModDamageTypes.register()

        val obj = runForDist(clientTarget = {
            MOD_BUS.addListener(::onClientSetup)
            Minecraft.getInstance()
        }, serverTarget = {
            MOD_BUS.addListener(::onServerSetup)
            "test"
        })

        println(obj)
    }

    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     * Fired on the mod specific event bus.
     */
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")
    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {
        LOGGER.log(Level.INFO, "Hello! This is working!")
    }

    private fun registerRenderers(event: RegisterRenderers) {
        event.registerEntityRenderer(
            ModEntities.RUBBER_BALL.get()
        ) { context: EntityRendererProvider.Context -> RubberBallRenderer(context) }
    }
}
