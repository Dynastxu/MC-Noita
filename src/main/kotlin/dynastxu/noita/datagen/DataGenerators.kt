package dynastxu.noita.datagen

import dynastxu.noita.Noita
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(modid = Noita.ID)
object DataGenerators {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        val packOutput = generator.packOutput
        val existingFileHelper = event.existingFileHelper
        val lookupProvider = event.lookupProvider

        generator.addProvider(
            event.includeClient(),
            ModItemModelProvider(packOutput, Noita.ID, existingFileHelper)
        )
        generator.addProvider(
            event.includeClient(),
            ModLanguageProvider(packOutput, Noita.ID, "en_us")
        )
        generator.addProvider(
            event.includeClient(),
            ModLanguageProvider(packOutput, Noita.ID, "zh_cn")
        )
        val blockTagsProvider = ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper)
        generator.addProvider(event.includeServer(), blockTagsProvider)
        generator.addProvider(
            event.includeServer(),
            ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper)
        )
    }
}
