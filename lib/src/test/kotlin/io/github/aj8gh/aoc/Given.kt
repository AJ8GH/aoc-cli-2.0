package io.github.aj8gh.aoc

import com.github.ajalt.mordant.table.Table
import com.github.ajalt.mordant.terminal.Terminal
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import io.github.aj8gh.aoc.cache.ReadmeCache
import io.github.aj8gh.aoc.cache.answer.AnswerCacheManager
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.handler.EchoHandler
import io.github.aj8gh.aoc.command.handler.SetHandler
import io.github.aj8gh.aoc.io.*
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.forceLoadActiveProfile
import io.github.aj8gh.aoc.properties.setActiveProfile
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

val setHandler = SetHandler(EchoHandler())

fun givenCurrentYearDayAndLevelAre(year: Int, day: Int) {
  setHandler.set(year = year, day = day, level = L1, false)
  stubOutStream()
}

fun givenCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) {
  setHandler.set(year = year, day = day, level = level, false)
  stubOutStream()
}

fun givenTheFollowingRequestStub(stub: MappingBuilder): StubMapping =
  WireMock.stubFor(stub)

fun givenActivePropertiesIsSetTo(profile: String) =
  setActiveProfile(profile)

fun givenTheRuntimeIsMocked(command: Array<String>): Runtime {
  val runtime = mockk<Runtime>()
  val process = mockk<Process>()
  every { runtime.exec(command) } returns process
  return runtime
}

fun givenTheTerminalIsMocked(): Terminal {
  val terminal = mockk<Terminal>(relaxUnitFun = true)
  justRun { terminal.println(any(Table::class)) }
  return terminal
}

fun givenTheCurrentTokenIs(token: String) = thenTheTokenHasBeenUpdatedTo(token)

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

fun andTodaysReadmeIsCached(html: String) = ReadmeCache().cacheReadme(html)

fun andTodaysCompletionLevelIs(level: Int) {
  val cacheManager = AnswerCacheManager()
  if (level == 0) cacheManager.clearCacheForDay()
  else (1..level).forEach { cacheManager.cacheAnswer(it, ANSWER) }
}

fun andCodeFilesDoNotExistForToday() {
  assertFalse { mainFile().exists() }
  assertFalse { testFile().exists() }
}

fun andCodeFilesExistForToday() {
  assertTrue { mainFile().exists() }
  assertTrue { testFile().exists() }
}

fun andNoExampleExistsForToday() = assertFalse { exampleFile().exists() }
