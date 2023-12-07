import kotlin.time.measureTimedValue

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val cards = arrayOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val cardsWithJoker = arrayOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

    fun part1(input: List<String>): Int {
        val handTypeMap: MutableMap<Int, List<Pair<String, Int>>> = mutableMapOf()

        input.forEach { line ->
            val hand = line.substring(0, 5)
            val bet = line.substringAfter(" ").trim().toInt()
            val handInSetSize = hand.toSet().size

            val type = when {
                handInSetSize == 1 -> 6
                handInSetSize == 2 && hand.any { c -> hand.count { it == c } == 4 } -> 5
                handInSetSize == 2 && hand.any { c ->
                    hand.count { it == c } == 3 && hand.replace(c.toString(), "").toSet().size == 1
                } -> 4

                handInSetSize == 3 && hand.any { c -> hand.count { it == c } == 3 } -> 3
                handInSetSize == 3 -> 2
                handInSetSize == 4 -> 1
                else -> 0
            }

            handTypeMap[type] = (handTypeMap[type] ?: emptyList()).plus(hand to bet)
        }

        return handTypeMap.toSortedMap().values.asIterable().fold(0 to 1) { (score, rank), hands ->
            if (hands.size == 1) {
                score + rank * hands[0].second to rank + 1
            } else {
                val valueSortedHands =
                    hands.sortedBy { it.first.map { cards.indexOf(it) + 1 }.joinToString { it.toHexString() } }
                score + valueSortedHands.foldIndexed(0) { handIndexInType, typeScore, (_, bet) ->
                    typeScore + bet * (handIndexInType + rank)
                } to rank + hands.size
            }
        }.first
    }

    fun part2(input: List<String>): Int {
        val handTypeMap: MutableMap<Int, List<Pair<String, Int>>> = mutableMapOf()

        input.forEach { line ->
            val hand = line.substring(0, 5)
            val bet = line.substringAfter(" ").trim().toInt()
            val handInSet = hand.toSet()
            val handInSetSize = handInSet.size

            val type = when {
                handInSetSize == 1 -> 6
                handInSetSize == 2 && hand.any { c -> hand.count { it == c } == 4 } -> {
                    if (hand.contains('J')) {
                        6
                    } else {
                        5
                    }
                }

                handInSetSize == 2 && hand.any { c ->
                    hand.count { it == c } == 3 && hand.replace(c.toString(), "").toSet().size == 1
                } -> {
                    if (hand.contains('J')) {
                        6
                    } else {
                        4
                    }
                }

                handInSetSize == 3 && hand.any { c -> hand.count { it == c } == 3 } -> {
                    when (hand.count { it == 'J' }) {
                        1 -> 5
                        2 -> 6
                        3 -> 5
                        else -> 3
                    }
                }

                handInSetSize == 3 -> {
                    when (hand.count { it == 'J' }) {
                        1 -> 4
                        2 -> 5
                        else -> 2
                    }
                }

                handInSetSize == 4 -> {
                    when (hand.count { it == 'J' }) {
                        1 -> 3
                        2 -> 3
                        else -> 1
                    }
                }

                else -> {
                    if (hand.contains('J')) {
                        1
                    } else {
                        0
                    }
                }
            }

            handTypeMap[type] = (handTypeMap[type] ?: emptyList()).plus(hand to bet)
        }

        return handTypeMap.toSortedMap().values.asIterable().fold(0 to 1) { (score, rank), hands ->
            if (hands.size == 1) {
                score + rank * hands[0].second to rank + 1
            } else {
                val valueSortedHands =
                    hands.sortedBy { it.first.map { cardsWithJoker.indexOf(it) + 1 }.joinToString { it.toHexString() } }
                score + valueSortedHands.foldIndexed(0) { handIndexInType, typeScore, (_, bet) ->
                    typeScore + bet * (handIndexInType + rank)
                } to rank + hands.size
            }
        }.first
    }

    val input = readInput("inputs/Day07")

    measureTimedValue { part1(input) }.println()
    measureTimedValue { part2(input) }.println()
}
