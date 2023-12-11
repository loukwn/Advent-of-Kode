import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTimedValue

fun main() {
    fun part1(input: List<String>): Int {
        val galaxies = mutableListOf<Pair<Int, Int>>()
        val rowHistogram = mutableMapOf<Int, Int>()
        val colHistogram = mutableMapOf<Int, Int>()

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, char ->
                if (char == '#') {
                    galaxies.add(rowIndex to colIndex)
                    rowHistogram[rowIndex] = rowHistogram[rowIndex]?.plus(1) ?: 1
                    colHistogram[colIndex] = colHistogram[colIndex]?.plus(1) ?: 1
                }
            }
        }

        val columnsWithNoGalaxies = input[0].indices.minus(colHistogram.keys)
        val rowsWithNoGalaxies = input.indices.minus(rowHistogram.keys)

        return galaxies.mapIndexed { index, galaxy ->
            galaxies.drop(index + 1).sumOf { galaxy2 ->
                val minRow = min(galaxy.first, galaxy2.first)
                val maxRow = max(galaxy.first, galaxy2.first)
                val minCol = min(galaxy.second, galaxy2.second)
                val maxCol = max(galaxy.second, galaxy2.second)

                val distance = maxCol - minCol + maxRow - minRow
                val emptyRowsInBetween = rowsWithNoGalaxies.count { it in minRow..maxRow }
                val emptyColsInBetween = columnsWithNoGalaxies.count { it in minCol..maxCol }

                distance + emptyColsInBetween + emptyRowsInBetween
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val galaxies = mutableListOf<Pair<Int, Int>>()
        val rowHistogram = mutableMapOf<Int, Int>()
        val colHistogram = mutableMapOf<Int, Int>()

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, char ->
                if (char == '#') {
                    galaxies.add(rowIndex to colIndex)
                    rowHistogram[rowIndex] = rowHistogram[rowIndex]?.plus(1) ?: 1
                    colHistogram[colIndex] = colHistogram[colIndex]?.plus(1) ?: 1
                }
            }
        }

        val columnsWithNoGalaxies = input[0].indices.minus(colHistogram.keys)
        val rowsWithNoGalaxies = input.indices.minus(rowHistogram.keys)

        return galaxies.mapIndexed { index, galaxy ->
            galaxies.drop(index + 1).sumOf { galaxy2 ->
                val minRow = min(galaxy.first, galaxy2.first)
                val maxRow = max(galaxy.first, galaxy2.first)
                val minCol = min(galaxy.second, galaxy2.second)
                val maxCol = max(galaxy.second, galaxy2.second)

                val distance = maxCol - minCol + maxRow - minRow
                val emptyRowsInBetween = rowsWithNoGalaxies.count { it in minRow..maxRow }
                val emptyColsInBetween = columnsWithNoGalaxies.count { it in minCol..maxCol }

                distance + emptyColsInBetween * 999_999L + emptyRowsInBetween * 999_999L
            }
        }.sum()
    }

    val input = readInput("inputs/Day11")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
