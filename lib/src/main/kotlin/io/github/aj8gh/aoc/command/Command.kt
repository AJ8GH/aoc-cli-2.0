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
  ),
  PROFILE(
    arrayOf(PROFILE_SHORT, PROFILE_LONG),
    PROFILE_DESCRIPTION,
  ),
  OPEN(
    arrayOf(OPEN_SHORT, OPEN_LONG),
    OPEN_DESCRIPTION,
  ),
  FILES(
    arrayOf(FILES_SHORT, FILES_LONG),
    FILES_DESCRIPTION,
  ),
  TOKEN(
    arrayOf(TOKEN_SHORT, TOKEN_LONG),
    TOKEN_DESCRIPTION,
  ),
  VERBOSE(
    arrayOf(VERBOSE_SHORT, VERBOSE_LONG),
    VERBOSE_DESCRIPTION,
  ),
  STATS(
    arrayOf(STATS_SHORT, STATS_LONG),
    STATS_DESCRIPTION,
  )
}
