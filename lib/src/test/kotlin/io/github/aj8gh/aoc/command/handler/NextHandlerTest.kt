package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D12
import io.github.aj8gh.aoc.command.D2
import io.github.aj8gh.aoc.command.D25
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.command.NEXT_SHORT
import io.github.aj8gh.aoc.command.Y15
import io.github.aj8gh.aoc.command.Y16
import io.github.aj8gh.aoc.command.Y23
import io.github.aj8gh.aoc.command.Y25
import io.github.aj8gh.aoc.command.Y26
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.KT_PROFILE
import io.github.aj8gh.aoc.test.getEchoMessage
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

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
    dateTime: String
  ) {


    GIVEN
      .currentYearDayAndLevelAre(year = year, day = day, level = level)
      .currentDateTimeIs(dateTime)

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
        "2024-01-01T00:00:00Z",
      ),
      Arguments.of(
        Y15, D1, L2,
        Y15, D2, L1,
        expectedMessage(Y15, D2, L1),
        "2024-01-01T00:00:00Z",
      ),
      Arguments.of(
        Y15, D25, L2,
        Y16, D1, L1,
        expectedMessage(Y16, D1, L1),
        "2024-01-01T00:00:00Z",
      ),
      Arguments.of(
        Y23, D25, L2,
        Y23, D25, L2,
        "You're already as far as you can go!",
        "2024-01-01T00:00:00Z",
      ),
      Arguments.of(
        Y25, D12, L2,
        Y25, D12, L2,
        "You're already as far as you can go!",
        "2025-12-25T00:00:00Z",
      ),
      Arguments.of(
        Y26, D12, L2,
        Y26, D12, L2,
        "You're already as far as you can go!",
        "2027-01-25T00:00:00Z",
      ),
    )

    private fun expectedMessage(year: Int, day: Int, level: Int) =
      getEchoMessage(year, day, level, KT_PROFILE)
  }
}
