package br.com.devrodrigues.core

import br.com.devrodrigues.core.StatePlayed.EMPTY

@Suppress("UNUSED_EXPRESSION")
class Board() {

    var board: MutableMap<Int, String>

    init {
        """
            Class for representing the game with 9 slots on a 3x3 board.
        """
        this.board = initialBoard()
    }

    private fun initialBoard():MutableMap<Int, String> {
        return (1..9).associateWith { EMPTY.value }.toMutableMap()
    }

    override fun toString(): String {
        val board = this.board
        println("""
            ${board[1]} | ${board[2]} | ${board[3]}
            ---------
            ${board[4]} | ${board[5]} | ${board[6]}
            ---------
            ${board[7]} | ${board[8]} | ${board[9]}
        """.trimIndent())
        return ""
    }

    fun pos(pos: Int): String {
        return this.board[pos] ?: EMPTY.value
    }

    fun play(player: StatePlayed, pos: Int) {
        """
            Perform the move in the board
        """
        try {
            if (this.board[pos] != EMPTY.value) {
                print { "Move already made." +
                        "Play one of these spots: ${getAvailableMovesStr()}" }
                return
            }
        } catch (e: Exception) {
            print { "Position already taken" }
        }
        this.board[pos] = player.value
    }

    private fun getAvailableMovesStr(): List<String> {
        return getAvailableMoves().map { it.toString() }
    }

    fun getAvailableMoves(): List<Int> {
        return board.filterValues { it == EMPTY.value }.keys.toList()
    }

    fun copy(): Board {
        val copiedBoard = Board()
        copiedBoard.board = board.toMutableMap()
        return copiedBoard
    }
}