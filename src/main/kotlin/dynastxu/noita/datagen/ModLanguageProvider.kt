package dynastxu.noita.datagen

import dynastxu.noita.item.ModItems
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider

class ModLanguageProvider(output: PackOutput, modid: String, private val locale: String) : LanguageProvider(
    output, modid,
    locale
) {
    override fun addTranslations() {
        if ("en_us" == locale) {
            addEnglishTranslations()
        } else if ("zh_cn" == locale) {
            addChineseTranslations()
        }
    }

    private fun addEnglishTranslations() {
        addItem(ModItems.WAND, "Wand")
        addItem(ModItems.STARTER_WAND, "Starter Wand")
        add("itemGroup.noita", "Noita")
    }

    private fun addChineseTranslations() {
        addItem(ModItems.WAND, "法杖")
        addItem(ModItems.STARTER_WAND, "初始法杖")
        add("itemGroup.noita", "女巫")
    }
}
