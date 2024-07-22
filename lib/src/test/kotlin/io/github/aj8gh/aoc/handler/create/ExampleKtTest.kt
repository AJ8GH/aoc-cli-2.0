package io.github.aj8gh.aoc.handler.create

import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.properties.day
import io.github.aj8gh.aoc.properties.year
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File

class ExampleKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun createExample(year: Int, day: Int) {
    givenCurrentYearDayAndLevelAre(year, day)
    andTodaysReadmeIsCached(html())
    andNoExampleExistsForToday()

    whenCreateExampleIsCalled()

    thenTodaysExampleIsCreatedAsExpected(expectedExamples())
  }

  private fun expectedExamples() = File("${EXAMPLE_DIR}y${year()}/d${day()}/example.txt")

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      // Arguments.of(Y15, D1),
      // Arguments.of(Y15, D2),
      // Arguments.of(Y15, D3),
      // Arguments.of(Y15, D4),
      // Arguments.of(Y15, D5),
      // Arguments.of(Y15, D6),
      // Arguments.of(Y15, D7),
      // Arguments.of(Y15, D8),
      Arguments.of(Y15, D9),

      Arguments.of(Y21, D1),
      Arguments.of(Y21, D2),
      Arguments.of(Y21, D3),
      Arguments.of(Y21, D4),
      Arguments.of(Y21, D5),
      Arguments.of(Y21, D6),
      Arguments.of(Y21, D7),
      Arguments.of(Y21, D8),
      Arguments.of(Y21, D9),
      Arguments.of(Y21, D10),
      Arguments.of(Y21, D11),
      // Arguments.of(Y21, D12),
      Arguments.of(Y21, D13),
      Arguments.of(Y21, D14),
      Arguments.of(Y21, D15),
      // Arguments.of(Y21, D16),

      Arguments.of(Y22, D1),
      Arguments.of(Y22, D2),
      Arguments.of(Y22, D3),
      Arguments.of(Y22, D4),
      Arguments.of(Y22, D5),
      Arguments.of(Y22, D6),
      Arguments.of(Y22, D7),
      Arguments.of(Y22, D8),
      Arguments.of(Y22, D9),
      Arguments.of(Y22, D10),
      Arguments.of(Y22, D11),
      Arguments.of(Y22, D12),
      Arguments.of(Y22, D13),
      Arguments.of(Y22, D14),
      Arguments.of(Y22, D15),
      Arguments.of(Y22, D16),

      Arguments.of(Y23, D1),
      Arguments.of(Y23, D2),
      Arguments.of(Y23, D3),
      Arguments.of(Y23, D4),
      Arguments.of(Y23, D5),
      Arguments.of(Y23, D6),
    )
  }
}
