package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.io.mainFile
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.io.testFile
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.util.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

private const val EXPECTED_CODE_DIR = "${TEST_RESOURCES_ROOT}expected-code/"
private const val MAIN_FILE = "main.txt"
private const val TEST_FILE = "test.txt"

class CodeKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun createCode(year: Int, day: Int, level: Int) {
    givenCurrentYearDayAndLevelAre(year, day, level)
    andCodeFilesDoNotExistForToday()

    whenCreateCodeIsCalled()

    thenMainFileIsCreatedAsExpected(expectedMainFile())
    andTestFileIsCreatedAsExpected(expectedTestFile())
  }

  @Test
  fun createCodeFilesExist() {
    givenCurrentYearDayAndLevelAre(Y15, D2, L1)
    andCodeFilesExistForToday()
    val existingMain = mainFile().readText()
    val existingTest = testFile().readText()

    whenCreateCodeIsCalled()

    thenMainFileIsUnchanged(existingMain)
    andTestFileIsUnchanged(existingTest)
  }

  private fun expectedMainFile() = read("${expectedCodeDir()}$MAIN_FILE")

  private fun expectedTestFile() = read("${expectedCodeDir()}$TEST_FILE")

  private fun expectedCodeDir() = "${EXPECTED_CODE_DIR}y${year()}/d${day()}/"

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(Y15, D1, L1),
      Arguments.of(Y16, D8, L1),
      Arguments.of(Y21, D25, L1),
    )
  }
}
