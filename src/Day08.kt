import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {
        val navigation = input[0].map { if (it == 'L') 0 else 1 }
        val map = input.drop(2).associate { line ->
            val currentPosition = line.substringBefore("=").trim()
            val positionsToNavigateFromCurrent = line.substringAfter("=").replace("[ ()]".toRegex(), "").split(",")

            currentPosition to positionsToNavigateFromCurrent
        }

        var steps = 0
        var currentPosition = "AAA"

        do {
            currentPosition = map[currentPosition]!![navigation[(steps % navigation.size).toInt()]]
            steps++
        } while (currentPosition != "ZZZ")

        return steps
    }

    fun part2(input: List<String>): Long {
        val navigation = input[0].map { if (it == 'L') 0 else 1 }
        val map = input.drop(2).associate { line ->
            val currentPosition = line.substringBefore("=").trim()
            val positionsToNavigateFromCurrent = line.substringAfter("=").replace("[ ()]".toRegex(), "").split(",")

            currentPosition to positionsToNavigateFromCurrent
        }
        var steps = 0
        var currentPositions = map.keys.filter { it.endsWith('A') }
        val foundEndAt = currentPositions.map { 0 }.toMutableList()

        do {
            currentPositions = currentPositions.map {
                map[it]!![navigation[(steps % navigation.size)]]
            }

            steps++
            currentPositions.forEachIndexed { index, s ->
                if (s.endsWith("Z")) {
                    foundEndAt[index] = steps
                }
            }
        } while (foundEndAt.any { it == 0 })

        return lcm(foundEndAt)
    }

    val input = readInput("inputs/Day08")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
