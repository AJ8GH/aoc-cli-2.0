package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.cache.readmeCacheFile
import com.github.aj8gh.aoc.command.handler.create.inputFile
import com.github.aj8gh.aoc.command.handler.create.readmeFile
import com.github.aj8gh.aoc.io.read
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import kotlin.test.assertEquals

fun thenCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) {
  val actual = activeProperties()
  assertEquals(year, actual.current.year)
  assertEquals(day, actual.current.day)
  assertEquals(level, actual.current.level)
}

fun andCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) =
  thenCurrentYearDayAndLevelAre(year, day, level)

fun thenTheFollowingMessageIsEchoed(expected: String) =
  assertEquals(expected, outContent())

fun andTheFollowingMessageIsEchoed(expected: String) =
  thenTheFollowingMessageIsEchoed(expected)

fun andTheFollowingMessagesAreEchoed(vararg expected: String) {
  val actual = outContent().lines()
  assertEquals(expected.size, actual.size, "Found mismatched number of expected and actual messages")
  for (i in expected.indices) {
    assertEquals(expected[i], actual[i].trim())
  }
}

fun thenTheFollowingRequestWasMade(expected: RequestPatternBuilder) =
  WireMock.verify(expected)

fun thenNoRequestsWereMadeForUrl(expectedUrl: String) =
  WireMock.verify(WireMock.exactly(0), WireMock.anyRequestedFor(WireMock.urlPathEqualTo(expectedUrl)))

fun andNoRequestsWereMadeForUrl(expectedUrl: String) = thenNoRequestsWereMadeForUrl(expectedUrl)

fun thenTodaysInputExists() =
  assertEquals(testInput(), read(inputFile().absolutePath).trim())

fun andTodaysInputExists() = thenTodaysInputExists()

fun andTodaysReadmeIsCreatedCorrectly(markdown: String) =
  assertEquals(markdown.trim(), read(readmeFile().absolutePath))

fun andTodaysReadmeHasBeenCached(expected: String) =
  assertEquals(expected, read(readmeCacheFile()))
