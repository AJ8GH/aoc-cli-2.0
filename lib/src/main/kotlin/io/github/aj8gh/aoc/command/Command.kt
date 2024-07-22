package io.github.aj8gh.aoc.command

import com.github.ajalt.clikt.completion.CompletionCandidates
import io.github.aj8gh.aoc.util.toCandidates

enum class Command(
  val names: Array<String>,
  val help: String,
  val completion: CompletionCandidates.Fixed? = null,
) {
  YEAR(
    arrayOf(YEAR_SHORT, YEAR_LONG),
    YEAR_DESCRIPTION,
    toCandidates(YEAR_RANGE),
  ),
  DAY(
    arrayOf(DAY_SHORT, DAY_LONG),
    DAY_DESCRIPTION,
    toCandidates(DAY_RANGE),
  ),
  LEVEL(
    arrayOf(LEVEL_SHORT, LEVEL_LONG),
    LEVEL_DESCRIPTION,
    toCandidates(LEVEL_RANGE),
  ),
  NEXT(
    arrayOf(NEXT_SHORT, NEXT_LONG),
    NEXT_DESCRIPTION,
  ),
  ECHO(
    arrayOf(ECHO_SHORT, ECHO_LONG),
    ECHO_DESCRIPTION,
  ),
  CREATE(
    arrayOf(CREATE_SHORT, CREATE_LONG),
    CREATE_DESCRIPTION,
  ),
  ANSWER(
    arrayOf(ANSWER_SHORT, ANSWER_LONG),
    ANSWER_DESCRIPTION,
  )
}
