import kotlin.time.measureTimedValue

fun main() {
    fun calculateNextRound(lastRoundItems: List<Long>, map: List<String>): List<Long> {
        return lastRoundItems.map { num ->
            map.forEach { mapEntry ->
                val parsedMapEntry = mapEntry.split(" ").map { it.toLong() }
                if (num in parsedMapEntry[1]..parsedMapEntry[1] + parsedMapEntry[2]) {
                    return@map parsedMapEntry[0] + num - parsedMapEntry[1]
                }
            }
            num
        }
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }

        val roundItems = seeds.toMutableList()
        var index = 3
        val map = mutableListOf<String>()
        do {
            val line = input[index]
            if (line.isEmpty() || !line[0].isDigit()) {
                // do calculations
                roundItems.addAll(calculateNextRound(roundItems.takeLast(seeds.size), map))
                map.clear()
                index += 2
            } else {
                map.add(line)
                index += 1
                if (index == input.size) {
                    roundItems.addAll(calculateNextRound(roundItems.takeLast(seeds.size), map))
                }
            }
        } while (index < input.size)

        return roundItems.takeLast(seeds.size).min()
    }

    fun part2(input: List<String>): Long {
        val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }

        var index = 3
        val layers = mutableListOf<List<List<Long>>>()
        val layer = mutableListOf<List<Long>>()
        do {
            val line = input[index]
            if (line.isEmpty() || !line[0].isDigit()) {
                // do calculations
                layers.add(layer.toList())
                layer.clear()
                index += 2
            } else {
                layer.add(line.split(" ").map { it.toLong() })
                index += 1
                if (index == input.size) {
                    layers.add(layer)
                }
            }
        } while (index < input.size)

        val reversedLayers = layers.reversed()
        var possibleSolution = 0L

        while (true) {
            val seed = reversedLayers.fold(possibleSolution) { acc, layer ->
                for (map in layer) {
                    if (acc in map[0]..<map[0] + map[2]) {
                        return@fold map[1] + acc - map[0]
                    }
                }

                acc
            }
            // if seed in seeds then return here
            val seedInOriginalSeeds = seeds
                .chunked(2)
                .map { range ->
                    seed in range[0]..<range[0] + range[1]
                }.any { it }

            if (seedInOriginalSeeds) break
            possibleSolution += 1
        }

        return possibleSolution
    }

    val input = readInput("inputs/Day05")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
