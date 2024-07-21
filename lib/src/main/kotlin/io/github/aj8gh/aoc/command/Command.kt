package io.github.aj8gh.aoc.command

import com.github.ajalt.clikt.completion.CompletionCandidates
import io.github.aj8gh.aoc.util.*

enum class Command(
  val names: Array<String>,
  val help: String,
  val completion: CompletionCandidates.Fixed? = null,
) {
  YEAR(
    arrayOf("-y", "--year"),
    "New year value to set, must be between 15 and current year",
    rangeOf(Y15, latestYear()),
  ),
  DAY(
    arrayOf("-d", "--day"),
    "New day value to set, must be between 1 and 25",
    rangeOf(D1, D25),
  ),
  LEVEL(
    arrayOf("-l", "--level"),
    "New level value to set, must be 1 or 2",
    rangeOf(L1, L2),
  ),
  NEXT(
    arrayOf("-n", "--next"),
    "Advance to the next level",
  ),
  ECHO(
    arrayOf("-e", "--echo"),
    "Echo the current year, day and level",
  ),
  CREATE(
    arrayOf("-c", "--create"),
    "Create resources for current level",

    ),
  ANSWER(
    arrayOf("-a", "--answer"),
    "Submit answer for current level"
  )
}
