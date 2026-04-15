package dynastxu.noita.component

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dynastxu.noita.data.Spell
import dynastxu.noita.utils.NbtHelper
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack

/**
 * 魔杖数据组件
 * @param shuffle 乱序
 * @param spellsPerCast 法术数/释放
 * @param castDelay 施放延迟
 * @param rechargeTime 充能延迟
 * @param manaMax 法力最大值
 * @param manaChgSpd 法力充能速度
 * @param capacity 容量
 * @param spread 散射
 * @param alwaysCasts 始终释放
 * @param speedMultiplier 速度加成
 * @param inventorySlots 物品栏槽位
 */
data class WandData(
    val shuffle: Boolean,
    val spellsPerCast: Int,
    val castDelay: Float,
    val rechargeTime: Float,
    val manaMax: Int,
    val manaChgSpd: Int,
    val capacity: Int,
    val spread: Float,
    val alwaysCasts: List<Spell>,
    val speedMultiplier: Float,
    val inventorySlots: List<ItemStack?> =  List(capacity) { null }
) {
    companion object {
        val CODEC: Codec<WandData> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.BOOL.optionalFieldOf("shuffle", false).forGetter(WandData::shuffle),
                Codec.INT.optionalFieldOf("spells_per_cast", 1).forGetter(WandData::spellsPerCast),
                Codec.FLOAT.optionalFieldOf("cast_delay", 0.0f).forGetter(WandData::castDelay),
                Codec.FLOAT.optionalFieldOf("recharge_time", 0.0f).forGetter(WandData::rechargeTime),
                Codec.INT.optionalFieldOf("mana_max", 0).forGetter(WandData::manaMax),
                Codec.INT.optionalFieldOf("mana_chg_spd", 0).forGetter(WandData::manaChgSpd),
                Codec.INT.optionalFieldOf("capacity", 0).forGetter(WandData::capacity),
                Codec.FLOAT.optionalFieldOf("spread", 0.0f).forGetter(WandData::spread),
                NbtHelper.createSpellListCodec("always_casts").optionalFieldOf("always_casts", listOf())
                    .forGetter(WandData::alwaysCasts),
                Codec.FLOAT.optionalFieldOf("speed_multiplier", 1.0f).forGetter(WandData::speedMultiplier),
                ItemStack.OPTIONAL_CODEC.listOf().optionalFieldOf("inventory_slots", listOf()).forGetter(WandData::inventorySlots)
            ).apply(instance, ::WandData)
        }

        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, WandData> = object : StreamCodec<RegistryFriendlyByteBuf, WandData> {
            override fun decode(buf: RegistryFriendlyByteBuf): WandData {
                val shuffle = ByteBufCodecs.BOOL.decode(buf)
                val spellsPerCast = ByteBufCodecs.VAR_INT.decode(buf)
                val castDelay = ByteBufCodecs.FLOAT.decode(buf)
                val rechargeTime = ByteBufCodecs.FLOAT.decode(buf)
                val manaMax = ByteBufCodecs.VAR_INT.decode(buf)
                val manaChgSpd = ByteBufCodecs.VAR_INT.decode(buf)
                val capacity = ByteBufCodecs.VAR_INT.decode(buf)
                val spread = ByteBufCodecs.FLOAT.decode(buf)
                val alwaysCasts = NbtHelper.readSpellList(buf)
                val speedMultiplier = ByteBufCodecs.FLOAT.decode(buf)
                val inventorySlots = readItemStackList(buf)
                return WandData(
                    shuffle,
                    spellsPerCast,
                    castDelay,
                    rechargeTime,
                    manaMax,
                    manaChgSpd,
                    capacity,
                    spread,
                    alwaysCasts,
                    speedMultiplier,
                    inventorySlots
                )
            }

            override fun encode(buf: RegistryFriendlyByteBuf, data: WandData) {
                ByteBufCodecs.BOOL.encode(buf, data.shuffle)
                ByteBufCodecs.VAR_INT.encode(buf, data.spellsPerCast)
                ByteBufCodecs.FLOAT.encode(buf, data.castDelay)
                ByteBufCodecs.FLOAT.encode(buf, data.rechargeTime)
                ByteBufCodecs.VAR_INT.encode(buf, data.manaMax)
                ByteBufCodecs.VAR_INT.encode(buf, data.manaChgSpd)
                ByteBufCodecs.VAR_INT.encode(buf, data.capacity)
                ByteBufCodecs.FLOAT.encode(buf, data.spread)
                NbtHelper.writeSpellList(buf, data.alwaysCasts)
                ByteBufCodecs.FLOAT.encode(buf, data.speedMultiplier)
                writeItemStackList(buf, data.inventorySlots)
            }

            private fun writeItemStackList(buf: RegistryFriendlyByteBuf, slots: List<ItemStack?>) {
                ByteBufCodecs.VAR_INT.encode(buf, slots.size)
                for (slot in slots) {
                    if (slot == null || slot.isEmpty) {
                        ByteBufCodecs.BOOL.encode(buf, false)
                    } else {
                        ByteBufCodecs.BOOL.encode(buf, true)
                        ItemStack.STREAM_CODEC.encode(buf, slot)
                    }
                }
            }

            private fun readItemStackList(buf: RegistryFriendlyByteBuf): List<ItemStack?> {
                val size = ByteBufCodecs.VAR_INT.decode(buf)
                if (size <= 0) {
                    return emptyList()
                }

                val slots = mutableListOf<ItemStack?>()
                repeat(size) {
                    val hasItem = ByteBufCodecs.BOOL.decode(buf)
                    if (hasItem) {
                        slots.add(ItemStack.STREAM_CODEC.decode(buf))
                    } else {
                        slots.add(null)
                    }
                }
                return slots
            }
        }
    }
}
