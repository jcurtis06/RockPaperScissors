package io.jcurtis.rockpaperscissors.game

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

object GameManager: Listener {
    private val games = mutableListOf<Game>()
    private val inventories = mutableListOf<Inventory>()
    private val pendingChallenges = mutableMapOf<Player, Player>()

    fun challenge(challenger: Player, challenged: Player) {
        pendingChallenges[challenger] = challenged
        challenged.sendMessage("${challenger.name} has challenged you to a game of Rock Paper Scissors! Type /rps accept to accept the challenge.")
        challenger.sendMessage("You have challenged ${challenged.name} to a game of Rock Paper Scissors!")
    }

    fun accept(player: Player) {
        if (!pendingChallenges.values.contains(player)) {
            player.sendMessage("You have no pending challenges.")
            return
        }

        val challenger = pendingChallenges.filterValues { it == player }.keys.first()
        pendingChallenges.remove(challenger)
        startGame(challenger, player)
    }

    private fun startGame(challenger: Player, challenged: Player) {
        val game = Game(challenger, challenged)
        inventories.add(game.gui.inventory)
        games.add(game)

        game.start()
    }

    fun removeGame(game: Game) {
        inventories.remove(game.gui.inventory)
        games.remove(game)
    }

    @EventHandler
    fun inventoryClickHandler(event: InventoryClickEvent) {
        if (!inventories.contains(event.inventory)) return
        event.isCancelled = true

        val game = games.first { it.gui.inventory == event.inventory }
        game.handleClick(event)
    }
}