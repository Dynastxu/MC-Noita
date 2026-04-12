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

        fun addDamageTypeTranslations() {
            addDeathAttack("slice_damage", $$"%1$s was cut into pieces")
            addDeathAttackPlayer("slice_damage", $$"%1$s was cut into pieces by %2$s")
            addDeathAttack("toxic_damage", $$"%1$s was poisoned")
            addDeathAttackPlayer("toxic_damage", $$"%1$s was poisoned by %2$s")
            addDeathAttack("drill_damage", $$"%1$s got an oversized gastric perforation")
            addDeathAttackPlayer("drill_damage", $$"%1$s was chiseled into a floor drain by %2$s")
            addDeathAttack("holy_damage", $$"%1$s was turned into a light")
            addDeathAttackPlayer("holy_damage", $$"%1$s was turned into a light by %2$s")
        }

        add("itemGroup.noita", "Noita")
        addItemTranslations()
        addDamageTypeTranslations()
    }

    private fun addChineseTranslations() {
        fun addItemTranslations() {
            // 法杖
            addItem(ModItems.WAND, "法杖")
            addItem(ModItems.STARTER_WAND, "初始法杖")

            // 法术
            addItem(ModItems.RUBBER_BALL, "弹跳绿豆")
        }

        fun addDamageTypeTranslations() {
            addDeathAttack("slice_damage", $$"%1$s 被切成了碎片")
            addDeathAttackPlayer("slice_damage", $$"%1$s 被 %2$s 切成了碎片")
            addDeathAttack("toxic_damage", $$"%1$s 被毒死了")
            addDeathAttackPlayer("toxic_damage", $$"%1$s 被 %2$s 毒死了")
            addDeathAttack("drill_damage", $$"%1$s 患了超大胃穿孔")
            addDeathAttackPlayer("drill_damage", $$"%1$s 被 %2$s 凿成了地漏")
            addDeathAttack("holy_damage", $$"%1$s 化成了一道光")
            addDeathAttackPlayer("holy_damage", $$"%1$s 被 %2$s 神圣死了")
        }

        add("itemGroup.noita", "女巫")
        addItemTranslations()
        addDamageTypeTranslations()
    }

    private fun addDeathAttack(id: String, msg: String) {
        add("death.attack.$id", msg)
    }

    private fun addDeathAttackPlayer(id: String, msg: String) {
        add("death.attack.$id.player", msg)
    }
}
