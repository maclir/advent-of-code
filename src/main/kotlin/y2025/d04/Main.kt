package y2025.d04

import utilities.*
import java.io.File

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d04/Input-test.txt"
        "src/main/kotlin/y2025/d04/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun String.parse() = run {
    this.mutableCharGrid()
}

private fun part1(input: String): Int {
    val map = input.parse()
    var count = 0
    map.forEachNode { node, c ->
        if (c == '@') {
            if (node.surrounding().count { sNode ->
                    map.atNodeSafe(sNode) == '@'
                } < 4) count++
        }
    }

    return count
}

private fun part2(input: String): Int {
    val map = input.parse()
    var count = 0
    var changed: Boolean
    do {
        changed = false
        map.forEachNode { node, c ->
            if (c == '@') {
                if (node.surrounding().count { sNode ->
                        map.atNodeSafe(sNode) == '@'
                    } < 4) {
                    changed = true
                    map.setNode(node, '.')
                    count++
                }
            }
        }
    } while (changed)

    return count
}