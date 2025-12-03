package template

import utilities.*
import java.io.File

private fun main() {
    val input = File(
        "src/main/kotlin/template/Input-test.txt"
//        "src/main/kotlin/template/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun String.parse() = run {
//    val (aInput, bInput) = this.split("\n\n")
    this.intLines()
//    this.longLines()
//    this.charGrid()
//    this.lines().map { it.toCharArray().toList() }
//    lateinit var currentNode: Node
//    this.lines().mapIndexed { rowIndex, row ->
//        row.toCharArray().toList().mapIndexed { colIndex, c ->
//            if (c == '^') {
//                currentNode = Node(rowIndex, colIndex)
//                '.'
//            } else c
//        }
//    }
//    this.lines().map { line ->
//        val matches = """(.*) to (.*) = ([0-9]+)""".toRegex().find(line)!!
//        println(
//            setOf(matches.groupValues[1], matches.groupValues[2], matches.groupValues[3].toInt()),
//        )
//    }
//    this.lines().sumOf { line ->
//        """mul\(([0-9]{1,3}),([0-9]{1,3})\)""".toRegex().findAll(line).map {
//            it.groupValues[1].toInt() to it.groupValues[2].toInt()
//        }.sumOf { (a, b) ->
//            a * b
//        }
//    }
//    this.lines().map { line ->
//        """p=([0-9]+),([0-9]+) v=([\-0-9]+),([\-0-9]+)""".toRegex().find(line)!!.groupValues
//            .drop(1).map {
//                it.toInt()
//            }
//    }
//    this.lines().forEach { line ->
//        """[0-9]+""".toRegex().findAll(line).map(MatchResult::value).map { it.toInt() }
//    }
}

private fun part1(input: String): Int {
    val parsedInput = input.parse()
    return 0
}

private fun part2(input: String): Int {
    val parsedInput = input.parse()
    return 0
}