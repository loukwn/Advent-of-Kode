fun main() {
    fun List<String>.itemAt(position: Pair<Int, Int>): Char {
        return this[position.first][position.second]
    }

    fun part1(input: List<String>): Int {
        fun hasSymbolAdjacentToIt(input: List<String>, rowIndex: Int, startColIndex: Int, endColIndex: Int): Boolean {
            val positionsToCheck = buildList {
                repeat(endColIndex - startColIndex + 3) {
                    // Add the row above and the row below
                    add(rowIndex - 1 to startColIndex - 1 + it)
                    add(rowIndex + 1 to startColIndex - 1 + it)
                }
                // Add the positions before and after the number
                add(rowIndex to startColIndex - 1)
                add(rowIndex to endColIndex + 1)
            }
                .filter { it.first in input.indices && it.second in input.indices }

            return positionsToCheck.any { with(input.itemAt(it)) { !isDigit() && this != '.' } }
        }
        return input.mapIndexed { rowIndex, row ->
            var sumOfRow = 0
            var numFoundAsString = ""
            var startIndexForNumFound = -1

            row.forEachIndexed { colIndex, c ->
                if (c.isDigit()) {
                    if (numFoundAsString.isEmpty()) {
                        startIndexForNumFound = colIndex
                    }
                    numFoundAsString += c

                    if (colIndex == row.length - 1) {
                        if (hasSymbolAdjacentToIt(input, rowIndex, startIndexForNumFound, colIndex - 1)) {
                            sumOfRow += numFoundAsString.toInt()
                        }
                    }
                } else {
                    if (numFoundAsString.isNotEmpty()) {
                        if (hasSymbolAdjacentToIt(input, rowIndex, startIndexForNumFound, colIndex - 1)) {
                            sumOfRow += numFoundAsString.toInt()
                        }
                    }
                    numFoundAsString = ""
                    startIndexForNumFound = -1
                }
            }
            sumOfRow
        }.sum()
    }

    fun part2(input: List<String>): Int {
        fun Pair<Int, Int>.getPositionToTheLeftOrNull(): Pair<Int, Int>? {
            return Pair(first, second - 1).takeIf { it.second >= 0 }
        }

        fun Pair<Int, Int>.getPositionToTheRightOrNull(maxIndex: Int): Pair<Int, Int>? {
            return Pair(first, second + 1).takeIf { it.second <= maxIndex }
        }

        fun getNumbersAdjacentToItOrNull(input: List<String>, rowIndex: Int, colIndex: Int): Pair<Int, Int>? {
            val numsFound = arrayListOf<Int>()

            val positionsToCheck = ArrayList(
                buildList {
                    repeat(3) {
                        // Add the row above and the row below
                        add(rowIndex - 1 to colIndex - 1 + it)
                        add(rowIndex + 1 to colIndex - 1 + it)
                    }
                    // Add the positions before and after the number
                    add(rowIndex to colIndex - 1)
                    add(rowIndex to colIndex + 1)
                }
                    .filter { it.first in input.indices && it.second in input.indices },
            )

            while (positionsToCheck.isNotEmpty()) {
                val position = positionsToCheck.first()
                val itemInPosition = input.itemAt(position)
                positionsToCheck.remove(position)

                if (itemInPosition.isDigit()) {
                    val numInString = StringBuilder(itemInPosition.toString())

                    var positionLeft = position.getPositionToTheLeftOrNull()
                    var positionRight = position.getPositionToTheRightOrNull(input.lastIndex)
                    do {
                        if (positionLeft != null) {
                            val leftItem = input.itemAt(positionLeft)
                            positionsToCheck.remove(positionLeft)

                            positionLeft = if (leftItem.isDigit()) {
                                numInString.insert(0, leftItem)
                                positionLeft.getPositionToTheLeftOrNull()
                            } else {
                                null
                            }
                        }

                        if (positionRight != null) {
                            val rightItem = input.itemAt(positionRight)
                            positionsToCheck.remove(positionRight)

                            positionRight = if (rightItem.isDigit()) {
                                numInString.append(rightItem)
                                positionRight.getPositionToTheRightOrNull(input.lastIndex)
                            } else {
                                null
                            }
                        }
                    } while (positionLeft != null || positionRight != null)

                    numsFound.add(numInString.toString().toInt())
                }
            }

            return numsFound.take(2).takeIf { it.size == 2 }?.zipWithNext()?.single()
        }

        return input.mapIndexed { rowIndex, row ->
            var sumOfRow = 0

            row.forEachIndexed { colIndex, c ->
                if (c != '*') return@forEachIndexed

                val (partNum1, partNum2) = getNumbersAdjacentToItOrNull(input, rowIndex, colIndex)
                    ?: return@forEachIndexed
                sumOfRow += partNum1 * partNum2
            }

            sumOfRow
        }.sum()
    }

    val input = readInput("inputs/Day03")
    part1(input).println()
    part2(input).println()
}
