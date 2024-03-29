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

    private val noAnswersCached = Arguments.of(Y15, D3, L1)
    private val intLevel1Cached = Arguments.of(Y16, D7, L1)
    private val intLevel2Cached = Arguments.of(Y16, D8, L1)
    private val intBothLevelsCached = Arguments.of(Y16, D9, L1)
    private val stringLevel1Cached = Arguments.of(Y17, D23, L1)
    private val stringLevel2Cached = Arguments.of(Y17, D24, L1)
    private val stringBothLevelsCached = Arguments.of(Y17, D25, L1)
    private val longLevel1Cached = Arguments.of(Y18, D23, L1)
    private val longLevel2Cached = Arguments.of(Y18, D24, L1)
    private val longBothLevelsCached = Arguments.of(Y18, D25, L1)

    @JvmStatic
    private fun inputProvider() = listOf(
      noAnswersCached,

      intLevel1Cached,
      intLevel2Cached,
      intBothLevelsCached,

      stringLevel1Cached,
      stringLevel2Cached,
      stringBothLevelsCached,

      longLevel1Cached,
      longLevel2Cached,
      longBothLevelsCached,
    )
  }
}
