import kotlin.time.measureTimedValue

fun main() {
    data class BeamTip(val position: Pair<Int, Int>, val direction: Direction)

    fun solve(
        positions: MutableMap<Pair<Int, Int>, Pair<Char, Boolean>>,
        startingBeamTip: BeamTip,
    ): Map<Pair<Int, Int>, Pair<Char, Boolean>> {
        var beamTips = listOf(startingBeamTip)
        val beamTipsHashes = mutableListOf<Int>()
        while (beamTipsHashes.toSet().size == beamTipsHashes.size) {
            val newBeamTipList = mutableListOf<BeamTip>()
            beamTips.forEach { beamTip ->
                val upPosition = BeamTip(
                    beamTip.position.first - 1 to beamTip.position.second,
                    Direction.Up,
                )
                val downPosition = BeamTip(
                    beamTip.position.first + 1 to beamTip.position.second,
                    Direction.Down,
                )

                val leftPosition = BeamTip(
                    beamTip.position.first to beamTip.position.second - 1,
                    Direction.Left,
                )

                val rightPosition = BeamTip(
                    beamTip.position.first to beamTip.position.second + 1,
                    Direction.Right,
                )

                val beamTipsToAdd = when (positions[beamTip.position]?.first) {
                    '|' -> {
                        when (beamTip.direction) {
                            Direction.Up -> listOf(upPosition)
                            Direction.Down -> listOf(downPosition)
                            Direction.Left, Direction.Right -> {
                                listOf(
                                    upPosition,
                                    downPosition,
                                )
                            }
                        }
                    }

                    '-' -> {
                        when (beamTip.direction) {
                            Direction.Left -> listOf(leftPosition)
                            Direction.Right -> listOf(rightPosition)
                            Direction.Up, Direction.Down -> {
                                listOf(
                                    leftPosition,
                                    rightPosition,
                                )
                            }
                        }
                    }

                    '\\' -> {
                        when (beamTip.direction) {
                            Direction.Up -> listOf(leftPosition)
                            Direction.Down -> listOf(rightPosition)
                            Direction.Left -> listOf(upPosition)
                            Direction.Right -> listOf(downPosition)
                        }
                    }

                    '/' -> {
                        when (beamTip.direction) {
                            Direction.Up -> listOf(rightPosition)
                            Direction.Down -> listOf(leftPosition)
                            Direction.Left -> listOf(downPosition)
                            Direction.Right -> listOf(upPosition)
                        }
                    }

                    '.' -> {
                        when (beamTip.direction) {
                            Direction.Up -> listOf(upPosition)
                            Direction.Down -> listOf(downPosition)
                            Direction.Left -> listOf(leftPosition)
                            Direction.Right -> listOf(rightPosition)
                        }
                    }

                    else -> listOf()
                }
                newBeamTipList.addAll(beamTipsToAdd)

                if (positions.containsKey(beamTip.position)) {
                    positions[beamTip.position] = positions[beamTip.position]!!.first to true
                }
            }

            beamTips = newBeamTipList.toSet().toList()
            beamTipsHashes.add(beamTips.hashCode())
        }

        return positions
    }

    fun part1(input: List<String>): Int {
        val positions = mutableMapOf<Pair<Int, Int>, Pair<Char, Boolean>>()

        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { colIndex, item ->
                positions[rowIndex to colIndex] = item to false
            }
        }

        return solve(positions, BeamTip(0 to 0, Direction.Right)).count { it.value.second }
    }

    fun part2(input: List<String>): Int {
        val positions = mutableMapOf<Pair<Int, Int>, Pair<Char, Boolean>>()

        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { colIndex, item ->
                positions[rowIndex to colIndex] = item to false
            }
        }

        val startingBeamTips = buildList {
            repeat(input.size) {
                add(BeamTip(0 to it, Direction.Down))
                add(BeamTip(input.size - 1 to it, Direction.Up))
                add(BeamTip(it to 0, Direction.Right))
                add(BeamTip(it to input.size - 1, Direction.Left))
            }
        }
            .map {
                solve(
                    buildMap { positions.forEach { t, u -> put(t, u) } }.toMutableMap(),
                    it,
                ).count { it.value.second }
            }

        return startingBeamTips.max()
    }

    val input = readInput("inputs/Day16")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
