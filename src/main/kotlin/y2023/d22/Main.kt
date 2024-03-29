package y2023.d22

import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

private fun main() {
    val input = File(
//        "src/main/kotlin/y2023/d22/Input-test.txt"
        "src/main/kotlin/y2023/d22/Input.txt"
    ).readText(Charsets.UTF_8)

    println("${
        measureTimeMillis {
            println(
                "part1: ${part1(input)}"
            )
        }
    } ms\n")

    println("${
        measureTimeMillis {
            println(
                "part2: ${part2(input)}"
            )

        }
    } ms")
}

private data class Node2D(val x: Int, val y: Int)
private data class Node3D(val x: Int, val y: Int, val z: Int)
private data class Brick(val start: Node3D, val end: Node3D) {
    val minX get() = min(start.x, end.x)
    val minY get() = min(start.y, end.y)
    val minZ get() = min(start.z, end.z)
    val maxX get() = max(start.x, end.x)
    val maxY get() = max(start.y, end.y)
    val maxZ get() = max(start.z, end.z)
}

private fun part1(input: String): Int {
    val highestZs = mutableMapOf<Node2D, Int>()
    val brickInCoordinate = mutableMapOf<Node3D, Brick>()

    val bricks = input.lines().map { line ->
        val (start, end) = line.split('~')
        val (startX, startY, startZ) = start.split(',').map { it.toInt() }
        val (endX, endY, endZ) = end.split(',').map { it.toInt() }
        Brick(Node3D(startX, startY, startZ), Node3D(endX, endY, endZ))
    }.sortedBy { it.minZ }.map { brick: Brick ->
        var brickLowestZ = 0
        for (x in brick.minX..brick.maxX) {
            for (y in brick.minY..brick.maxY) {
                val highestZ = highestZs[Node2D(x, y)] ?: continue
                brickLowestZ = max(brickLowestZ, highestZ + 1)
            }
        }
        val brickZDiff = abs(brick.start.z - brick.end.z)

        val newBrick = Brick(
            Node3D(brick.minX, brick.minY, brickLowestZ),
            Node3D(brick.maxX, brick.maxY, brickLowestZ + brickZDiff),
        )
        for (x in newBrick.minX..newBrick.maxX) {
            for (y in newBrick.minY..newBrick.maxY) {
                highestZs[Node2D(x, y)] = brickLowestZ + brickZDiff
                for (z in newBrick.minZ..newBrick.maxZ) {
                    brickInCoordinate[Node3D(x, y, z)] = newBrick
                }
            }
        }
        newBrick
    }

    val brickSupports = mutableMapOf<Brick, List<Brick>>()
    val brickSupportedBy = mutableMapOf<Brick, List<Brick>>()
    bricks.forEach { brick ->
        val higherZ = brick.maxZ + 1
        val lowerZ = brick.minZ - 1
        val thisBrickSupports = mutableListOf<Brick>()
        val thisBrickSupportedBy = mutableListOf<Brick>()
        for (x in brick.minX..brick.maxX) {
            for (y in brick.minY..brick.maxY) {
                brickInCoordinate[Node3D(x, y, higherZ)]?.let {
                    thisBrickSupports.add(it)
                }
                brickInCoordinate[Node3D(x, y, lowerZ)]?.let {
                    thisBrickSupportedBy.add(it)
                }
            }
        }
        brickSupports[brick] = thisBrickSupports
        brickSupportedBy[brick] = thisBrickSupportedBy
    }
    val safeToRemove = mutableSetOf<Brick>()
    bricks.forEach { brick ->
        val supports = brickSupports[brick]!!
        if (supports.isEmpty()) safeToRemove.add(brick)
        else {
            if (supports.all { upperBrick -> brickSupportedBy[upperBrick]!!.size > 1 }) {
                safeToRemove.add(brick)
            }
        }
    }
    println(safeToRemove.size)
    return bricks.filter { brick -> brickSupports[brick]!!.all { brickSupportedBy[it]!!.size > 1 } }.size
}

private fun part2(input: String): Int {
    return 0
}