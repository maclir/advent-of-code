package y2025.d02

import utilities.*
import java.io.File

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d02/Input-test.txt"
        "src/main/kotlin/y2025/d02/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun part1(input: String): Long {
    val ranges = input.split(',').map { line ->
        val matches = """([0-9]+)-([0-9]+)""".toRegex().find(line)!!
        matches.groupValues[1].toLong() to matches.groupValues[2].toLong()
    }

    var sum = 0L
    ranges.forEach { (from, to) ->
        for (l in from..to) {
            val lString = l.toString()
            val length = lString.length
            if (length % 2 != 0) continue
            if (lString.drop(length / 2) != lString.dropLast(length / 2)) continue
            sum += l
        }
    }
    return sum
}

private fun part2(input: String): Long {
    val ranges = input.split(',').map { line ->
        val matches = """([0-9]+)-([0-9]+)""".toRegex().find(line)!!
        matches.groupValues[1].toLong() to matches.groupValues[2].toLong()
    }

    var sum = 0L
    ranges.forEach { (from, to) ->
        for (l in from..to) {
            val lString = l.toString()
            val length = lString.length
            var chunkSize = 1
            while (chunkSize <= length / 2) {
                val chunks = lString.chunked(chunkSize)
                if (chunks.all { it == chunks.first() }) {
                    sum += l
                    break
                }
                chunkSize++
            }
        }
    }
    return sum
}