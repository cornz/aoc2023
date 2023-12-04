fun main() {


    fun doubleNumber(initialValue: Int, times: Int): Int {
        if (times == 0) {
            return 0
        }
        var result = initialValue
        for (i in 1 until times) {
            result *= 2
        }
        return result
    }

    fun matchNumbers(input: String): Int {
        val winners = input.split(" | ")[0].split(": ")[1].split(" ")
            .map { x -> x.replace(" ", "") }.filter { it.isNotEmpty() }
        val myNumbers = input.split(" | ")[1].split(" ")
            .map { x -> x.replace(" ", "") }.filter { it.isNotEmpty() }
        val count = myNumbers.count { it in winners }

        return doubleNumber(1,count)
    }

    fun matchNumbersCount(input: String): Int {
        val winners = input.split(" | ")[0].split(": ")[1].split(" ")
            .map { x -> x.replace(" ", "") }.filter { it.isNotEmpty() }
        val myNumbers = input.split(" | ")[1].split(" ")
            .map { x -> x.replace(" ", "") }.filter { it.isNotEmpty() }
        val count = myNumbers.count { it in winners }

        return count
    }


    fun processCards(input: List<String>): Int {
        val cardCounts = IntArray(input.size) { 1 }

        for ((index, cardData) in input.withIndex()) {
            val matchCount = matchNumbersCount(cardData)
            for (i in 1..matchCount) {
                val subsequentCardIndex = index + i
                if (subsequentCardIndex < cardCounts.size) {
                    cardCounts[subsequentCardIndex] += cardCounts[index]
                }
            }
        }

        return cardCounts.sum()
    }




    fun part2(input: List<String>): Int {
        val res = processCards(input)
        return res
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (row in input) {
            sum += matchNumbers(row)
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("input/Day04")
    part1(input).println()
    part2(input).println()


}
