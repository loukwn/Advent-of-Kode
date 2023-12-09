import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val histories = arrayListOf(line.split(" ").map { it.toInt() })
            while (histories.last().toSet().size != 1) {
                histories.add(histories.last().zipWithNext { a, b -> b - a })
            }
            histories.reversed().drop(1).fold(histories.last().last()) { previousLast, history ->
                history.last() + previousLast
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val histories = arrayListOf(line.split(" ").map { it.toInt() })
            while (histories.last().toSet().size != 1) {
                histories.add(histories.last().zipWithNext { a, b -> b - a })
            }
            histories.reversed().drop(1).fold(histories.last().first()) { previousLast, history ->
                history.first() - previousLast
            }
        }
    }

    val input = readInput("inputs/Day09")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
