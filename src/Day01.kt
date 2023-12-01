fun main() {



    fun sumJoinDigits(input: String): Int {
        val numberMap = mapOf(
            "one" to 1, "two" to 2, "three" to 3, "four" to 4,
            "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9,
            "1" to 1, "2" to 2, "3" to 3, "4" to 4, "5" to 5, "6" to 6,
            "7" to 7, "8" to 8, "9" to 9
        )
        val first = numberMap[input.findAnyOf(numberMap.keys)!!.second]
        val second = numberMap[input.findLastAnyOf(numberMap.keys)!!.second]

        return (first.toString() + second.toString()).toInt()
    }


    fun getDigits(input: String): List<String> {
        return Regex("\\d")
            .findAll(input)
            .map { it.value }
            .toList()
    }

    fun joinDigits(input: List<String>): Int {
        return (input.first() + input.last()).toInt()
    }

    fun part1(input: List<String>): Int {
        return input.map { getDigits(it) }
            .map { joinDigits(it) }
            .sum()
    }

    fun part2(input: List<String>): Int {
       return input.map { sumJoinDigits(it) }
           .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day01_test")
    val testInput2 = readInput("input/Day01_test_2")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("input/Day01")
    val input2 = readInput("input/Day01_2")
    part1(input).println()
    part2(input2).println()


}
