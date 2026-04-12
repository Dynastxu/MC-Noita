package dynastxu.noita.utils

import com.mojang.serialization.Codec
import dynastxu.noita.data.Spell
import dynastxu.noita.data.Spells
import io.netty.buffer.ByteBuf
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.codec.ByteBufCodecs

object NbtHelper {
    /**
     * 从 NBT 标签中读取法术列表
     */
    fun getSpellsList(tag: CompoundTag, key: String): MutableList<Spell>? {
        if (!tag.contains(key, Tag.TAG_LIST.toInt())) {
            return null
        }

        val spellsList = tag.getList(key, Tag.TAG_STRING.toInt())
        val spells = ArrayList<Spell>()

        for (i in spellsList.indices) {
            val spellName = spellsList.getString(i)
            val spell: Spell? = getSpellByName(spellName)
            if (spell != null) {
                spells.add(spell)
            }
        }

        return if (spells.isEmpty()) null else spells
    }

    /**
     * 将法术列表写入 NBT 标签
     */
    fun setSpellsList(tag: CompoundTag, key: String, spells: List<Spell>?) {
        if (spells.isNullOrEmpty()) {
            return
        }

        val spellsList = ListTag()
        for (spell in spells) {
            val spellName = getSpellName(spell)
            if (spellName != null) {
                spellsList.add(StringTag.valueOf(spellName))
            }
        }

        if (!spellsList.isEmpty()) {
            tag.put(key, spellsList)
        }
    }

    /**
     * 创建用于 Codec 的法术列表编解码器
     */
    @JvmStatic
    fun createSpellListCodec(key: String): Codec<List<Spell>> {
        return CompoundTag.CODEC.xmap(
            { tag ->
                val spells = getSpellsList(tag, key)
                spells ?: emptyList()
            },
            { spells ->
                val tag = CompoundTag()
                setSpellsList(tag, key, spells)
                tag
            }
        )
    }

    /**
     * 将法术列表写入 ByteBuf
     */
    fun writeSpellList(buf: ByteBuf, spells: List<Spell>) {
        val spellNames: MutableList<String> = ArrayList()
        for (spell in spells) {
            val name = getSpellName(spell)
            if (name != null) {
                spellNames.add(name)
            }
        }

        ByteBufCodecs.VAR_INT.encode(buf, spellNames.size)
        for (name in spellNames) {
            ByteBufCodecs.STRING_UTF8.encode(buf, name)
        }
    }

    /**
     * 从 ByteBuf 读取法术列表
     */
    fun readSpellList(buf: ByteBuf): MutableList<Spell> {
        val size = ByteBufCodecs.VAR_INT.decode(buf)
        if (size == 0) {
            return mutableListOf()
        }

        val spells: MutableList<Spell> = ArrayList()
        repeat(size) {
            val name = ByteBufCodecs.STRING_UTF8.decode(buf)
            val spell: Spell? = getSpellByName(name)
            if (spell != null) {
                spells.add(spell)
            }
        }
        return spells
    }

    /**
     * 根据 Spell 对象获取其名称
     */
    private fun getSpellName(spell: Spell?): String? {
        if (spell === Spells.rubberBall) {
            return "RUBBER_BALL"
        }
        return null
    }

    /**
     * 根据名称获取 Spell 对象
     */
    private fun getSpellByName(name: String): Spell? {
        return when (name) {
            "RUBBER_BALL" -> Spells.rubberBall
            else -> null
        }
    }
}
