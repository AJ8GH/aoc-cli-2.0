package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D12
import io.github.aj8gh.aoc.command.D13
import io.github.aj8gh.aoc.command.D2
import io.github.aj8gh.aoc.command.D25
import io.github.aj8gh.aoc.command.DAY_SHORT
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.command.LEVEL_SHORT
import io.github.aj8gh.aoc.command.Y15
import io.github.aj8gh.aoc.command.Y16
import io.github.aj8gh.aoc.command.Y23
import io.github.aj8gh.aoc.command.Y24
import io.github.aj8gh.aoc.command.Y25
import io.github.aj8gh.aoc.command.Y26
import io.github.aj8gh.aoc.command.YEAR_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.EMPTY_MESSAGE
import io.github.aj8gh.aoc.test.KT_PROFILE
import io.github.aj8gh.aoc.test.getEchoMessage
import io.github.aj8gh.aoc.test.getInvalidDayEchoMessage
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SetHandlerTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun setTest(year: Int?, day: Int?, level: Int?, message: String) {
    val expectedYear = year ?: Y15
    val expectedDay = day ?: D1
    val expectedLevel = level ?: L1
    val args: MutableList<String> = mutableListOf()

    year?.let { args.addAll(listOf(YEAR_SHORT, it.toString())) }
    day?.let { args.addAll(listOf(DAY_SHORT, it.toString())) }
    level?.let { args.addAll(listOf(LEVEL_SHORT, it.toString())) }

    WHEN
      .theAppIsRunWithArgs(args)

    THEN
      .currentYearDayAndLevelAre(expectedYear, expectedDay, expectedLevel)
      .theFollowingMessageIsEchoed(message)
  }

  @ParameterizedTest
  @MethodSource("invalidInputProvider")
  fun setTestInvalidInputs(
    year: Int?,
    day: Int?,
    level: Int?,
    currentYear: Int,
    currentDay: Int,
    currentLevel: Int,
    message: String,
  ) {
    val args: MutableList<String> = mutableListOf()
    year?.let { args.addAll(listOf(YEAR_SHORT, it.toString())) }
    day?.let { args.addAll(listOf(DAY_SHORT, it.toString())) }
    level?.let { args.addAll(listOf(LEVEL_SHORT, it.toString())) }

    GIVEN
      .currentYearDayAndLevelAre(currentYear, currentDay, currentLevel)

    WHEN
      .theAppIsRunWithArgs(args)

    THEN
      .currentYearDayAndLevelAre(currentYear, currentDay, currentLevel)
      .theFollowingMessageIsEchoed(message)
  }

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(Y16, D2, L2, getEchoMessage(Y16, D2, L2, KT_PROFILE)),
      Arguments.of(null, D2, L2, getEchoMessage(Y15, D2, L2, KT_PROFILE)),
      Arguments.of(Y16, null, L2, getEchoMessage(Y16, D1, L2, KT_PROFILE)),
      Arguments.of(Y16, D2, null, getEchoMessage(Y16, D2, L1, KT_PROFILE)),
      Arguments.of(Y16, null, null, getEchoMessage(Y16, D1, L1, KT_PROFILE)),
      Arguments.of(null, null, L2, getEchoMessage(Y15, D1, L2, KT_PROFILE)),
      Arguments.of(null, D2, null, getEchoMessage(Y15, D2, L1, KT_PROFILE)),
      Arguments.of(null, null, null, EMPTY_MESSAGE),
      Arguments.of(Y24, D25, null, getEchoMessage(Y24, D25, L1, KT_PROFILE)),
      Arguments.of(Y25, D12, null, getEchoMessage(Y25, D12, L1, KT_PROFILE)),
    )

    @JvmStatic
    private fun invalidInputProvider() = listOf(
      Arguments.of(
        Y25,
        D13,
        null,
        Y24,
        D1,
        L1,
        "${getInvalidDayEchoMessage(Y25, D13)}\n${getEchoMessage(Y24, D1, L1, KT_PROFILE)}"
      ),
      Arguments.of(
        Y25,
        D25,
        null,
        Y25,
        D12,
        L1,
        "${getInvalidDayEchoMessage(Y25, D25)}\n${getEchoMessage(Y25, D12, L1, KT_PROFILE)}"
      ),
      Arguments.of(
        Y26,
        D13,
        null,
        Y15,
        D2,
        L2,
        "${getInvalidDayEchoMessage(Y26, D13)}\n${getEchoMessage(Y15, D2, L2, KT_PROFILE)}"
      ),
      Arguments.of(
        null,
        D13,
        null,
        Y25,
        D2,
        L2,
        "${getInvalidDayEchoMessage(Y25, D13)}\n${getEchoMessage(Y25, D2, L2, KT_PROFILE)}"
      ),
      Arguments.of(
        Y25,
        null,
        null,
        Y23,
        D13,
        L2,
        "${getInvalidDayEchoMessage(Y25, D13)}\n${getEchoMessage(Y23, D13, L2, KT_PROFILE)}"
      ),
    )
  }
}
