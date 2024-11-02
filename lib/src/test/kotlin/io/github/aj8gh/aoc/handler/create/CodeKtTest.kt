package io.github.aj8gh.aoc.handler.create

import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.io.mainFile
import io.github.aj8gh.aoc.io.testFile
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class CodeKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun createCode(language: String, year: Int, day: Int, level: Int) {
    givenActivePropertiesIsSetTo(language)
    givenCurrentYearDayAndLevelAre(year, day, level)
    andCodeFilesDoNotExistForToday()

    whenCreateCodeIsCalled()

    thenMainFileIsCreatedAsExpected(expectedMainFile())
    andTestFileIsCreatedAsExpected(expectedTestFile())
  }

  @ParameterizedTest
  @ValueSource(strings = [KT, GO])
  fun createCodeFilesExist(language: String) {
    givenActivePropertiesIsSetTo(language)
    givenCurrentYearDayAndLevelAre(Y15, D2, L1)
    andCodeFilesExistForToday()
    val existingMain = mainFile().readText()
    val existingTest = testFile().readText()

    whenCreateCodeIsCalled()

    thenMainFileIsUnchanged(existingMain)
    andTestFileIsUnchanged(existingTest)
  }

  companion object {

    private val noAnswersCached = Arguments.of(KT, Y15, D3, L1)
    private val intLevel1Cached = Arguments.of(KT, Y16, D7, L1)
    private val intLevel2Cached = Arguments.of(KT, Y16, D8, L1)
    private val intBothLevelsCached = Arguments.of(KT, Y16, D9, L1)
    private val stringLevel1Cached = Arguments.of(KT, Y17, D23, L1)
    private val stringLevel2Cached = Arguments.of(KT, Y17, D24, L1)
    private val stringBothLevelsCached = Arguments.of(KT, Y17, D25, L1)
    private val longLevel1Cached = Arguments.of(KT, Y18, D23, L1)
    private val longLevel2Cached = Arguments.of(KT, Y18, D24, L1)
    private val longBothLevelsCached = Arguments.of(KT, Y18, D25, L1)

    private val noAnswersCachedGo = Arguments.of(GO, Y15, D3, L1)
    private val intLevel1CachedGo = Arguments.of(GO, Y16, D7, L1)
    private val intLevel2CachedGo = Arguments.of(GO, Y16, D8, L1)
    private val intBothLevelsCachedGo = Arguments.of(GO, Y16, D9, L1)
    private val stringLevel1CachedGo = Arguments.of(GO, Y17, D23, L1)
    private val stringLevel2CachedGo = Arguments.of(GO, Y17, D24, L1)
    private val stringBothLevelsCachedGo = Arguments.of(GO, Y17, D25, L1)
    private val longLevel1CachedGo = Arguments.of(GO, Y18, D23, L1)
    private val longLevel2CachedGo = Arguments.of(GO, Y18, D24, L1)
    private val longBothLevelsCachedGo = Arguments.of(GO, Y18, D25, L1)

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

      noAnswersCachedGo,
      intLevel1CachedGo,
      intLevel2CachedGo,
      intBothLevelsCachedGo,
      stringLevel1CachedGo,
      stringLevel2CachedGo,
      stringBothLevelsCachedGo,
      longLevel1CachedGo,
      longLevel2CachedGo,
      longBothLevelsCachedGo,
    )
  }
}
