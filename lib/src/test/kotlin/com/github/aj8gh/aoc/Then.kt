package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.io.*
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.level
import com.github.aj8gh.aoc.properties.year
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import java.io.File
import kotlin.test.assertEquals

fun thenCurrentYearDayAndLevelAre(expectedYear: Int, expectedDay: Int, expectedLevel: Int) {
  assertEquals(expectedYear, year())
  assertEquals(expectedDay, day())
  assertEquals(expectedLevel, level())
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
  assertEquals(testInput(), read(inputFile()).trim())

fun andTodaysInputExists() = thenTodaysInputExists()

fun andTodaysReadmeIsCreatedCorrectly(markdown: String) =
  assertEquals(markdown.trim(), read(readmeFile()))

fun andTodaysReadmeHasBeenCached(expected: String) =
  assertEquals(expected, read(readmeCacheFile()))

fun thenMainFileIsCreatedAsExpected(expected: String) = assertEquals(expected, mainFile().readText())

fun andTestFileIsCreatedAsExpected(expected: String) = assertEquals(expected, testFile().readText())

fun thenMainFileIsUnchanged(expected: String) = assertEquals(expected, mainFile().readText())

fun andTestFileIsUnchanged(expected: String) = assertEquals(expected, testFile().readText())

fun thenTodaysExamplesAreCreatedAsExpected(expected: Array<File>) {
  val actual = File(resourcesDir()).listFiles()!!
    .filter { it.name.contains("example") }

  assertEquals(expected.size, actual.size)

  for (i in expected.indices) {
    assertEquals(expected[i].readText(), actual[i].readText())
  }
}
