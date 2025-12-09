package y2025.d09

import utilities.*
import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d09/Input-test.txt"
        "src/main/kotlin/y2025/d09/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun String.parse() = run {
    this.nodeLines(",")
}

private fun part1(input: String): Long {
    val parsedInput = input.parse()
    return parsedInput.combinations(2).map { (r1, r2) ->
        (abs(r1.row - r2.row) + 1).toLong() * abs((r1.col - r2.col) + 1)
    }.maxOf { it }
}

private fun part2(input: String): Long {
    val parsedInput = input.parse()
    var previousNode = parsedInput.last()
    val lines = mutableListOf<Line>()
    for (node in parsedInput) {
        lines.add(Line(previousNode, node))
        previousNode = node
    }
    val cachedInPerimeter = { node: Node ->
        lines.count {
            Line(
                node,
                Node(node.row, 0)
            ).intersects(it)
        } % 2 != 0
    }.cache()

    val (r1, r2) = parsedInput.combinations(2)
        .sortedByDescending { (r1, r2) ->
            (abs(r1.row - r2.row) + 1).toLong() * abs((r1.col - r2.col) + 1)
        }
        .first { (r1, r2) ->
            val minRow = min(r1.row, r2.row)
            val maxRow = max(r1.row, r2.row)
            val minCol = min(r1.col, r2.col)
            val maxCol = max(r1.col, r2.col)
            if (!cachedInPerimeter(Node((maxRow + minRow) / 2, (maxCol + minCol) / 2))) {
                return@first false
            }
            val rectLines = listOf(
                Line(Node(minRow + 1, minCol + 1), Node(maxRow - 1, minCol + 1)),
                Line(Node(minRow + 1, minCol + 1), Node(minRow + 1, maxCol - 1)),
                Line(Node(maxRow - 1, maxCol - 1), Node(maxRow - 1, minCol + 1)),
                Line(Node(maxRow - 1, maxCol - 1), Node(minRow + 1, maxCol - 1)),
            )
            !lines.any { line ->
                rectLines.any { rectLine ->
                    line.intersects(rectLine)
                }
            }
        }
    return (abs(r1.row - r2.row) + 1).toLong() * abs((r1.col - r2.col) + 1)
}

private data class Line(val n1: Node, val n2: Node) {
    fun isHorizontal() = n1.row == n2.row
    fun minRow() = min(n1.row, n2.row)
    fun maxRow() = max(n1.row, n2.row)
    fun minCol() = min(n1.col, n2.col)
    fun maxCol() = max(n1.col, n2.col)

    fun intersects(other: Line): Boolean {
        val h1 = isHorizontal()
        val h2 = other.isHorizontal()
        return if (h1 != h2) {
            val hLine = if (h1) this else other
            val vLine = if (h1) other else this

            return vLine.n1.col in hLine.minCol()..hLine.maxCol() &&
                    hLine.n1.row in vLine.minRow()..vLine.maxRow()
        } else if (h1) {
            n1.row == other.n1.row &&
                    max(minCol(), other.minCol()) <= min(maxCol(), other.maxCol())
        } else {
            // Both vertical: same column and overlapping rows
            n1.col == other.n1.col &&
                    max(minRow(), other.minRow()) <= min(maxRow(), other.maxRow())
        }
    }
}

private fun Node.onLine(line: Line) = if (line.isHorizontal()) {
    row == line.n1.row && col in line.minCol()..line.maxCol()
} else {
    col == line.n1.col && row in line.minRow()..line.maxRow()
}