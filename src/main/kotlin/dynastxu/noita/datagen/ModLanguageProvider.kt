package dynastxu.noita.datagen

import dynastxu.noita.Noita
import dynastxu.noita.item.ModItems
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.LanguageProvider

class ModLanguageProvider(output: PackOutput, modid: String, private val locale: String) : LanguageProvider(
    output, modid,
    locale
) {
    companion object {
        const val BLACK = "§0"
        const val DARK_BLUE = "§1"
        const val DARK_GREEN = "§2"
        const val DARK_AQUA = "§3"
        const val DARK_RED = "§4"
        const val DARK_PURPLE = "§5"
        const val GOLD = "§6"
        const val GRAY = "§7"
        const val DARK_GRAY = "§8"
        const val BLUE = "§9"
        const val GREEN = "§a"
        const val AQUA = "§b"
        const val RED = "§c"
        const val LIGHT_PURPLE = "§d"
        const val YELLOW = "§e"
        const val WHITE = "§f"
        const val RANDOM = "§k"
        const val BOLD = "§l"
        const val STRIKE = "§m"
        const val UNDERLINE = "§n"
        const val ITALIC = "§o"
        const val RESET = "§r"
    }
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

        fun addTooltipTranslations() {
            addTooltip("yes", "Yes")
            addTooltip("no", "No")
            addTooltip("wand.not_initialized", "$RED$BOLD${ITALIC}Not Initialized$RESET")
            addTooltip("wand.shuffle", "${AQUA}Shuffle: ${WHITE}%s$RESET")
            addTooltip("wand.spells_per_cast", "${AQUA}Spells/Cast: ${WHITE}%s$RESET")
            addTooltip("wand.cast_delay", "${AQUA}Cast delay: ${WHITE}%ss$RESET")
            addTooltip("wand.recharge_time", "${AQUA}Rechrg. Time: ${WHITE}%ss$RESET")
            addTooltip("wand.mana_max", "${AQUA}Mana max: ${WHITE}%s$RESET")
            addTooltip("wand.mana_chg_spd", "${AQUA}Mana chg. Spd: ${WHITE}%s$RESET")
            addTooltip("wand.capacity", "${AQUA}Capacity: ${WHITE}%s$RESET")
            addTooltip("wand.spread", "${AQUA}Spread: ${WHITE}%s°$RESET")
        }

        add("itemGroup.${Noita.ID}", "Noita")
        addItemTranslations()
        addDamageTypeTranslations()
        addTooltipTranslations()
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

        fun addTooltipTranslations() {
            addTooltip("yes", "是")
            addTooltip("no", "否")
            addTooltip("wand.not_initialized", "$RED$BOLD${ITALIC}未初始化$RESET")
            addTooltip("wand.shuffle", "${AQUA}乱序：${WHITE}%s$RESET")
            addTooltip("wand.spells_per_cast", "${AQUA}法术数/释放：${WHITE}%s$RESET")
            addTooltip("wand.cast_delay", "${AQUA}释放延迟：${WHITE}%s 秒$RESET")
            addTooltip("wand.recharge_time", "${AQUA}充能延迟：${WHITE}%s 秒$RESET")
            addTooltip("wand.mana_max", "${AQUA}法力最大值：${WHITE}%s$RESET")
            addTooltip("wand.mana_chg_spd", "${AQUA}法力充能速度：${WHITE}%s$RESET")
            addTooltip("wand.capacity", "${AQUA}容量：${WHITE}%s$RESET")
            addTooltip("wand.spread", "${AQUA}散射：${WHITE}%s°$RESET")
        }

        add("itemGroup.${Noita.ID}", "女巫")
        addItemTranslations()
        addDamageTypeTranslations()
        addTooltipTranslations()
    }

    private fun addDeathAttack(id: String, msg: String) {
        add("death.attack.$id", msg)
    }

    private fun addDeathAttackPlayer(id: String, msg: String) {
        add("death.attack.$id.player", msg)
    }

    private fun addTooltip(key: String, msg: String) {
        add("tooltip.${Noita.ID}.$key", msg)
    }
}
