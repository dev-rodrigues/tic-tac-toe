@file:Suppress("UNUSED_EXPRESSION")

package br.com.devrodrigues.core

import br.com.devrodrigues.core.StatePlayed.EMPTY
import java.util.Collections.min
import java.util.Objects.isNull

class Game(
    private val oPlayer: BasePlayer,
    private val xPlayer: BasePlayer,
    val board: Board,
    private val verbose: Boolean = false,
    private var nextPlayer: BasePlayer? = null,
    var winner: BasePlayer? = null
) {

    init {
        if (nextPlayer == null) {
            this.nextPlayer = xPlayer
        }
    }

    private fun getNextPlayer(turn: StatePlayed): BasePlayer {
        return when (turn) {
            StatePlayed.CROSS -> xPlayer
            StatePlayed.CIRCLE -> oPlayer
            else -> throw Exception("Invalid turn")
        }
    }

    private fun computeWinner(): String? {
        """
        Check whether someone has won
        """
        return hasWinner(board)
    }

    fun play() {
        """
        The game play
        """

        var turn = nextPlayer!!
        while (true) {
            if (verbose) {
                println("Player $turn is your turn")
                println()
            }

            turn.move(board)
            if (verbose) {
                println()
                print(board)
            }
            turn = getNextPlayer(turn.symbol)
            val winner = computeWinner()

            if (winner != null) {
                if (verbose) {
                    println("$winner WON!!!!!!")
                }
                this.winner = turn
                break
            }

            if (board.getAvailableMoves().isEmpty()) {
                if (verbose) {
                    println("DEU VELHA!")
                }
                break
            }
        }
    }
}

// use case
fun hasWinner(board: Board): String? {
    """
        Check whether someone won the game
    """

    val victories = listOf(
        listOf(1, 5, 9),
        listOf(7, 5, 3),
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9),
        listOf(7, 4, 1),
        listOf(8, 5, 2),
        listOf(9, 6, 3)
    )

    for ((p1, p2, p3) in victories) {
        if (board.pos(p1) == board.pos(p2) && board.pos(p2) == board.pos(p3) && board.pos(p3) != EMPTY.value) {
            return board.pos(p1)
        }
    }
    return null
}

fun computedScore(board: Board): Int? {
    """
    Compute the score for minimax process
    """

    val winner = hasWinner(board)
    if (winner!= null) {
        return MINIMAX_SCORES()[winner]!!
    }
    if (board.getAvailableMoves().isEmpty()) {
        return 0
    }

    return null
}

fun getBestPosition(turn: StatePlayed, board: Board): Int {
    """
    Get best position using Minimax algorithm
    """
    val boardCopy = board.copy()
    val moves = mutableMapOf<Int, Int>()

    for (move in boardCopy.getAvailableMoves()) {
        boardCopy.play(turn, move)

        //
        val score = computedScore(board)

        if (score != null) {
            return move
        } else {
            moves[move] = minimax(boardCopy, 1, false)
        }

        board.board[move] = EMPTY.value
    }

    return min(moves.entries, Comparator.comparingInt { it.value }).key
}

fun minimax(board: Board, depth: Int, isMaximizing: Boolean): Int {
    """
    Minimax algorithm
    """

    var bestScore = if (isMaximizing) {
        Int.MIN_VALUE
    } else {
        Int.MAX_VALUE
    }

    var score: Int? = null

    for (move in board.getAvailableMoves()) {
        val currentSymbol = if (isMaximizing) StatePlayed.CIRCLE else StatePlayed.CROSS
        board.play(currentSymbol, move)
        score = computedScore(board)

        if (score != null) {
            board.board[move] = EMPTY.value
            return score
        }

        score = minimax(board, depth + 1, !isMaximizing)
        board.board[move] = EMPTY.value

        if (isMaximizing) {
            bestScore = maxOf(bestScore, score)
        } else {
            bestScore = minOf(bestScore, score)
        }
    }

    return if (isNull(score)) {
        0
    } else {
        score!!
    }
}

fun MINIMAX_SCORES() = mapOf(
    StatePlayed.CROSS.value to 10,
    StatePlayed.CIRCLE.value to -10,
)