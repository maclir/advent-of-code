package y2025.d03

import utilities.*
import java.io.File
import kotlin.math.max

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d03/Input-test.txt"
        "src/main/kotlin/y2025/d03/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun part1(input: String): Int {
    return input.lines().sumOf { line ->
        line.toCharArray().toList().combinations(2).maxOf { digits ->
            digits.joinToString("")
        }.toInt()
    }
}

private fun part2(input: String): Long {
    return input.lines().sumOf { line ->
        var newLine = line.toMutableList()
        val maxs = MutableList(12) {'0'}
        for (index in maxs.indices) {
            maxs[index] = newLine.dropLast(maxs.size - index - 1).max()
            newLine = newLine.drop(newLine.indexOf(maxs[index]) + 1).toMutableList()
        }
        (maxs.joinToString("")).toLong()
    }
}