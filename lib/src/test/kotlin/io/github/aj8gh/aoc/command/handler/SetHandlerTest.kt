package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.EMPTY_MESSAGE
import io.github.aj8gh.aoc.test.KT_PROFILE
import io.github.aj8gh.aoc.test.getEchoMessage
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

    year?.let {
      args.add(YEAR_SHORT)
      args.add(it.toString())
    }
    day?.let {
      args.add(DAY_SHORT)
      args.add(it.toString())
    }
    level?.let {
      args.add(LEVEL_SHORT)
      args.add(it.toString())
    }

    WHEN
      .theAppIsRunWithArgs(args)

    THEN
      .currentYearDayAndLevelAre(expectedYear, expectedDay, expectedLevel)
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
    )
  }
}
