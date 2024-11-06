package io.github.aj8gh.aoc.command.handler

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.*
import io.mockk.mockk
import kotlin.test.Test

class StatsKtTest : BaseTest() {

  @Test
  fun stats() {
    val terminal = givenTheTerminalIsMocked()

    whenStatsIsCalled(true, terminal)

    thenTheStatsArePrintedAsExpected(terminal)
  }

  @Test
  fun statsWithFlagFalse() {
    val terminal = mockk<Terminal>()

    whenStatsIsCalled(false, terminal)

    thenTheTerminalWasNotInvoked(terminal)
  }
}
