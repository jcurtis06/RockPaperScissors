package io.jcurtis.rockpaperscissors.game

import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class Game(private val challenger: Player, private val challenged: Player) : Listener {
    val gui = GameGUI()

    private var challengerChoice = Choice.NONE
    private var challengedChoice = Choice.NONE

    fun start() {
        challenger.openInventory(gui.inventory)
        challenged.openInventory(gui.inventory)
    }

    fun checkWin() {
        val result = when {
            challengerChoice == challengedChoice -> "It's a tie!"
            challengerChoice.beats(challengedChoice) -> "${challenger.name} wins!"
            else -> "${challenged.name} wins!"
        }

        challenger.sendMessage(result)
        challenged.sendMessage(result)

        end()
    }

    fun end() {
        challenger.closeInventory()
        challenged.closeInventory()

        GameManager.removeGame(this)
    }

    fun handleClick(event: InventoryClickEvent) {
        if (event.slot < 3 || event.slot > 5) return

        val player = event.whoClicked as Player

        if (player == challenger) {
            challengerChoice = Choice.entries.toTypedArray()[event.slot - 3]
            player.sendMessage("Challenger: You have chosen ${challengerChoice.name}.")
        } else if (player == challenged) {
            challengedChoice = Choice.entries.toTypedArray()[event.slot - 3]
            player.sendMessage("Challenged: You have chosen ${challengedChoice.name}.")
        }

        if (challengerChoice != Choice.NONE && challengedChoice != Choice.NONE) {
            checkWin()
        }
    }
}

enum class Choice {
    ROCK,
    PAPER,
    SCISSORS,
    NONE
}

fun Choice.beats(other: Choice): Boolean {
    return (this == Choice.ROCK && other == Choice.SCISSORS) ||
            (this == Choice.PAPER && other == Choice.ROCK) ||
            (this == Choice.SCISSORS && other == Choice.PAPER)
}