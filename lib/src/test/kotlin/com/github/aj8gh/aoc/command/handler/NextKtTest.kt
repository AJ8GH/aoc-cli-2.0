package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.util.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

private const val SECOND_YEAR = FIRST_YEAR + 1
private const val SECOND_DAY = FIRST_DAY + 1

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
    thenTheFollowingMessageIsEchoed(
      expectedMessage(next, expectedYear, expectedDay, expectedLevel))
  }

  private fun expectedMessage(next: Boolean, year: Int, day: Int, level: Int) =
    EMPTY_MESSAGE.takeUnless { next } ?: getEchoMessage(year, day, level)

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(
        true,
        FIRST_YEAR, FIRST_DAY, LEVEL_1,
        FIRST_YEAR, FIRST_DAY, LEVEL_2
      ),
      Arguments.of(
        false,
        FIRST_YEAR, FIRST_DAY, LEVEL_1,
        FIRST_YEAR, FIRST_DAY, LEVEL_1
      ),
      Arguments.of(
        true,
        FIRST_YEAR, FIRST_DAY, LEVEL_2,
        FIRST_YEAR, SECOND_DAY, LEVEL_1
      ),
      Arguments.of(
        true,
        FIRST_YEAR, LAST_DAY, LEVEL_2,
        SECOND_YEAR, FIRST_DAY, LEVEL_1
      ),
      Arguments.of(
        true,
        LATEST_YEAR, LAST_DAY, LEVEL_2,
        LATEST_YEAR, LAST_DAY, LEVEL_2
      ),
    )
  }
}
