import kotlin.time.measureTimedValue

fun main() {
    fun <T> List<T>.isPalindrome(): Boolean {
        return this.zip(this.reversed()).all { (a, b) -> a == b } && this.size % 2 == 0
    }

    fun part1(input: List<String>): Int {
        val instances = mutableListOf<List<String>>()
        val instance = mutableListOf<String>()
        for (line in input) {
            if (line.isNotEmpty()) {
                instance.add(line)
            } else {
                instances.add(instance.toList())
                instance.clear()
            }
        }
        instances.add(instance)

        return instances.sumOf { rowsAsList ->
            val colsAsList = buildList {
                repeat(rowsAsList[0].length) { col ->
                    val s =
                        buildString {
                            repeat(rowsAsList.size) { row ->
                                append(rowsAsList[row][col])
                            }
                        }
                    add(s)
                }
            }

            repeat(rowsAsList.size - 1) {
                val listDroppingFromStart = rowsAsList.drop(it)
                if (listDroppingFromStart.isPalindrome()) {
                    return@sumOf 100 * (listDroppingFromStart.size / 2 + it)
                }

                val listDroppingFromEnd = rowsAsList.dropLast(it)
                if (listDroppingFromEnd.isPalindrome()) {
                    return@sumOf 100 * listDroppingFromEnd.size / 2
                }
            }

            repeat(colsAsList.size - 1) {
                val listDroppingFromStart = colsAsList.drop(it)
                if (listDroppingFromStart.isPalindrome()) {
                    return@sumOf listDroppingFromStart.size / 2 + it
                }

                val listDroppingFromEnd = colsAsList.dropLast(it)
                if (listDroppingFromEnd.isPalindrome()) {
                    return@sumOf listDroppingFromEnd.size / 2
                }
            }
            0
        }
    }

    fun part2(input: List<String>): Int {
        fun MutableList<String>.invertAt(strIndex: Int, charIndex: Int) {
            val invertedChar = if (this[strIndex][charIndex] == '#') '.' else '#'
            this[strIndex] =
                this[strIndex].take(charIndex) + invertedChar + this[strIndex].takeLast(this[strIndex].length - charIndex - 1)
        }

        val instances = mutableListOf<List<String>>()
        val instance = mutableListOf<String>()
        for (line in input) {
            if (line.isNotEmpty()) {
                instance.add(line)
            } else {
                instances.add(instance.toList())
                instance.clear()
            }
        }
        instances.add(instance)

        return instances.sumOf {
            val rowsAsList = it.toMutableList()
            val colsAsList = buildList {
                repeat(rowsAsList[0].length) { col ->
                    val s =
                        buildString {
                            repeat(rowsAsList.size) { row ->
                                append(rowsAsList[row][col])
                            }
                        }
                    add(s)
                }
            }.toMutableList()

            val results = mutableListOf(0)
            var smudge: Pair<Int, Int>? = null
            do {
                smudge?.let { (row, col) ->
                    rowsAsList.invertAt(row, col)
                    colsAsList.invertAt(col, row)
                }

                var found = false
                for (i in rowsAsList.indices) {
                    val listDroppingFromStart = rowsAsList.drop(i)
                    if (listDroppingFromStart.isPalindrome()) {
                        val toAdd = 100 * (listDroppingFromStart.size / 2 + i)
                        if (toAdd != results.last()) {
                            results.add(toAdd)
                            found = true
                            break
                        }
                    }

                    val listDroppingFromEnd = rowsAsList.dropLast(i)
                    if (listDroppingFromEnd.isPalindrome()) {
                        val toAdd = 100 * listDroppingFromEnd.size / 2
                        if (toAdd != results.last()) {
                            results.add(toAdd)
                            found = true
                            break
                        }
                    }
                }

                if (!found) {
                    for (i in colsAsList.indices) {
                        val listDroppingFromStart = colsAsList.drop(i)
                        if (listDroppingFromStart.isPalindrome()) {
                            val toAdd = listDroppingFromStart.size / 2 + i
                            if (toAdd != results.last()) {
                                results.add(toAdd)
                                break
                            }
                        }

                        val listDroppingFromEnd = colsAsList.dropLast(i)
                        if (listDroppingFromEnd.isPalindrome()) {
                            val toAdd = listDroppingFromEnd.size / 2
                            if (toAdd != results.last()) {
                                results.add(toAdd)
                                break
                            }
                        }
                    }
                }

                smudge?.let { (row, col) ->
                    rowsAsList.invertAt(row, col)
                    colsAsList.invertAt(col, row)
                }
                smudge = if (smudge == null) {
                    0 to 0
                } else {
                    if (smudge.second == colsAsList.size - 1) {
                        smudge.first + 1 to 0
                    } else {
                        smudge.first to smudge.second + 1
                    }
                }
            } while (results.size != 3)

            results[2]
        }
    }

    val input = readInput("inputs/Day13")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
