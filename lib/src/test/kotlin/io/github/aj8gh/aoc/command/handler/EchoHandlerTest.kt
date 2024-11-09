package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.EMPTY_MESSAGE
import io.github.aj8gh.aoc.test.KT_PROFILE
import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.Y15
import io.github.aj8gh.aoc.test.getEchoMessage
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class EchoHandlerTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun echoTest(echo: Boolean, expected: String, verbose: Boolean) {
    WHEN
      .echoIsCalledFor(echo, verbose)

    THEN
      .theFollowingMessageIsEchoed(expected)
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

private const val EXPECTED_VERBOSE_MESSAGE = """AoC Properties
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
