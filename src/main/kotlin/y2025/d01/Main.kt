package y2025.d01

import utilities.*
import java.io.File

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d01/Input-test.txt"
        "src/main/kotlin/y2025/d01/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun part1(input: String): Int {
    val rotations = input.lines().map { line ->
        val matches = """([LR])([0-9]*)""".toRegex().find(line)!!
        matches.groupValues[1] to matches.groupValues[2].toInt()
    }
    var dial = 50
    var password = 0
    rotations.forEach{ (dir, click) ->
        if (dir == "L") dial -= click
        else dial += click

        while (dial >= 100) dial -= 100
        while (dial < 0) dial += 100

        if (dial == 0) password++
    }
    return password
}

private fun part2(input: String): Int {
    val rotations = input.lines().map { line ->
        val matches = """([LR])([0-9]*)""".toRegex().find(line)!!
        matches.groupValues[1] to matches.groupValues[2].toInt()
    }
    var dial = 50
    var password = 0
    rotations.forEach{ (dir, click) ->
        val oldDial = dial
        if (dir == "L") {
            dial -= click
            if (oldDial == 0 && dial < 0) {
                password--
            }
            while (dial < 0) {
                dial += 100
                password++
            }
            if (dial == 0) {
                password++
            }
        } else {
            dial += click
            while (dial >= 100) {
                dial -= 100
                password++
            }
        }
    }
    return password
}