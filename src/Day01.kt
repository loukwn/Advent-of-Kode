fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (first, last) = line.first { it.isDigit() }.toString() to line.last { it.isDigit() }.toString()
            "$first$last".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val stringDigits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        return input.sumOf { line ->

            val firstDigitIndex = line.indexOfFirst { it.isDigit() }
            val lastDigitIndex = line.indexOfLast { it.isDigit() }

            val stringDigitMatches = arrayListOf<Pair<String, Int>>()

            stringDigits.forEach {
                val firstOccurenceIndex = line.indexOf(it)
                val lastOccurenceIndex = line.lastIndexOf(it)

                stringDigitMatches.add(it to firstOccurenceIndex)
                if (firstOccurenceIndex != lastOccurenceIndex) {
                    stringDigitMatches.add(it to lastOccurenceIndex)
                }
            }

            val sortedStringDigits = stringDigitMatches.filterNot { it.second == -1 }.sortedBy { it.second }
            val firstStringDigitInLine = sortedStringDigits.firstOrNull()
            val lastStringDigitInLine = sortedStringDigits.lastOrNull()

            val firstDigit = if (firstStringDigitInLine == null || (firstDigitIndex < firstStringDigitInLine.second && firstDigitIndex != -1)) {
                line[firstDigitIndex]
            } else {
                stringDigits.indexOf(firstStringDigitInLine.first) + 1
            }

            val lastDigit = if (lastStringDigitInLine == null || (lastDigitIndex > lastStringDigitInLine.second)) {
                line[lastDigitIndex]
            } else {
                stringDigits.indexOf(lastStringDigitInLine.first) + 1
            }

            "$firstDigit$lastDigit".toInt()
        }
    }

    val input = readInput("inputs/Day01")
    part1(input).println()
    part2(input).println()
}
