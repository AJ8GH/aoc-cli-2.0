package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.KT_PROFILE
import io.github.aj8gh.aoc.test.getEchoMessage
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class EchoHandlerTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun echoTest(args: List<String>, expected: String) {
    WHEN
      .theAppIsRunWithArgs(args)

    THEN
      .theFollowingMessageIsEchoed(expected)
  }

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(listOf(ECHO_SHORT), getEchoMessage(Y15, D1, L1, KT_PROFILE)),
      Arguments.of(listOf(ECHO_SHORT, VERBOSE_SHORT), EXPECTED_VERBOSE_MESSAGE),
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
