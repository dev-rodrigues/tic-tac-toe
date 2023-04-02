@file:Suppress("UNUSED_EXPRESSION")

package br.com.devrodrigues.core

abstract class BasePlayer(var symbol: StatePlayed, var verbose: Boolean = false) {
    abstract fun move(board: Board)

    override fun toString(): String {
        return this.symbol.value
    }
}

class HumanPlayer(symbol: StatePlayed, verbose: Boolean = false) : BasePlayer(
    symbol = symbol,
    verbose = verbose
) {
    override fun move(board: Board) {
        """
        Move over board
        """

        var position: Int? = null

        while (true) {
            print("What's your move? ")
            position = readlnOrNull()?.toIntOrNull() ?: continue
            if (position in board.getAvailableMoves()) {
                break
            }
            println("Play again ... ")
        }
        board.play(symbol, position!!)
    }
}

class MiniMaxAgentPlayer(symbol: StatePlayed, verbose: Boolean): BasePlayer(
    symbol = symbol,
    verbose = verbose
) {
    override fun move(board: Board) {
        """
        Make movement 
        """
        val  position = getBestPosition(symbol, board)
        board.play(symbol, position)
    }
}