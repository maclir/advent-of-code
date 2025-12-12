package y2025.d12

import utilities.*
import java.io.File

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d12/Input-test.txt"
        "src/main/kotlin/y2025/d12/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private data class Region(
    val size: Size,
    val targetPresentQuantity: List<Int>,
) {
    data class Size(val width: Int, val height: Int) {
        val area: Int get() = width * height
    }
}

private fun String.parse() = run {
    val inputs = this.split("\n\n")
    val regions = inputs.last().lines().map { line ->
        val (sizeP, targetPresentNumbersP) = line.split(": ")
        val (sizeW, sizeH) = sizeP.split("x").map(String::toInt)
        Region(Region.Size(sizeW, sizeH), targetPresentNumbersP.split(" ").map(String::toInt))
    }

    val presents = inputs.dropLast(1).map {
        it.lines().drop(1).map { it.toCharArray().toList() }
    }

    presents to regions
}

private fun part1(input: String): Int {
    val (presents, regions) = input.parse()

    return regions.count { region ->
        val neededArea = region.targetPresentQuantity.sumOf { targetQuantity ->
            9 * targetQuantity
        }
        region.size.area >= neededArea
    }
}

private fun part2(input: String): Int {
    val parsedInput = input.parse()
    return 0
}