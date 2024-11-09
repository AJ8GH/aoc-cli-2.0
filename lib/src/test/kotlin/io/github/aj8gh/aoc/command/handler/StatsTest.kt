package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class StatsTest : BaseTest() {

  @Test
  fun stats() {
    GIVEN
      .theTerminalIsMocked()

    WHEN
      .statsIsCalled(true)

    THEN
      .theStatsArePrintedAsExpected()
  }

  @Test
  fun statsWithFlagFalse() {
    WHEN
      .statsIsCalled(false)

    THEN
      .theTerminalWasNotInvoked()
  }
}
