import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (winners, toCheck) = with(line.split(" | ")) {
                get(0).split(": ")[1].split(" ").filterNot { it.isEmpty() }.map { it.toInt() } to
                    get(1).split(" ").filterNot { it.isEmpty() }.map { it.toInt() }
            }

            2.0.pow(toCheck.count { it in winners }.toDouble() - 1).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        fun MutableMap<Int, Int>.addTo(index: Int, value: Int) {
            put(index, (get(index) ?: 0) + value)
        }

        val map = mutableMapOf<Int, Int>()
        input.forEachIndexed { index, line ->
            val (winners, toCheck) = with(line.split(" | ")) {
                get(0).split(": ")[1].split(" ").filterNot { it.isEmpty() }.map { it.toInt() } to
                    get(1).split(" ").filterNot { it.isEmpty() }.map { it.toInt() }
            }

            val copiesOfNextCard = toCheck.count { it in winners }
            map.addTo(index, 1)
            repeat(map[index] ?: 1) {
                repeat(copiesOfNextCard) {
                    map.addTo(index + 1 + it, 1)
                }
            }
        }

        return map.values.sum()
    }

    val input = readInput("inputs/Day04")
    part1(input).println()
    part2(input).println()
}
