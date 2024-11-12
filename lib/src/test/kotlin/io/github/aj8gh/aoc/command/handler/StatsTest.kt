package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.STATS_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class StatsTest : BaseTest() {

  @Test
  fun stats() {
    WHEN
      .theAppIsRunWithArg(STATS_SHORT)

    THEN
      .theStatsAreOutputToTheTerminal()
  }
}
