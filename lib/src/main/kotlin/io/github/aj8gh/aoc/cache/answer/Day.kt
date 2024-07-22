package io.github.aj8gh.aoc.cache.answer

import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2

private const val UNKNOWN_LEVEL = "Unknown level"

data class Day(
  var level1: String? = null,
  var level2: String? = null,
) {

  fun level(level: Int) = when (level) {
    L1 -> level1
    L2 -> level2
    else -> throw IllegalArgumentException(UNKNOWN_LEVEL)
  }

  fun save(level: Int, answer: String) = when (level) {
    L1 -> level1 = answer
    L2 -> level2 = answer
    else -> throw IllegalArgumentException(UNKNOWN_LEVEL)
  }

  fun clear() {
    level1 = null
    level2 = null
  }
}
