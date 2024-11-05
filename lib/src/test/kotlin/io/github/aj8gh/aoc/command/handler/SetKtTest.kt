package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SetKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun setTest(year: Int?, day: Int?, level: Int?, message: String) {
    val expectedYear = year?.mod(2000) ?: Y15
    val expectedDay = day ?: D1
    val expectedLevel = level ?: L1

    whenSetIsCalledFor(year = year, day = day, level = level)

    thenCurrentYearDayAndLevelAre(expectedYear, expectedDay, expectedLevel)
    andTheFollowingMessageIsEchoed(message)
  }

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(Y16, D2, L2, getEchoMessage(Y16, D2, L2, KT_PROFILE)),
      Arguments.of(2016, D2, L2, getEchoMessage(Y16, D2, L2, KT_PROFILE)),
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
