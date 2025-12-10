package y2025.d10

import utilities.*
import java.io.File
import kotlin.collections.sumOf

private fun main() {
    val input = File(
//        "src/main/kotlin/y2025/d10/Input-test.txt"
        "src/main/kotlin/y2025/d10/Input.txt"
    ).readText(Charsets.UTF_8)

    printRun(::part1, input)
    printRun(::part2, input)
}

private data class MachineLine(
    val targetState: Long,
    val buttonWirings: List<List<Int>>,
    val buttonMasks: List<Long>,
    val joltageRequirements: List<Int>,
)

private fun String.parse() = run {
    this.lines().map { line ->
        val parts = line.split(" ")
        var targetState = 0L
        parts[0].drop(1).dropLast(1).toCharArray().map { it == '#' }.forEachIndexed { index, isOn ->
            if (isOn) targetState = targetState or (1L shl index)
        }

        val buttonWirings = parts.drop(1).dropLast(1).map { it.drop(1).dropLast(1).split(",").map { it.toInt() } }
        val buttonMasks = buttonWirings.map { indices ->
            var mask = 0L
            for (idx in indices) {
                mask = mask or (1L shl idx)
            }
            mask
        }

        val joltageRequirement = parts.last().drop(1).dropLast(1).split(",").map { it.toInt() }
        MachineLine(targetState, buttonWirings, buttonMasks, joltageRequirement)
    }
}

private fun part1(input: String): Int {
    val parsedInput = input.parse()
    return parsedInput.sumOf { machine ->
        val queue = ArrayDeque<Pair<Long, Int>>()
        val visited = HashSet<Long>()

        queue.add(0L to 0)
        visited.add(0L)
        while (queue.isNotEmpty()) {
            val (currentState, presses) = queue.removeFirst()

            if (currentState == machine.targetState) {
                return@sumOf presses
            }

            for (buttonMask in machine.buttonMasks) {
                val nextState = currentState xor buttonMask

                if (visited.add(nextState)) {
                    queue.add(nextState to presses + 1)
                }
            }
        }
        throw Exception("Solution not found")
    }
}

private fun part2(input: String): Long {
    val parsedInput = input.parse()
    return parsedInput.sumOf { machine ->
        val numButtons = machine.buttonWirings.size
        val numTargets = machine.joltageRequirements.size

        val currentTargets = machine.joltageRequirements.toIntArray()
        val buttonValues = Array(numButtons) { -1 }

        val targetToButtons = Array(numTargets) { IntArray(0) }
        for (r in 0 until numTargets) {
            val affectedBy = ArrayList<Int>()
            for (b in 0 until numButtons) {
                if (machine.buttonWirings[b].contains(r)) {
                    affectedBy.add(b)
                }
            }
            targetToButtons[r] = affectedBy.toIntArray()
        }

        val buttonToTargets = machine.buttonWirings
        var minTotalPresses = Long.MAX_VALUE

        fun solve(currentPressCount: Long) {
            if (currentPressCount >= minTotalPresses) return

            var bestTarget = -1
            var minUnknowns = Int.MAX_VALUE

            for (r in 0 until numTargets) {
                if (currentTargets[r] < 0) return

                var unknownCount = 0
                for (btn in targetToButtons[r]) {
                    if (buttonValues[btn] == -1) unknownCount++
                }

                if (unknownCount == 0) {
                    if (currentTargets[r] != 0) return
                } else {
                    if (unknownCount < minUnknowns) {
                        minUnknowns = unknownCount
                        bestTarget = r
                    }
                }
            }

            if (bestTarget == -1) {
                minTotalPresses = currentPressCount
                return
            }

            val buttonsInTarget = targetToButtons[bestTarget]
            val unknownBtnIndices = buttonsInTarget.filter { buttonValues[it] == -1 }

            if (minUnknowns == 1) {
                val forcedBtn = unknownBtnIndices[0]
                val needed = currentTargets[bestTarget]

                if (needed < 0) throw Exception("impossible condition")

                buttonValues[forcedBtn] = needed
                val affectedTargets = buttonToTargets[forcedBtn]
                for (r in affectedTargets) currentTargets[r] -= needed

                solve(currentPressCount + needed)

                for (r in affectedTargets) currentTargets[r] += needed
                buttonValues[forcedBtn] = -1
            } else {
                val branchBtn = unknownBtnIndices.maxByOrNull { buttonToTargets[it].size }!!
                val affectedTargets = buttonToTargets[branchBtn]

                val limit = currentTargets[bestTarget]

                for (k in 0..limit) {
                    buttonValues[branchBtn] = k
                    for (r in affectedTargets) currentTargets[r] -= k

                    solve(currentPressCount + k)
                    for (r in affectedTargets) currentTargets[r] += k
                }
                buttonValues[branchBtn] = -1
            }
        }

        solve(0L)
        if (minTotalPresses == Long.MAX_VALUE) throw Exception("Solution not found")
        else minTotalPresses
    }
}
