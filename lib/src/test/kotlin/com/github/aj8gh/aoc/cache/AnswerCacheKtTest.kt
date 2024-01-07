package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.BaseTest
import com.github.aj8gh.aoc.command.handler.*
import com.github.aj8gh.aoc.givenCurrentYearDayAndLevelAre
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

private const val ANSWER = "321"

class AnswerCacheKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun cacheIncorrect(year: Int, day: Int, level: Int, response: String, answer: String) {
    givenCurrentYearDayAndLevelAre(year, day, level)
    assertEquals(NOT_CACHED, checkAnswer(answer))
    cacheAnswer(ANSWER)
    assertEquals(response, checkAnswer(answer))
  }

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(15, 1, 1, CORRECT, ANSWER),
      Arguments.of(16, 1, 1, CORRECT, ANSWER),
      Arguments.of(17, 1, 1, CORRECT, ANSWER),
      Arguments.of(15, 1, 1, TOO_LOW, "320"),
      Arguments.of(16, 1, 1, TOO_HIGH, "322"),
      Arguments.of(17, 1, 1, INCORRECT, "abc"),
    )
  }
}
