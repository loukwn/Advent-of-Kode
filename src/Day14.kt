import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {
        return input[0].indices.sumOf { colIndex ->
            input.foldIndexed(0 to 0) { index, acc, row ->
                when (row[colIndex]) {
                    'O' -> acc.first + input.size - acc.second to acc.second + 1
                    '#' -> acc.first to index + 1
                    else -> acc
                }
            }.first
        }
    }

    fun part2(input: List<String>): Int {
        fun tiltInDirection(input: List<List<Char>>, direction: Int): Pair<List<List<Char>>, Int> {
            val newRockSetup = mutableListOf<MutableList<Char>>().apply {
                repeat(if (direction % 2 == 0) input.size else input[0].size) {
                    add(
                        mutableListOf<Char>().apply {
                            repeat(if (direction % 2 == 0) input[0].size else input.size) {
                                add(
                                    '.',
                                )
                            }
                        },
                    )
                }
            }

            val indices = if (direction % 2 == 0) input[0].indices else input.indices
            val minorSide = when (direction) {
                0 -> input
                1 -> input[0].indices.map { a -> buildList { repeat(input.size) { b -> add(input[b][a]) } } }
                2 -> input.reversed()
                else -> input[0].indices.map { a -> buildList { repeat(input.size) { b -> add(input[b][a]) } } }.reversed()
            }

            val score = indices.sumOf { minorIndex ->
                minorSide.foldIndexed(0 to 0) { index, acc, row ->
                    when (row[minorIndex]) {
                        'O' -> {
                            newRockSetup[acc.second][minorIndex] = 'O'
                            acc.first + minorSide.size - acc.second to acc.second + 1
                        }

                        '#' -> {
                            newRockSetup[index][minorIndex] = '#'
                            acc.first to index + 1
                        }

                        else -> acc
                    }
                }.first
            }

            val finalRockSetup = when (direction) {
                0 -> newRockSetup
                1 -> input.indices.map { a -> input[0].indices.map { b -> newRockSetup[b][a] } }
                2 -> newRockSetup.reversed()
                else -> input.indices.map { a -> input[0].indices.reversed().map { b -> newRockSetup[b][a] } }
            }
            return finalRockSetup to score
        }

        var rocks = input.map { it.toList() }
        repeat(100) {
            repeat(4) { tiltInDirection(rocks, it).also { rocks = it.first } }
        }

        return rocks.mapIndexed { index, chars ->
            chars.count { it == 'O' } * (rocks.size - index)
        }.sum()
    }

    val input = readInput("inputs/Day14")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
