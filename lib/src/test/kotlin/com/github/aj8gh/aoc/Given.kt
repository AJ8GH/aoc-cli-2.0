package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.cache.inputCacheFile
import com.github.aj8gh.aoc.command.handler.create.getInputFile
import com.github.aj8gh.aoc.command.handler.set
import com.github.aj8gh.aoc.io.read
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse

fun givenCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) {
  set(year = year, day = day, level = level)
  resetOutStream()
}

fun givenTheFollowingRequestStub(stub: MappingBuilder): StubMapping =
  WireMock.stubFor(stub)

fun andTheFollowingRequestStub(stub: MappingBuilder) = givenTheFollowingRequestStub(stub)

fun andTodaysInputFileDoesNotExist() = assertFalse { getInputFile().exists() }

fun andTodaysInputFileAlreadyExists() = thenTodaysInputExists()

fun andTodaysInputIsCached() = assertEquals(testInput(), read(inputCacheFile()).trim())

fun andTodaysInputIsNotCached() = assertFalse(File(inputCacheFile()).exists())

fun andNoReadmeExistsForToday() = Unit

fun andTodaysReadmeIsNotCached() = Unit
