package io.github.aj8gh.aoc.command.handler

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import io.mockk.mockk
import kotlin.test.Test

class StatsTest : BaseTest() {

  private val terminal = mockk<Terminal>(relaxUnitFun = true)

  @Test
  fun stats() {
    GIVEN
      .theTerminalIsMocked(terminal)

    WHEN
      .statsIsCalled(true, terminal)

    THEN
      .theStatsArePrintedAsExpected(terminal)
  }

  @Test
  fun statsWithFlagFalse() {
    WHEN
      .statsIsCalled(false, terminal)

    THEN
      .theTerminalWasNotInvoked(terminal)
  }
}
