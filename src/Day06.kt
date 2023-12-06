import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].split(" ").drop(1).filter { it.isNotEmpty() }.map { it.trim().toInt() }
        val distances = input[1].split(" ").drop(1).filter { it.isNotEmpty() }.map { it.trim().toInt() }

        return times.zip(distances).map { (time, recordDistance) ->
            (0..time).count { heldTime ->
                val distanceTraveled = (time - heldTime) * heldTime

                recordDistance < distanceTraveled
            }
        }.reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        val time = input[0].replace(" ", "").split(":")[1].toLong()
        val distance = input[1].replace(" ", "").split(":")[1].toLong()

        return (0..time).count { heldTime ->
            val distanceTraveled = (time - heldTime) * heldTime

            distance < distanceTraveled
        }
    }

    val input = readInput("inputs/Day06")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
