package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.cache.answer.cacheAnswer
import com.github.aj8gh.aoc.cache.answer.clearCacheForDay
import com.github.aj8gh.aoc.cache.cacheReadme
import com.github.aj8gh.aoc.command.handler.set
import com.github.aj8gh.aoc.io.*
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import kotlin.test.assertEquals
import kotlin.test.assertFalse

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

fun andTodaysInputIsNotCached() = assertFalse(inputCacheFile().exists())

fun andNoReadmeExistsForToday() = assertFalse { readmeFile().exists() }

fun andTodaysReadmeExists(markdown: String) = run {
  createResourcesDirIfNotExists()
  write(readmeFile(), markdown)
}

fun andTodaysReadmeIsNotCached() = assertFalse { readmeCacheFile().exists() }

fun andTodaysReadmeIsCached(readme: String) = cacheReadme(readme)

fun andTodaysCompletionLevelIs(level: Int) {
  if (level == 0) {
    clearCacheForDay()
    return
  }
  (1..level).forEach { cacheAnswer(it, ANSWER) }
}

fun givenCodeFilesDoNotExistForToday() = Unit
