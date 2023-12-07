enum class CardType(val rank: Int) {
    NONE(0),
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIRS(3),
    THREE_OF_KIND(4),
    FULL_HOUSE(5),
    FOUR_OF_KIND(6),
    FIVE_OF_KIND(7)
}

enum class Card(val value: Int) {
    _2(2), _3(3), _4(4), _5(5), _6(6), _7(7), _8(8), _9(9), T(10), J(11), Q(12), K(13), A(14);

    companion object {
        fun fromChar(c: Char): Card {
            val identifier = if(c.isDigit()) "_$c" else c.toString()
            return valueOf(identifier)
        }
    }
}


fun main() {

    data class HandValue(
        val hand: String,
        val value: Int,
        var type: CardType
    )


    fun evaluateHand(hand: HandValue): HandValue {
        val frequencies = hand.hand.groupingBy { it }.eachCount()

        val pairs = frequencies.count { it.value == 2 }
        val threes = frequencies.count { it.value == 3 }
        val fours = frequencies.count { it.value == 4 }
        val fives = frequencies.count { it.value == 5 }

        when {
            fives >= 1 -> hand.type = CardType.FIVE_OF_KIND
            fours >= 1 -> hand.type =CardType.FOUR_OF_KIND
            threes >= 1 && pairs >= 1 -> hand.type = CardType.FULL_HOUSE
            threes >= 1 -> hand.type = CardType.THREE_OF_KIND
            pairs >= 2 -> hand.type = CardType.TWO_PAIRS
            pairs >= 1 -> hand.type = CardType.ONE_PAIR
            else -> hand.type = CardType.HIGH_CARD
        }

        return hand
    }

    fun compareHands(hand1: HandValue, hand2: HandValue): Int {
        val sortedHand1 = evaluateHand(hand1)
        val sortedHand2 = evaluateHand(hand2)

        if (sortedHand1.type.rank > sortedHand2.type.rank) return 1
        if (sortedHand1.type.rank < sortedHand2.type.rank) return -1

        return 0
    }

    fun compareHandsEqual(hand1: HandValue, hand2: HandValue): Int {
        if (hand1.type.rank != hand2.type.rank) {
            return 0
        }
        val sortedHand1 = hand1.hand.toCharArray().map { Card.fromChar(it).value }
        val sortedHand2 = hand2.hand.toCharArray().map { Card.fromChar(it).value }

        for (i in sortedHand1.indices) {
            if (sortedHand1[i] > sortedHand2[i]) return 1
            if (sortedHand1[i] < sortedHand2[i]) return -1
        }

        return 0
    }

    fun sortHands(hands: List<HandValue>): List<HandValue> {
        return hands.sortedWith(Comparator(::compareHands))
    }

    fun sortHandsEqual(hands: List<HandValue>): List<HandValue> {
        return hands.sortedWith(Comparator(::compareHandsEqual))
    }

    fun getResult(hands: List<HandValue>): Int {
        var res = 0
        for (hand in hands.withIndex()) {
            val finalValue = hand.value.value
            val multiplier = hand.index+1
            res += (finalValue*multiplier)
        }

        return res
    }



    fun part2(input: List<String>): Int {
       return 0
    }

    fun part1(input: List<String>): Int {
        val hands = input.map { x -> HandValue(x.split(" ")[0], x.split(" ")[1].toInt(), CardType.NONE)}
        val handsSortedByRank = sortHands(hands)
        val handsSortedByValues = sortHandsEqual(handsSortedByRank)
        val res = getResult(handsSortedByValues)
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day07_test")
    check(part1(testInput) == 6440)
    //check(part2(testInput) == 0)

    val input = readInput("input/Day07")
    part1(input).println()
    //part2(input).println()


}
