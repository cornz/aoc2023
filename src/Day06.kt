fun main() {


    fun calculateWays(time: Long, record: Long): Long {
        var ways = 0L
        for (speed in 0 until time) {
            val distance = speed * (time - speed)
            if (distance > record) {
                ways++
            }
        }
        return ways
    }


    fun part2(input: List<String>): Long {
        val raceTimes = listOf(input[0].split(":")[1].replace(" ", "").toLong())
        val raceDistances = listOf(input[1].split(":")[1].replace(" ", "").toLong())

        val totalWays = raceTimes.zip(raceDistances).map { (time, record) ->
            calculateWays(time, record)
        }.reduce { acc, ways -> acc * ways }

        return totalWays
    }

    fun part1(input: List<String>): Long {
        val raceTimes = input[0].split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        val raceDistances = input[1].split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }

        val totalWays = raceTimes.zip(raceDistances).map { (time, record) ->
            calculateWays(time, record)
        }.reduce { acc, ways -> acc * ways }

        return totalWays
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("input/Day06")
    part1(input).println()
    part2(input).println()


}
