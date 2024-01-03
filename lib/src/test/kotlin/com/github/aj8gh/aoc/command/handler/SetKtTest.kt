package com.github.aj8gh.aoc.command.handler

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SetKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun setTest(year: Int?, day: Int?, level: Int?) {
    val expectedYear = year ?: DEFAULT_YEAR
    val expectedDay = day ?: DEFAULT_DAY
    val expectedLevel = level ?: DEFAULT_LEVEL

    whenSetIsCalledFor(year = year, day = day, level = level)
    thenCurrentYearDayAndLevelAre(expectedYear, expectedDay, expectedLevel)
    thenTheFollowingMessageIsEchoed(
      getEchoMessage(expectedYear, expectedDay, expectedLevel))
  }

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(16, 2, 2),
      Arguments.of(null, null, null),
      Arguments.of(null, 2, 2),
      Arguments.of(16, null, 2),
      Arguments.of(16, 2, null),
      Arguments.of(16, null, null),
      Arguments.of(null, null, 2),
      Arguments.of(null, 2, null),
    )
  }
}
