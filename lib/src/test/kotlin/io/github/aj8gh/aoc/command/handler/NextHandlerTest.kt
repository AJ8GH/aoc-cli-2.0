package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.KT_PROFILE
import io.github.aj8gh.aoc.test.getEchoMessage
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
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

private val LATEST_YEAR = 23

class NextHandlerTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun nextTest(
    year: Int,
    day: Int,
    level: Int,
    expectedYear: Int,
    expectedDay: Int,
    expectedLevel: Int,
    expectedMessage: String,
  ) {

    GIVEN
      .currentYearDayAndLevelAre(year = year, day = day, level = level)

    WHEN
      .theAppIsRunWithArg(NEXT_SHORT)

    THEN
      .currentYearDayAndLevelAre(expectedYear, expectedDay, expectedLevel)
      .theFollowingMessageIsEchoed(expectedMessage)
  }


  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(
        Y15, D1, L1,
        Y15, D1, L2,
        expectedMessage(Y15, D1, L2),
      ),
      Arguments.of(
        Y15, D1, L2,
        Y15, D2, L1,
        expectedMessage(Y15, D2, L1),
      ),
      Arguments.of(
        Y15, D25, L2,
        Y16, D1, L1,
        expectedMessage(Y16, D1, L1)
      ),
      Arguments.of(
        LATEST_YEAR, D25, L2,
        LATEST_YEAR, D25, L2,
        "You're already as far as you can go!"
      ),
    )

    private fun expectedMessage(year: Int, day: Int, level: Int) =
      getEchoMessage(year, day, level, KT_PROFILE)
  }
}
