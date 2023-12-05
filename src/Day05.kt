import kotlin.time.measureTimedValue

fun main() {
    fun parseInput(input: List<String>): Pair<List<Long>, List<List<List<Long>>>> {
        val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }

        var index = 3
        val layers = mutableListOf<List<List<Long>>>()
        val layer = mutableListOf<List<Long>>()
        do {
            val line = input[index]
            if (line.isEmpty() || !line[0].isDigit()) {
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

        return seeds to layers
    }

    fun part1(input: List<String>): Long {
        val (seeds, layers) = parseInput(input)

        return layers.fold(seeds) { roundItems, layer ->
            roundItems.map { num ->
                layer.forEach { mapEntry ->
                    if (num in mapEntry[1]..mapEntry[1] + mapEntry[2]) {
                        return@map mapEntry[0] + num - mapEntry[1]
                    }
                }
                num
            }
        }.min()
    }

    fun part2(input: List<String>): Long {
        val (seeds, layers) = parseInput(input)

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
