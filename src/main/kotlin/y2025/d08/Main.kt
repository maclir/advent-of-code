package y2025.d08

import utilities.*
import java.io.File
import kotlin.collections.fold

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d08/Input-test.txt"
        "src/main/kotlin/y2025/d08/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun String.parse() = run {
    this.intLines(",").map {
        val (a, b, c) = it.map { it.toDouble() }
        Triple(a, b, c)
    }
}

private fun part1(input: String): Long {
    val parsedInput = input.parse()
    val combinations = parsedInput.combinations(2).sortedBy { (o1, o2) ->
        o1.calculateDistance(o2)
    }

    val circuits = mutableListOf(mutableListOf<Triple<Double, Double, Double>>())
    repeat(10) { i ->
        val (t1, t2) = combinations[i]
        val i1 = circuits.indexOfFirst { it.contains(t1) }
        val i2 = circuits.indexOfFirst { it.contains(t2) }
        if (i1 >= 0 && i2 >= 0) {
            if (i1 == i2) return@repeat
            circuits[i1].addAll(circuits.removeAt(i2))
        } else if (i1 >= 0) {
            circuits[i1].add(t2)
        } else if (i2 >= 0) {
            circuits[i2].add(t1)
        } else {
            circuits.add(mutableListOf(t1, t2))
        }
    }

    return circuits.map { it.size }.sortedDescending().take(3).fold(1L) { acc, lng ->
        acc * lng
    }
}

private fun part2(input: String): Long {
    val parsedInput = input.parse()
    val combinations = parsedInput.combinations(2).sortedBy { (o1, o2) ->
        o1.calculateDistance(o2)
    }

    val circuits = mutableListOf(mutableListOf<Triple<Double, Double, Double>>())
    for (combination in combinations) {
        val (t1, t2) = combination
        val i1 = circuits.indexOfFirst { it.contains(t1) }
        val i2 = circuits.indexOfFirst { it.contains(t2) }
        if (i1 >= 0 && i2 >= 0) {
            if (i1 == i2) continue
            circuits[i1].addAll(circuits[i2])
            if (circuits[i1].size == parsedInput.size) {
                return (t1.first * t2.first).toLong()
            }
            circuits.removeAt(i2)
        } else if (i1 >= 0) {
            circuits[i1].add(t2)
            if (circuits[i1].size == parsedInput.size) {
                return (t1.first * t2.first).toLong()
            }
        } else if (i2 >= 0) {
            circuits[i2].add(t1)
            if (circuits[i2].size == parsedInput.size) {
                return (t1.first * t2.first).toLong()
            }
        } else {
            circuits.add(mutableListOf(t1, t2))
        }
    }

    return 0L
}