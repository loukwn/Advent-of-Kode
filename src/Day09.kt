import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val input = readInput("inputs/Day09")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}