package y2025.d07

import utilities.*
import java.io.File

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d07/Input-test.txt"
        "src/main/kotlin/y2025/d07/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private fun String.parse() = run {
    lateinit var currentNode: Node
    val map = this.lines().mapIndexed { rowIndex, row ->
        row.toCharArray().toList().mapIndexed { colIndex, c ->
            if (c == 'S') {
                currentNode = Node(rowIndex, colIndex)
                'S'
            } else c
        }.toMutableList()
    }
    currentNode to map
}

private fun part1(input: String): Int {
    val (startNode, map) = input.parse()
    var currentRow = startNode.row + 1
    while (currentRow < map.size) {
        for (col in 0 .. map[currentRow].lastIndex) {
            val currentNode = Node(currentRow, col)
            val above = map.atNodeSafe(currentNode.move(BaseDirection.UP))
            if (above == 'S' || above == '|') {
                if (map.atNode(currentNode) == '^') {
                    map.setNodeSafe(currentNode.move(BaseDirection.LEFT), '|')
                    map.setNodeSafe(currentNode.move(BaseDirection.RIGHT), '|')
                } else {
                    map.setNode(currentNode, '|')
                }
            }
        }
        currentRow++
    }
    var count = 0
    map.forEachNode { node, ch ->
        if (ch == '|' && map.atNodeSafe(node.move(BaseDirection.DOWN)) == '^') {
            count++
        }
    }
    return count
}

private fun part2(input: String): Long {
    val (startNode, map) = input.parse()
    var currentCounts = mutableMapOf(startNode to 1L)
    outerWhile@ while (true) {
        val nextCounts = mutableMapOf<Node, Long>()
        for ((node, count) in currentCounts) {
            val nodeBelow = node.move(BaseDirection.DOWN)
            if (!nodeBelow.isInMap(map)) break@outerWhile
            if (map.atNode(nodeBelow) == '^') {
                val right = nodeBelow.move(BaseDirection.RIGHT)
                val left = nodeBelow.move(BaseDirection.LEFT)

                nextCounts[right] = nextCounts.getOrDefault(right, 0L) + count
                nextCounts[left] = nextCounts.getOrDefault(left, 0L) + count
            } else {
                nextCounts[nodeBelow] = nextCounts.getOrDefault(nodeBelow, 0L) + count
            }
        }
        currentCounts = nextCounts
    }

    return currentCounts.values.sum()
}