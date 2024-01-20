package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.util.D1
import com.github.aj8gh.aoc.util.L1
import com.github.aj8gh.aoc.util.Y21
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File

class ExampleKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun createExample(year: Int, day: Int, level: Int) {
    givenCurrentYearDayAndLevelAre(year, day, level)
    andTodaysReadmeIsCached(html())
    andNoExampleExistsForToday()

    whenCreateExampleIsCalled()

    thenTodaysExamplesAreCreatedAsExpected(expectedExamples())
  }

  private fun html() = read("${HTML_DIR}y${year()}/d${day()}.html")

  private fun expectedExamples() = File("${EXAMPLE_DIR}y${year()}/d${day()}").listFiles()!!

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(Y21, D1, L1)
    )
  }
}
