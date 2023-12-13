import kotlin.time.measureTimedValue

fun main() {
    fun offsetIterator(offsetMax: Int, numOfOffsets: Int, doOnOffset: (List<Int>) -> Unit) {
        val offsets = buildList { repeat(numOfOffsets) { add(0) } }.toMutableList()
        var counter = 0
        while (true) {
            val value = counter % (offsetMax + 1)
            if (value == 0 && counter != 0) {
                var offset = offsets.lastIndex
                offsets[offset] = 0
                offset--
                if (offset < 0) break
                while (offset >= 0 && offsets[offset] == offsetMax) {
                    offsets[offset] = 0
                    offset--
                }
                if (offset < 0) break
                offsets[offset]++
            } else {
                offsets[offsets.lastIndex] = value
            }
            counter++
            doOnOffset(offsets)
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val records = line.substringBefore(" ")
            val recordsNum = line.substringAfter(" ").split(',').map { it.toInt() }

            val recordsThatMatch = mutableListOf<String>()
            val offsetMax = records.length - (recordsNum.sum() + (recordsNum.count() - 1))

            offsetIterator(offsetMax, recordsNum.size) { offsets ->
                val recordCandidate = buildString {
                    recordsNum.forEachIndexed { index, i ->
                        repeat(offsets[index]) {
                            append('.')
                        }
                        repeat(i) {
                            append('#')
                        }
                        if (index != recordsNum.lastIndex) {
                            append('.')
                        }
                    }
                }.padEnd(records.length, '.')

                val recordCandidateIsValid = records.length == recordCandidate.length &&
                    records
                        .zip(recordCandidate)
                        .none { (a, b) -> (a == '.' && b == '#') || (a == '#' && b == '.') }

                if (recordCandidateIsValid) {
                    recordsThatMatch.add(recordCandidate)
                }
            }
            recordsThatMatch.toSet().count()
        }
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    val input = readInput("inputs/Day12")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
