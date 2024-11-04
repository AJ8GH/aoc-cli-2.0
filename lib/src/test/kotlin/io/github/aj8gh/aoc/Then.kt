package io.github.aj8gh.aoc

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import io.github.aj8gh.aoc.command.handler.EXTENSION
import io.github.aj8gh.aoc.io.*
import io.github.aj8gh.aoc.properties.*
import io.mockk.verify
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

fun andMainFileIsCreatedAsExpected(expected: String) = thenMainFileIsCreatedAsExpected(expected)

fun thenMainFileIsUnchanged(expected: String) = assertEquals(expected, mainFile().readText())

fun andTestFileIsUnchanged(expected: String) = assertEquals(expected, testFile().readText())

fun thenTodaysExampleIsCreatedAsExpected(expected: File) =
  assertEquals(trimLines(expected), trimLines(exampleFile()))

fun thenTheFollowingProfileIsActive(profile: String) {
  assertEquals(profile, aocProperties().active)

  val actual = activeProfile()
  val expected = readYaml(File("$AOC_HOME$profile$EXTENSION"), Profile::class.java)
  assertEquals(expected, actual)
}

fun theRuntimeIsInvoked(runtime: Runtime) =
  verify { runtime.exec(arrayOf(activeProfile().ide, files().projectHome)) }

fun thenTheRuntimeIsNotInvoked(runtime: Runtime) =
  verify(exactly = 0) { runtime.exec(arrayOf(activeProfile().ide, files().projectHome)) }

fun thenTodaysExamplesAreCreatedAsExpected(expected: Array<File>) {
  val actual = File(resourcesDir()).listFiles()!!
    .filter { it.name.contains("example") }

  assertEquals(expected.size, actual.size)

  for (i in expected.indices) {
    assertEquals(
      trimLines(expected[i]),
      trimLines(actual[i]),
    )
  }
}

private fun trimLines(file: File) = file
  .readLines().joinToString { it.trimEnd() }
