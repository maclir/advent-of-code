package y2025.d11

import utilities.*
import java.io.File

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d11/Input-test.txt"
        "src/main/kotlin/y2025/d11/Input.txt"
    ).readText(Charsets.UTF_8)

//    printRun(::part1, input)
    printRun(::part2, input)
}

private fun String.parse() = run {
    this.lines().associate { line ->
        val (name, outputs) = line.split(": ")
        name to outputs.split(" ")
    }
}

private fun part1(input: String): Int {
    val map = input.parse()

    fun countValidPath(start: String): Int {
        if (start == "out") return 1
        else {
            return map[start]!!.sumOf { output ->
                 countValidPath(output)
            }
        }
    }

    return countValidPath("you")
}

private fun part2(input: String): Long {
    val map = input.parse()
    lateinit var cachedCountValidPath: (String, Pair<Boolean, Boolean>) -> Long
    cachedCountValidPath = { start: String, visited: Pair<Boolean, Boolean> ->
        if (start == "out") if (visited.first && visited.second) 1
        else 0
        else map[start]!!.sumOf { output ->
            cachedCountValidPath(output, (visited.first || (output == "dac")) to (visited.second || (output == "fft")))
        }
    }.cache()

    return cachedCountValidPath("svr", false to false)
}