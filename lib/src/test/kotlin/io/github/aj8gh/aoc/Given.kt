package io.github.aj8gh.aoc

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import io.github.aj8gh.aoc.cache.answer.cacheAnswer
import io.github.aj8gh.aoc.cache.answer.clearCacheForDay
import io.github.aj8gh.aoc.cache.cacheReadme
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.handler.set
import io.github.aj8gh.aoc.io.*
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.files
import io.github.aj8gh.aoc.properties.forceLoadActiveProfile
import io.github.aj8gh.aoc.properties.setActiveProfile
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun givenCurrentYearDayAndLevelAre(year: Int, day: Int) {
  set(year = year, day = day, level = L1)
  stubOutStream()
}

fun givenCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) {
  set(year = year, day = day, level = level)
  stubOutStream()
}

fun givenTheFollowingRequestStub(stub: MappingBuilder): StubMapping =
  WireMock.stubFor(stub)

fun givenActivePropertiesIsSetTo(profile: String) =
  setActiveProfile(profile)

fun givenTheRuntimeIsMocked(): Runtime {
  val runtime = mockk<Runtime>()
  val process = mockk<Process>()
  every { runtime.exec(arrayOf(activeProfile().ide, files().projectHome)) } returns process
  return runtime
}

fun andWriteAnswerInCodeIsSetTo(bool: Boolean) {
  write(activeProfile().copy(writeAnswerInCode = bool))
  forceLoadActiveProfile()
}

fun andTheFollowingRequestStub(stub: MappingBuilder) = givenTheFollowingRequestStub(stub)

fun andTodaysInputFileDoesNotExist() = assertFalse { inputFile().exists() }

fun andTodaysInputFileAlreadyExists() = thenTodaysInputExists()

fun andTodaysInputIsCached() = assertEquals(testInput(), read(inputCacheFile()).trim())

fun andTodaysInputIsNotCached() = assertFalse(inputCacheFile().exists())

fun andNoReadmeExistsForToday() = assertFalse { readmeFile().exists() }

fun andTodaysReadmeExists(markdown: String) {
  createResourcesDirIfNotExists()
  write(readmeFile(), markdown)
}

fun andTodaysReadmeIsNotCached() = assertFalse { readmeCacheFile().exists() }

fun andTodaysReadmeIsCached(html: String) = cacheReadme(html)

fun andTodaysCompletionLevelIs(level: Int) =
  if (level == 0) clearCacheForDay()
  else (1..level).forEach { cacheAnswer(it, ANSWER) }

fun andCodeFilesDoNotExistForToday() {
  assertFalse { mainFile().exists() }
  assertFalse { testFile().exists() }
}

fun andCodeFilesExistForToday() {
  assertTrue { mainFile().exists() }
  assertTrue { testFile().exists() }
}

fun andNoExampleExistsForToday() = assertFalse { exampleFile().exists() }
