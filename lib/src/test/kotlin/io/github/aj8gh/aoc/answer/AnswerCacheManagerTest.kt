package io.github.aj8gh.aoc.answer

import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.cache.answer.AnswerCacheManager
import io.github.aj8gh.aoc.command.handler.CORRECT
import io.github.aj8gh.aoc.command.handler.INCORRECT
import io.github.aj8gh.aoc.command.handler.TOO_HIGH
import io.github.aj8gh.aoc.command.handler.TOO_LOW
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

private const val ANSWER = "321"

class AnswerCacheManagerTest : BaseTest() {

  private val subject = AnswerCacheManager()

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun cache(year: Int, day: Int, level: Int, expectedResponse: String, answer: String) {
    GIVEN
      .currentYearDayAndLevelAre(year, day, level)
      .todaysAnswerIsNotCached(subject, answer)

    WHEN
      .theAnswerIsCached(subject, ANSWER)

    THEN
      .theExpectedCheckCacheResponseIsReturned(subject, expectedResponse, answer)
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
