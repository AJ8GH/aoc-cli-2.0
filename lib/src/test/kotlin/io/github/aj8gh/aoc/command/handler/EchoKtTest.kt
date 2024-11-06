package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.Y15
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

const val EXPECTED_VERBOSE_MESSAGE = """AoC Properties
---
active: kt
url: http://localhost
session: session

Active Profile
---
name: "kt"
language: "kt"
ide: "idea"
writeAnswerInCode: true
files:
  projectHome: "src/test/resources/home/project/"
  mainDir: "src/main/kotlin/io/github/aj8gh/aoc/"
  testDir: "src/test/kotlin/io/github/aj8gh/aoc/"
  mainFilePrefix: "Day"
  testFilePrefix: "Day"
  testFileSuffix: "Test"
  resourcesDir: "src/main/resources/"
  resourcesSubDir: ""
  modulePrefix: "y-"
  yearPrefix: "y"
  dayPrefix: "d"
current:
  year: 15
  day: 1
  level: 1"""


class EchoKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun echoTest(echo: Boolean, expected: String, verbose: Boolean) {
    whenEchoCurrentIsCalledFor(echo, verbose)

    thenTheFollowingMessageIsEchoed(expected)
  }

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(false, EMPTY_MESSAGE, false),
      Arguments.of(false, EMPTY_MESSAGE, true),
      Arguments.of(true, getEchoMessage(Y15, D1, L1, KT_PROFILE), false),
      Arguments.of(true, EXPECTED_VERBOSE_MESSAGE, true),
    )
  }
}
