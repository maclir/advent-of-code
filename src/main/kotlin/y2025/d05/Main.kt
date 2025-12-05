package y2025.d05

import utilities.*
import java.io.File

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d05/Input-test.txt"
        "src/main/kotlin/y2025/d05/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun String.parse() = run {
    val (aInput, bInput) = this.split("\n\n")
    aInput.longLines("-") to bInput.singleLongLines()
}

private fun part1(input: String): Int {
    val (ranges, ingredients) = input.parse()
    return ingredients.count { ingredient ->
        ranges.any { range ->
            ingredient in range[0]..range[1]
        }
    }
}

private fun part2(input: String): Long {
    val (ranges, _) = input.parse()
    val merged = ranges.sortedBy { it[0] }.map { it.toMutableList() }.toMutableList()
    var index = 1
    while (index < merged.size) {
        if (merged[index][0] <= merged[index - 1][1]) {
            merged[index - 1][1] = merged[index - 1][1].coerceAtLeast(merged[index][1])
            merged.removeAt(index)
        } else {
            index++
        }
    }
    return merged.sumOf { range ->
        range[1] - range[0] + 1
    }
}