package com.github.aj8gh.aoc.command.handler

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class EchoKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun echoTest(echo: Boolean, expected: String) {
    echoCurrent(echo)
    assertMessage(expected)
  }

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(false, ""),
      Arguments.of(true, "You are on year $DEFAULT_YEAR day $DEFAULT_DAY level $DEFAULT_LEVEL"),
    )
  }
}
