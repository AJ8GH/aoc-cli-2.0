package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.cache.inputCacheFile
import com.github.aj8gh.aoc.command.handler.create.getInputFile
import com.github.aj8gh.aoc.command.handler.set
import com.github.aj8gh.aoc.io.read
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import kotlin.test.assertEquals
import kotlin.test.assertFalse

fun givenCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) {
  set(year = year, day = day, level = level)
  resetOutStream()
}

fun givenTheFollowingRequestStub(stub: MappingBuilder): StubMapping =
    WireMock.stubFor(stub)

fun givenTodaysInputFileDoesNotExist() = assertFalse { getInputFile().exists() }

fun givenTodaysInputFileAlreadyExists() = thenTodaysInputExists()

fun givenTodaysInputIsCached() = assertEquals(testInput(), read(inputCacheFile()).trim())