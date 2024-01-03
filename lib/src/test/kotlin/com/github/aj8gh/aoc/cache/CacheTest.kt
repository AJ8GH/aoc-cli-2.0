package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.command.handler.BaseTest
import com.github.aj8gh.aoc.command.handler.CORRECT
import com.github.aj8gh.aoc.command.handler.NOT_CACHED
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

private const val ANSWER = "321"

class CacheManagerKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun cache(year: Int, day: Int, level: Int) {
    givenCurrentYearDayAndLevelAre(year, day, level)
    assertEquals(NOT_CACHED, checkAnswer(ANSWER))
    cache(ANSWER)
    assertEquals(CORRECT, checkAnswer(ANSWER))
  }

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(15, 1, 2),
      Arguments.of(16, 1, 1),
      Arguments.of(17, 1, 1),
    )
  }
}
