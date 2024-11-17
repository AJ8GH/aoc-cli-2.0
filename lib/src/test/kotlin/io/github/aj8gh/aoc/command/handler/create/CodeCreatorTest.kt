package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.test.*
import io.github.aj8gh.aoc.test.context.files
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class CodeCreatorTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun createCode(language: String, year: Int, day: Int, level: Int) {
    GIVEN
      .activeProfileIs(language)
      .currentYearDayAndLevelAre(year, day, level)
      .codeFilesDoNotExistForToday()

    WHEN
      .createCodeIsCalled()

    THEN
      .mainFileIsCreatedAsExpected(expectedMainFile())
      .testFileIsCreatedAsExpected(expectedTestFile())
  }

  @ParameterizedTest
  @ValueSource(strings = [KT_PROFILE, GO_PROFILE])
  fun createCodeFilesExist(language: String) {
    GIVEN
      .activeProfileIs(language)
      .currentYearDayAndLevelAre(Y15, D2, L1)
      .codeFilesExistForToday()

    val existingMain = files.mainFile().readText()
    val existingTest = files.testFile().readText()

    WHEN
      .createCodeIsCalled()

    THEN
      .mainFileIsUnchanged(existingMain)
      .testFileIsUnchanged(existingTest)
  }

  @ParameterizedTest
  @ValueSource(strings = [KT_PROFILE, GO_PROFILE])
  fun createCodeWithoutAnswer(language: String) {
    GIVEN
      .activeProfileIs(language)
      .currentYearDayAndLevelAre(Y16, D10, L1)
      .writeAnswerInCodeIsSetTo(false)
      .codeFilesDoNotExistForToday()

    WHEN
      .createCodeIsCalled()

    THEN
      .mainFileIsCreatedAsExpected(expectedMainFile())
      .testFileIsCreatedAsExpected(expectedTestFile())
  }

  companion object {

    private val noAnswersCached = Arguments.of(KT_PROFILE, Y15, D3, L1)
    private val intLevel1Cached = Arguments.of(KT_PROFILE, Y16, D7, L1)
    private val intLevel2Cached = Arguments.of(KT_PROFILE, Y16, D8, L1)
    private val intBothLevelsCached = Arguments.of(KT_PROFILE, Y16, D9, L1)
    private val stringLevel1Cached = Arguments.of(KT_PROFILE, Y17, D23, L1)
    private val stringLevel2Cached = Arguments.of(KT_PROFILE, Y17, D24, L1)
    private val stringBothLevelsCached = Arguments.of(KT_PROFILE, Y17, D25, L1)
    private val longLevel1Cached = Arguments.of(KT_PROFILE, Y18, D23, L1)
    private val longLevel2Cached = Arguments.of(KT_PROFILE, Y18, D24, L1)
    private val longBothLevelsCached = Arguments.of(KT_PROFILE, Y18, D25, L1)

    private val noAnswersCachedGo = Arguments.of(GO_PROFILE, Y15, D3, L1)
    private val intLevel1CachedGo = Arguments.of(GO_PROFILE, Y16, D7, L1)
    private val intLevel2CachedGo = Arguments.of(GO_PROFILE, Y16, D8, L1)
    private val intBothLevelsCachedGo = Arguments.of(GO_PROFILE, Y16, D9, L1)
    private val stringLevel1CachedGo = Arguments.of(GO_PROFILE, Y17, D23, L1)
    private val stringLevel2CachedGo = Arguments.of(GO_PROFILE, Y17, D24, L1)
    private val stringBothLevelsCachedGo = Arguments.of(GO_PROFILE, Y17, D25, L1)
    private val longLevel1CachedGo = Arguments.of(GO_PROFILE, Y18, D23, L1)
    private val longLevel2CachedGo = Arguments.of(GO_PROFILE, Y18, D24, L1)
    private val longBothLevelsCachedGo = Arguments.of(GO_PROFILE, Y18, D25, L1)

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
