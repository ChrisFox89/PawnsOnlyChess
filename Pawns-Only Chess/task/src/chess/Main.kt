package chess

class Board {
    var turn = true
    var enpassant = false

    private var cell = mutableListOf(
        mutableListOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
        mutableListOf('B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'),
        mutableListOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
        mutableListOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
        mutableListOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
        mutableListOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
        mutableListOf('W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'),
        mutableListOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ')
    )

    //  display implementation
    fun printboard(){
        println("  +---+---+---+---+---+---+---+---+")
        for (i in 0..7) {
            print("${8-i} |")
            for (j in 0..7) print(" ${cell[i][j]} |")
            println()
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h \n")
    }
    //  moving pawns function
    fun move(command: String) {


        val convertlist = listOf('a','b','c','d','e','f','g','h')

        var x1 = 0
        for (i in 0..7)
            if (command[0] == convertlist[i]){
                x1 = i
                break
            }
        val y1 = 8 - command[1].digitToInt()

        var x2 = 0
        for (i in 0..7)
            if (command[2] == convertlist[i]){
                x2 = i
                break
            }
        val y2 = 8 - command[3].digitToInt()

        // white turn
        if (turn) {
            if (cell[y1][x1] != 'W') {
                println("No white pawn at ${command[0]}${command[1]}")
            } else when {
                y1 == 6 &&  y2 == 4 && x1 == x2 && cell[5][x1] == ' '-> {
                    cell[y2][x2] = 'W'
                    cell[y1][x1] = ' '
                    turn = !turn
                    printboard()
                    enpassant = true
                }
                (y1 - y2 == 1) && (x1 == x2) && cell[y2][x2] == ' ' -> {
                    cell[y2][x2] = 'W'
                    cell[y1][x1] = ' '
                    turn = !turn
                    printboard()
                    enpassant = false
                }
                (y1 - y2 == 1) && (kotlin.math.abs(x1 - x2) == 1) && cell[y2][x2] == 'B' -> {
                    cell[y2][x2] = 'W'
                    cell[y1][x1] = ' '
                    turn = !turn
                    printboard()
                    enpassant = false
                }
                enpassant && y2 == 2 && (cell[y1][x2] == 'B') -> {
                    cell[y2][x2] = 'W'
                    cell[y1][x2] = ' '
                    cell[y1][x1] = ' '
                    turn = !turn
                    printboard()
                    enpassant = false
                }

                else -> println("Invalid Input")
            }
        } else { // black turn
            if (cell[y1][x1] != 'B') {
                println("No black pawn at ${command[0]}${command[1]}")
            } else when {
                y1 == 1 &&  y2 == 3 && x1 == x2 -> {
                    cell[y2][x2] = 'B'
                    cell[y1][x1] = ' '
                    turn = !turn
                    printboard()
                    enpassant = true
                }
                (y2 - y1 == 1) && (x1 == x2) && cell[y2][x2] == ' ' -> {
                    cell[y2][x2] = 'B'
                    cell[y1][x1] = ' '
                    turn = !turn
                    printboard()
                    enpassant = false
                }
                (y2 - y1 == 1) && (kotlin.math.abs(x1 - x2) == 1) && cell[y2][x2] == 'W' -> {
                    cell[y2][x2] = 'B'
                    cell[y1][x1] = ' '
                    turn = !turn
                    printboard()
                    enpassant = false
                }
                enpassant && y2 == 5 && (cell[y1][x2] == 'W') -> {
                    cell[y2][x2] = 'B'
                    cell[y1][x2] = ' '
                    cell[y1][x1] = ' '
                    turn = !turn
                    printboard()
                    enpassant = false
                }
                else -> println("Invalid Input")
            }

        }
    }

    fun checkWinCondition(): Boolean {
        // reaching last line condition
        for (i in 0..7) {
            if (cell[0][i] == 'W') {
                println("White wins!")
                return true
            }
        }
        for (i in 0..7) {
            if (cell[7][i] == 'B') {
                println("Black wins!")
                return true
            }
        }

        // losing all chess condition

        var endOfWChess = true
        var endOfBChess = true
        for (i in 0..7) {
            for (j in 0..7) {
                if (cell[i][j] == 'W') endOfWChess = false
                if (cell[i][j] == 'B') endOfBChess = false
            }
        }

        if (endOfWChess) {
            println("Black wins!")
            return true
        }

        if (endOfBChess) {
            println("White wins!")
            return true
        }

        // Stalemate check
        if (!enpassant) {
            var ableToMove = false
            if (turn) {
                for (i in 1..7) {
                    for (j in 0..7) {
                        if (cell[i][j] == 'W') {
                            if ((cell[i - 1][j] == ' ') /*|| ((cell[i - 1][j + 1] == 'B') || (cell[i - 1][j - 1] == 'B'))*/) ableToMove = true
                        }
                    }
                }
            }
            if (!turn) {
                for (i in 0..6) {
                    for (j in 0..7) {
                        if (cell[i][j] == 'B') {
                            if ((cell[i + 1][j] == ' ') /*|| ((cell[i + 1][j + 1] == 'W') || (cell[i + 1][j - 1] == 'W'))*/) ableToMove = true
                        }
                    }
                }
            }

            if (!ableToMove) {
                println("Stalemate!")
                return true
            }
        }



        return false
    }

}


fun main() {

    val board = Board() // board init
    var command = ""    // user input value

    println("Pawns-Only Chess")

    println("First Player's name:")
    val name1 = readLine()!!

    println("Second Player's name:")
    val name2 = readLine()!!


    board.printboard()
    val pattern = Regex("[1-h][1-8][1-h][1-8]")

    do {
        try {
            if (board.turn) {
                println("$name1's turn:")
                command = readLine()!!
                board.move(command)

            } else {
                println("$name2's turn:")
                command = readLine()!!
                board.move(command)

            }
        } catch (e: Exception) { }

        if (!command.matches(pattern) && command != "exit") println("Invalid Input")
        if (board.checkWinCondition()) break
    } while(command != "exit")

    println("Bye!")

}