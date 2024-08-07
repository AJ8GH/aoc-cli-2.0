package io.github.aj8gh.aoc.handler

import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.Y15
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
