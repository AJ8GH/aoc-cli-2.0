package io.github.aj8gh.aoc.handler

import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.util.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

private val CLOCK = Clock.fixed(
  Instant.parse("2023-12-26T00:00:00Z"),
  ZoneId.systemDefault()
)

private val LATEST_YEAR = latestYear(CLOCK)

class NextKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun nextTest(
    next: Boolean,
    year: Int,
    day: Int,
    level: Int,
    expectedYear: Int,
    expectedDay: Int,
    expectedLevel: Int,
  ) {
    givenCurrentYearDayAndLevelAre(year = year, day = day, level = level)

    whenNextIsCalledFor(next)

    thenCurrentYearDayAndLevelAre(expectedYear, expectedDay, expectedLevel)
    andTheFollowingMessageIsEchoed(expectedMessage(next, expectedYear, expectedDay, expectedLevel))
  }

  private fun expectedMessage(next: Boolean, year: Int, day: Int, level: Int) =
    EMPTY_MESSAGE.takeUnless { next } ?: getEchoMessage(year, day, level)

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(
        true,
        Y15, D1, L1,
        Y15, D1, L2
      ),
      Arguments.of(
        false,
        Y15, D1, L1,
        Y15, D1, L1
      ),
      Arguments.of(
        true,
        Y15, D1, L2,
        Y15, D2, L1
      ),
      Arguments.of(
        true,
        Y15, D25, L2,
        Y16, D1, L1
      ),
      Arguments.of(
        true,
        LATEST_YEAR, D25, L2,
        LATEST_YEAR, D25, L2
      ),
    )
  }
}
