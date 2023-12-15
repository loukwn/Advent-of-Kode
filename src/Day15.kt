import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {
        return input.first().split(",").sumOf {
            it.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }.toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val hashmap = mutableMapOf<Int, MutableList<Pair<String, Int>>>()

        input.first().split(",").forEach { op ->
            val label = op.takeWhile { it != '-' && it != '=' }
            val box = label.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }.toInt()
            if (hashmap.containsKey(box)) {
                val labelInBox = hashmap[box]!!.firstOrNull { it.first == label }
                if (labelInBox != null) {
                    if (op.last() == '-') {
                        hashmap[box]!!.remove(labelInBox)
                    } else {
                        hashmap[box]!!.replaceAll { if (it.first == label) label to op[op.lastIndex].digitToInt() else it }
                    }
                } else {
                    if (op.last().isDigit()) {
                        hashmap[box]!!.add(label to op[op.lastIndex].digitToInt())
                    }
                }
            } else {
                if (op.last().isDigit()) {
                    hashmap[box] = mutableListOf(label to op[op.lastIndex].digitToInt())
                }
            }
        }

        return hashmap.asIterable()
            .sumOf { entry -> (entry.key + 1) * (entry.value.foldIndexed(0) { i, acc, fl -> acc + (i + 1) * fl.second }) }
    }

    val input = readInput("inputs/Day15")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
