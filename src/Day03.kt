fun main() {

    data class NumberWithIndices(
        val stringIndex: Int,
        val number: Int,
        val startIndex: Int,
        val endIndex: Int
    )

    data class StarWithIndices(
        val stringIndex: Int,
        val star: String,
        val startIndex: Int,
        val endIndex: Int
    )

    data class Coordinates(
        val y: Int,
        val x: Int
    )

    fun createCharArrayGrid(input: List<String>): Array<CharArray> {
        return input.map { it.toCharArray() }.toTypedArray()
    }

    fun findAllNumbersWithIndices(inputs: List<String>): List<NumberWithIndices> {
        val regex = "\\d+".toRegex()
        return inputs.flatMapIndexed { index, input ->
            regex.findAll(input).map {
                NumberWithIndices(index, it.value.toInt(), it.range.first, it.range.last)
            }
        }
    }

    fun findAllStarsWithIndices(inputs: List<String>): List<StarWithIndices> {
        val regex = "\\*".toRegex()
        return inputs.flatMapIndexed { index, input ->
            regex.findAll(input).map {
                StarWithIndices(index, it.value, it.range.first, it.range.last)
            }
        }
    }

    fun isPart(input: Char): Boolean {
        return !input.isDigit() && input != '.'
    }

    fun isDigit(input: Char): Boolean {
        return input.isDigit()
    }

    fun findNumbersByCoordinates(input: List<Coordinates>, check: List<NumberWithIndices>): Int {
        val results = mutableListOf<Int>()
        for (coord in input) {
            val result = check.filter { number -> number.stringIndex == coord.y }
                .filter { number -> (coord.x in number.startIndex .. number.endIndex) }
            val number = result[0].number
            if (!results.contains(number)) {
                results.add(number)
            }

        }

        return results.reduce { acc, i -> acc * i }
    }

    fun findAdjacentNumber(input: StarWithIndices, grid: Array<CharArray>, numberIndices: List<NumberWithIndices>): Int {
        var passes = mutableListOf<Coordinates>()
        val y = input.stringIndex
        val x1 = input.startIndex
        val x2 = input.endIndex
        for (i in x1 .. x2) {
            if (y > 0) {
                if (isDigit(grid[y - 1][i])) {
                    passes.add(Coordinates(y-1,i))
                }
                if (i > 0) {
                    if (isDigit(grid[y - 1][i - 1])) {
                        passes.add(Coordinates(y-1,i-1))
                    }
                    if (i < grid[y].size-1) {
                        if (isDigit(grid[y - 1][i + 1])) {
                            passes.add(Coordinates(y-1,i+1))
                        }
                    }
                }
            }
            if (y < grid[y].size-1) {
                if (isDigit(grid[y + 1][i])) {
                    passes.add(Coordinates(y+1,i))
                }
                if (i > 0)
                    if (isDigit(grid[y + 1][i - 1])) {
                        passes.add(Coordinates(y+1,i-1))
                    }
            }
            if (i < grid[y].size-1) {
                if (isDigit(grid[y + 1][i + 1])) {
                    passes.add(Coordinates(y+1,i+1))
                }
            }
            if (i > 0) {
                if (isDigit(grid[y][i - 1])) {
                    passes.add(Coordinates(y,i-1))
                }

            }
            if (i < grid[y].size-1) {
                if (isDigit(grid[y][i + 1])) {
                    passes.add(Coordinates(y,i+1))
                }
            }
        }


        if (passes.count() > 1) {
            return findNumbersByCoordinates(passes, numberIndices)
        }
        return 0
    }



    fun findAdjacentSymbol(input: NumberWithIndices, grid: Array<CharArray>): Int {
        val passes = mutableListOf(false)
        val y = input.stringIndex
        val x1 = input.startIndex
        val x2 = input.endIndex
        for (i in x1 .. x2) {
            if (y > 0) {
                passes.add(isPart(grid[y - 1][i]))
                if (i > 0) {
                    passes.add(isPart(grid[y - 1][i - 1]))
                }
                if (i < grid[y].size-1) {
                    passes.add(isPart(grid[y - 1][i + 1]))
                }
            }
            if (y < grid[y].size-1) {
                passes.add(isPart(grid[y + 1][i]))
                if (i > 0) {
                    passes.add(isPart(grid[y + 1][i - 1]))
                }
                if (i < grid[y].size-1) {
                    passes.add(isPart(grid[y + 1][i + 1]))
                }
            }
            if (i > 0) {
                passes.add(isPart(grid[y][i - 1]))
            }
            if (i < grid[y].size-1) {
                passes.add(isPart(grid[y][i + 1]))
            }
        }

        if (passes.contains(true)) {
            return input.number
        }
        return 0
    }






    fun part1(input: List<String>): Int {
        val numberIndices = findAllNumbersWithIndices(input)
        val grid = createCharArrayGrid(input)
        var sum = 0
        for (number in numberIndices) {
            val enginePart = findAdjacentSymbol(number, grid)
            sum += enginePart
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val numberIndices = findAllNumbersWithIndices(input)
        val starIndices = findAllStarsWithIndices(input)
        val grid = createCharArrayGrid(input)
        var sum = 0
        for (star in starIndices) {
            val enginePart = findAdjacentNumber(star, grid, numberIndices)
            sum += enginePart
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
   val testInput = readInput("input/Day03_test")
   check(part1(testInput) == 4361)

    val testInput2 = readInput("input/Day03_test")
    check(part2(testInput2) == 467835)

    val input = readInput("input/Day03")
    part1(input).println()
    part2(input).println()
}
