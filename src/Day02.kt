fun main() {

    fun sumCubes(input: String, colorsWithNumber: String): Int {
        val gameNumber = input.split("Game ")[1].split(":")[0].toInt()
        val checks = colorsWithNumber.split(", ")
        val games  = input.replace(Regex("Game \\d+: "), "").split("; ")
        for (game in games) {
            for (check in checks) {
                val number = check.split(" ")[0].toInt()
                val color = check.split(" ")[1]
                var cubes = game.split(", ")
                var cubes2 = cubes.filter { x -> x.contains(color) }
                    .map { x -> x.replace(" "+color, "") }
                    .mapNotNull { it.toIntOrNull() }
                    .filter { x -> x > number }
                if (cubes2.isNotEmpty()) {
                    return 0
                }
            }
        }
        return gameNumber
    }

    fun multiplyCubes(input: String): Int {
        val reds = mutableListOf<Int>()
        val greens = mutableListOf<Int>()
        val blues = mutableListOf<Int>()
        val games  = input.replace(Regex("Game \\d+: "), "").split("; ")
        for (game in games) {
            for (color in listOf("red", "green", "blue")) {
                var cubes = game.split(", ")
                var cubes2 = cubes.filter { x -> x.contains(color) }
                    .map { x -> x.replace(" "+color, "") }
                    .mapNotNull { it.toIntOrNull() }
                if (cubes2.isNotEmpty()) {
                    when (color) {
                        "red" -> {
                            reds.add(cubes2[0])
                        }
                        "green" -> {
                            greens.add(cubes2[0])
                        }
                        "blue" -> {
                            blues.add(cubes2[0])
                        }
                    }
                }
            }
        }
        return reds.max() * greens.max() * blues.max()
    }


    fun part1(input: List<String>): Int {
        val gamesPossible = mutableListOf<Int>()
        for (row in input) {
            val gameNumber = sumCubes(row, "12 red, 13 green, 14 blue")
            gamesPossible.add(gameNumber)
        }
        return gamesPossible.sum()
    }

    fun part2(input: List<String>): Int {
        var solve = 0
        for (row in input) {
            solve += multiplyCubes(row)
        }

        return solve
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("input/Day02_test")
    check(part1(testInput) == 8)

    val testInput2 = readInput("input/Day02_test_2")
    check(part2(testInput2) == 2286)

    val input = readInput("input/Day02")
    part1(input).println()
    part2(input).println()
}
