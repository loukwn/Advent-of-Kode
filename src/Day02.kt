import kotlin.math.max

fun main() {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val splitLine = line.split(": ")
            val gameId = splitLine[0].split(" ")[1].toInt()
            val setsOfCubes = splitLine[1].split("; ")

            val isValidGame = setsOfCubes.map { set ->
                val redCubes = set.split(" r").takeIf { it.size > 1 }?.getOrNull(0)?.split(" ")?.last()?.toInt() ?: 0
                val greenCubes = set.split(" g").takeIf { it.size > 1 }?.getOrNull(0)?.split(" ")?.last()?.toInt() ?: 0
                val blueCubes = set.split(" b").takeIf { it.size > 1 }?.getOrNull(0)?.split(" ")?.last()?.toInt() ?: 0

                redCubes <= maxRed && greenCubes <= maxGreen && blueCubes <= maxBlue
            }.all { it }

            if (isValidGame) gameId else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val splitLine = line.split(": ")
            val setsOfCubes = splitLine[1].split("; ")

            val minimumCubesPerGame = setsOfCubes.map { set ->
                val redCubes = set.split(" r").takeIf { it.size > 1 }?.getOrNull(0)?.split(" ")?.last()?.toInt() ?: 0
                val greenCubes = set.split(" g").takeIf { it.size > 1 }?.getOrNull(0)?.split(" ")?.last()?.toInt() ?: 0
                val blueCubes = set.split(" b").takeIf { it.size > 1 }?.getOrNull(0)?.split(" ")?.last()?.toInt() ?: 0

                Triple(redCubes, greenCubes, blueCubes)
            }.reduce { acc, triple ->
                Triple(
                    max(acc.first, triple.first),
                    max(acc.second, triple.second),
                    max(acc.third, triple.third),
                )
            }

            minimumCubesPerGame.first * minimumCubesPerGame.second * minimumCubesPerGame.third
        }
    }

    val input = readInput("inputs/Day02")
    part1(input).println()
    part2(input).println()
}
