package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.util.D1
import com.github.aj8gh.aoc.util.L1
import com.github.aj8gh.aoc.util.Y15
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SetKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun setTest(year: Int?, day: Int?, level: Int?, message: String) {
    val expectedYear = year ?: Y15
    val expectedDay = day ?: D1
    val expectedLevel = level ?: L1

    whenSetIsCalledFor(year = year, day = day, level = level)

    thenCurrentYearDayAndLevelAre(expectedYear, expectedDay, expectedLevel)
    andTheFollowingMessageIsEchoed(message)
  }

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(16, 2, 2, getEchoMessage(16, 2, 2)),
      Arguments.of(null, 2, 2, getEchoMessage(Y15, 2, 2)),
      Arguments.of(16, null, 2, getEchoMessage(16, D1, 2)),
      Arguments.of(16, 2, null, getEchoMessage(16, 2, L1)),
      Arguments.of(16, null, null, getEchoMessage(16, D1, L1)),
      Arguments.of(null, null, 2, getEchoMessage(Y15, D1, 2)),
      Arguments.of(null, 2, null, getEchoMessage(Y15, 2, L1)),
      Arguments.of(null, null, null, EMPTY_MESSAGE),
    )
  }
}
