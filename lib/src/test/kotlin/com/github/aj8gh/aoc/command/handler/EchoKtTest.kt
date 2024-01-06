package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.util.D1
import com.github.aj8gh.aoc.util.L1
import com.github.aj8gh.aoc.util.Y15
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class EchoKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun echoTest(echo: Boolean, expected: String) {
    whenEchoCurrentIsCalledFor(echo)

    thenTheFollowingMessageIsEchoed(expected)
  }

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(false, EMPTY_MESSAGE),
      Arguments.of(true, getEchoMessage(Y15, D1, L1)),
    )
  }
}
