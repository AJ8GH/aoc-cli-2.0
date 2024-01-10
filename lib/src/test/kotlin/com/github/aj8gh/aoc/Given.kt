package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.cache.*
import com.github.aj8gh.aoc.command.handler.create.resourcesDir
import com.github.aj8gh.aoc.command.handler.create.inputFile
import com.github.aj8gh.aoc.command.handler.create.readmeFile
import com.github.aj8gh.aoc.command.handler.set
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.io.write
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun givenCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) {
  set(year = year, day = day, level = level)
  stubOutStream()
}

fun givenTheFollowingRequestStub(stub: MappingBuilder): StubMapping =
  WireMock.stubFor(stub)

fun andTheFollowingRequestStub(stub: MappingBuilder) = givenTheFollowingRequestStub(stub)

fun andTodaysInputFileDoesNotExist() = assertFalse { inputFile().exists() }

fun andTodaysInputFileAlreadyExists() = thenTodaysInputExists()

fun andTodaysInputIsCached() = assertEquals(testInput(), read(inputCacheFile()).trim())

fun andTodaysInputIsNotCached() = assertFalse(File(inputCacheFile()).exists())

fun andNoReadmeExistsForToday() = assertFalse { readmeFile().exists() }

fun andTodaysReadmeExists(markdown: String) = run {
  resourcesDir().mkdirs()
  write(readmeFile(), markdown)
}

fun andTodaysReadmeIsNotCached() = assertFalse { File(readmeCacheFile()).exists() }

fun andTodaysReadmeIsCached(readme: String) = cacheReadme(readme)

fun andTodaysCompletionLevelIs(level: Int) {
  if (level == 0) {
    clearCacheForDay()
    return
  }
  (1..level).forEach { cacheAnswer(it, ANSWER) }
}

fun givenCodeFilesDoNotExistForToday() = Unit
