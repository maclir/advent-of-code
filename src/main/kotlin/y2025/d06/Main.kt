package y2025.d06

import utilities.*
import java.io.File

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d06/Input-test.txt"
        "src/main/kotlin/y2025/d06/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun String.grid(delimiters: Regex) =
    this.lines().map { it.trim().split(delimiters) }

private fun String.parse() = run {
    val grid = this.grid("\\s+".toRegex())
    val operators = grid.last().map { it[0] }
    val allNumbers = grid.dropLast(1).map { it.map { it.toLong() } }
    operators.mapIndexed { index, operator ->
        operator to allNumbers.map { it[index] }
    }
}

private fun part1(input: String): Long {
    val parsedInput = input.parse()
    return parsedInput.sumOf { (operator, numbers) ->
        when (operator) {
            '+' -> numbers.sum()
            '*' -> numbers.fold(1L) { acc, lng -> acc * lng }
            else -> 0L
        }
    }
}

private fun part2(input: String): Long {
    val grid = input.charGrid()
    var colIndex = grid.maxOf { it.lastIndex }
    val lastRowIndex = grid.lastIndex
    var acc = 0L
    val accNumbers = mutableListOf<Int>()
    while (colIndex >= 0) {
        var numberString = ""
        for (rowIndex in 0 until lastRowIndex) {
            numberString += grid.atNodeOrDefault(Node(rowIndex, colIndex), "")
        }
        if (numberString.isBlank()) {
            colIndex--
            continue
        }
        accNumbers.add(numberString.trim().toInt())
        val operator = grid.atNodeOrDefault(Node(lastRowIndex, colIndex), ' ')
        if (operator != ' ') {
            acc += when (operator) {
                '+' -> accNumbers.sum().toLong()
                '*' -> accNumbers.fold(1L) { acc, lng -> acc * lng }
                else -> 0L
            }
            accNumbers.clear()
        }
        colIndex--
    }
    return acc
}