package dynastxu.noita.menu

import dynastxu.noita.Noita.ID
import dynastxu.noita.block.ModBlocks
import dynastxu.noita.item.Wand
import dynastxu.noita.tag.ModItemTags
import net.minecraft.world.Containers
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class WandWorkbenchMenu(
    containerId: Int,
    private val playerInventory: Inventory,
    private val access: ContainerLevelAccess
) : AbstractContainerMenu(ModMenus.WAND_WORKBENCH_MENU.get(), containerId) {
    private val LOGGER: Logger = LogManager.getLogger(ID)
    companion object {
        // 显示常量
        private const val WAND_SLOT_INDEX = 0
        private const val SPELL_DISPLAY_ROWS = 3
        private const val SPELL_DISPLAY_COLS = 9
        private const val SPELL_DISPLAY_SLOTS = SPELL_DISPLAY_ROWS * SPELL_DISPLAY_COLS
        private const val TOTAL_INTERNAL_DISPLAY_SLOTS = 1 + SPELL_DISPLAY_SLOTS

        private const val PLAYER_INVENTORY_START = TOTAL_INTERNAL_DISPLAY_SLOTS
        private const val HOTBAR_START = PLAYER_INVENTORY_START + 27

        const val MAX_SPELL_CAPACITY = 999
    }

    // 独立容器
    private val wandContainer = SimpleContainer(1)
    private val spellContainer = SimpleContainer(MAX_SPELL_CAPACITY)

    // 法术槽动态列表
    private val spellSlots = mutableListOf<DynamicSpellSlot>()

    // 当前容量（由法杖决定）
    private var capacity = 0
    // 滚动偏移（显示的第一行）
    private var scrollOffset = 0

    init {
        addWandSlot()
        addSpellSlots()
        addPlayerInventory()
        addPlayerHotbar()
    }

    private fun addWandSlot() {
        addSlot(object : Slot(wandContainer, 0, -20, 35) {
            override fun mayPlace(stack: ItemStack): Boolean = stack.`is`(ModItemTags.WAND)

            override fun setChanged() {
                super.setChanged()
                updateCapacityFromWand()
                loadSpellsFromWand()
            }

            override fun onTake(player: Player, stack: ItemStack) {
                saveSpellsToWand(player, stack)
                super.onTake(player, stack)
            }
        })
    }

    private fun saveSpellsToWand(player: Player, wandStack: ItemStack) {
        if (wandStack.isEmpty) return
        if (wandStack.item is Wand) {
            val wand = wandStack.item as Wand
            val capacity = wand.getCapacity(wandStack)  // 直接从法杖读取容量
            val inventorySlots = List(capacity) { index ->
                val itemStack = spellContainer.getItem(index)
                if (itemStack.isEmpty) ItemStack.EMPTY else itemStack
            }
            LOGGER.info("Saving spells to wand: capacity=$capacity, slots=${inventorySlots.size}")
            wand.setInventorySlots(wandStack, inventorySlots)

            // 验证保存是否成功
            val savedSlots = wand.getInventorySlots(wandStack)
            LOGGER.info("Saved slots count: ${savedSlots.size}")
            savedSlots.forEachIndexed { index, slot ->
                LOGGER.info("  Saved Slot $index: ${if (slot == null) "null" else slot.displayName.string}")
            }
        } else {
            // 理论上不会进入此处
            LOGGER.error("WandWorkbenchMenu: wandStack.item is not Wand")
            Containers.dropContents(player.level(), player.blockPosition(), spellContainer)
        }
        spellContainer.clearContent()
    }

    private fun addSpellSlots() {
        for (row in 0 until SPELL_DISPLAY_ROWS) {
            for (col in 0 until SPELL_DISPLAY_COLS) {
                val displayIndex = row * SPELL_DISPLAY_COLS + col
                val x = 30 + col * 18
                val y = 17 + row * 18
                val slot = DynamicSpellSlot(spellContainer, displayIndex, x, y)
                spellSlots.add(slot)
                addSlot(slot)
            }
        }
        updateSpellSlotIndices()
    }

    private fun addPlayerInventory() {
        for (row in 0 until 3) {
            for (col in 0 until 9) {
                addSlot(Slot(playerInventory, row * 9 + col + 9, 8 + col * 18, 84 + row * 18))
            }
        }
    }

    private fun addPlayerHotbar() {
        for (col in 0 until 9) {
            addSlot(Slot(playerInventory, col, 8 + col * 18, 142))
        }
    }

    // 从法杖读取容量
    private fun readCapacityFromWand(stack: ItemStack): Int {
        if (stack.item is Wand) {
            val item = stack.item as Wand
            return item.getCapacity(stack)
        } else {
            return 0
        }
    }

    private fun updateCapacityFromWand() {
        val wandStack = wandContainer.getItem(0)
        val newCap = if (wandStack.isEmpty) 0 else readCapacityFromWand(wandStack)
        setCapacity(newCap)
    }

    fun setCapacity(newCapacity: Int) {
        if (capacity != newCapacity) {
            capacity = newCapacity
            val maxOffset = ((capacity - 1) / SPELL_DISPLAY_COLS).coerceAtLeast(0)
            if (scrollOffset > maxOffset) scrollOffset = maxOffset
            updateSpellSlotIndices()
            broadcastChanges()
        }
    }

    private fun updateSpellSlotIndices() {
        spellSlots.forEachIndexed { displayIndex, slot ->
            val containerIndex = scrollOffset * SPELL_DISPLAY_COLS + displayIndex
            slot.setContainerIndex(if (containerIndex < capacity) containerIndex else -1)
        }
    }

    // 滚动操作（通过 clickMenuButton 触发）
    fun scroll(amount: Int) {
        val maxOffset = ((capacity - 1) / SPELL_DISPLAY_COLS).coerceAtLeast(0)
        val newOffset = (scrollOffset + amount).coerceIn(0, maxOffset)
        if (newOffset != scrollOffset) {
            scrollOffset = newOffset
            updateSpellSlotIndices()
            broadcastChanges()
        }
    }

    override fun clickMenuButton(player: Player, id: Int): Boolean {
        when (id) {
            1 -> scroll(-1)   // 向上滚动
            -1 -> scroll(1)   // 向下滚动
        }
        return true
    }

    override fun quickMoveStack(
        player: Player,
        p1: Int
    ): ItemStack {
        TODO("Not yet implemented")
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(access, player, ModBlocks.WAND_WORKBENCH.get())
    }

    override fun removed(player: Player) {
        super.removed(player)
        access.execute { _, _ ->
            val wand: ItemStack = wandContainer.getItem(0)
            saveSpellsToWand(player, wand)
            clearContainer(player, wandContainer)
        }
    }

    private fun loadSpellsFromWand() {
        val wandStack = wandContainer.getItem(0)
        if (wandStack.isEmpty) return

        if (wandStack.item is Wand) {
            val item = wandStack.item as Wand
            val inventorySlots = item.getInventorySlots(wandStack)

            spellContainer.clearContent()
            inventorySlots.forEachIndexed { index, itemStack ->
                if (itemStack != null && !itemStack.isEmpty) {
                    spellContainer.setItem(index, itemStack)
                }
            }
        }
    }

    // 自定义动态法术槽
    inner class DynamicSpellSlot(
        container: SimpleContainer,
        displayIndex: Int,
        x: Int,
        y: Int
    ) : Slot(container, displayIndex, x, y) {

        private var containerIndex = displayIndex

        fun setContainerIndex(index: Int) {
            containerIndex = index
        }

        override fun getContainerSlot(): Int = containerIndex

        override fun mayPlace(stack: ItemStack): Boolean {
            return stack.`is`(ModItemTags.SPELL) && containerIndex >= 0 && containerIndex < capacity
        }

        override fun getMaxStackSize(): Int = 1

        override fun isActive(): Boolean {
            return containerIndex in 0..<capacity
        }

        override fun getItem(): ItemStack {
            return if (containerIndex !in 0..<capacity) {
                ItemStack.EMPTY
            } else {
                super.getItem()
            }
        }
    }
}
