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
        fun addItemTranslations() {
            // 法杖
            addItem(ModItems.WAND, "Wand")
            addItem(ModItems.STARTER_WAND, "Starter Wand")

            // 法术
            addItem(ModItems.RUBBER_BALL, "Bouncing Burst")
        }

        add("itemGroup.noita", "Noita")
        addItemTranslations()
    }

    private fun addChineseTranslations() {
        fun addItemTranslations() {
            // 法杖
            addItem(ModItems.WAND, "法杖")
            addItem(ModItems.STARTER_WAND, "初始法杖")

            // 法术
            addItem(ModItems.RUBBER_BALL, "弹跳绿豆")
        }

        add("itemGroup.noita", "女巫")
        addItemTranslations()
    }
}
