import kotlin.time.measureTimedValue

enum class Direction { Up, Down, Left, Right }

fun main() {
    fun List<String>.elementAt(position: Pair<Int, Int>): Char {
        return this[position.first][position.second]
    }

    fun getNextDirectionFromPosition(input: List<String>, position: Pair<Int, Int>, direction: Direction): Direction {
        return when (input.elementAt(position)) {
            '7' -> if (direction == Direction.Right) Direction.Down else Direction.Left
            '-' -> if (direction == Direction.Right) Direction.Right else Direction.Left
            '|' -> if (direction == Direction.Down) Direction.Down else Direction.Up
            'L' -> if (direction == Direction.Down) Direction.Right else Direction.Up
            'J' -> if (direction == Direction.Down) Direction.Left else Direction.Up
            else -> if (direction == Direction.Left) Direction.Down else Direction.Right
        }
    }

    fun part1(input: List<String>): Int {
        val validTopOnes = listOf('|', '7', 'F')
        val validBottomOnes = listOf('|', 'L', 'J')
        val validLeftOnes = listOf('-', 'L', 'F')

        val startPosition = input.mapIndexed { rowIndex, row -> rowIndex to row.indexOf('S') }.first { it.second != -1 }
        val (pathStart, pathDirection) = when {
            startPosition.first > 0 && input.elementAt(startPosition.first - 1 to startPosition.second) in validTopOnes -> {
                val nextPosition = startPosition.first - 1 to startPosition.second
                nextPosition to getNextDirectionFromPosition(input, nextPosition, Direction.Up)
            }

            startPosition.first <= input.lastIndex && input.elementAt(startPosition.first + 1 to startPosition.second) in validBottomOnes -> {
                val nextPosition = startPosition.first + 1 to startPosition.second
                nextPosition to getNextDirectionFromPosition(input, nextPosition, Direction.Down)
            }

            startPosition.second > 0 && input.elementAt(startPosition.first to startPosition.second - 1) in validLeftOnes -> {
                val nextPosition = startPosition.first to startPosition.second - 1
                nextPosition to getNextDirectionFromPosition(input, nextPosition, Direction.Left)
            }

            else -> {
                val nextPosition = startPosition.first to startPosition.second + 1
                nextPosition to getNextDirectionFromPosition(input, nextPosition, Direction.Right)
            }
        }

        var currentPathPosition = pathStart
        var currentPathDirection = pathDirection
        var pathScore = 1
        val positionList = mutableListOf(startPosition, currentPathPosition)

        do {
            val pathElement = input.elementAt(currentPathPosition)
            if (pathElement != 'S') {
                val newPathPosition = when (currentPathDirection) {
                    Direction.Up -> currentPathPosition.first - 1 to currentPathPosition.second
                    Direction.Down -> currentPathPosition.first + 1 to currentPathPosition.second
                    Direction.Left -> currentPathPosition.first to currentPathPosition.second - 1
                    Direction.Right -> currentPathPosition.first to currentPathPosition.second + 1
                }
                val newPathElement = input.elementAt(newPathPosition)
                currentPathDirection = when (newPathElement) {
                    '7' -> if (currentPathDirection == Direction.Right) Direction.Down else Direction.Left
                    '-' -> if (currentPathDirection == Direction.Right) Direction.Right else Direction.Left
                    '|' -> if (currentPathDirection == Direction.Down) Direction.Down else Direction.Up
                    'L' -> if (currentPathDirection == Direction.Down) Direction.Right else Direction.Up
                    'J' -> if (currentPathDirection == Direction.Down) Direction.Left else Direction.Up
                    else -> if (currentPathDirection == Direction.Left) Direction.Down else Direction.Right
                }
                positionList.add(newPathPosition)

                pathScore++
                currentPathPosition = newPathPosition
            }
        } while (pathElement != 'S')

        return pathScore / 2
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("inputs/Day10")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
