package io.jcurtis.rockpaperscissors.game

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class GameGUI {
    var inventory: Inventory = Bukkit.createInventory(null, 9, Component.text("Rock Paper Scissors"))

    init {
        inventory.setItem(3, generateItemStack("Rock", Material.STONE))
        inventory.setItem(4, generateItemStack("Paper", Material.PAPER))
        inventory.setItem(5, generateItemStack("Scissors", Material.SHEARS))
    }

    private fun generateItemStack(name: String, material: Material): ItemStack {
        val itemStack = ItemStack(material)
        val itemMeta = itemStack.itemMeta
        itemMeta.displayName(Component.text(name))
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}