package io.github.aj8gh.aoc.test.steps

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import io.github.aj8gh.aoc.properties.Profile
import io.github.aj8gh.aoc.test.context.context
import io.github.aj8gh.aoc.test.context.files
import io.github.aj8gh.aoc.test.context.props
import io.github.aj8gh.aoc.test.context.reader
import io.github.aj8gh.aoc.test.outContent
import io.github.aj8gh.aoc.test.testInput
import io.mockk.verify
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Then {

  fun currentYearDayAndLevelAre(expectedYear: Int, expectedDay: Int, expectedLevel: Int): Then {
    assertEquals(expectedYear, props.year())
    assertEquals(expectedDay, props.day())
    assertEquals(expectedLevel, props.level())
    return this
  }

  fun theExpectedCheckCacheResponseIsReturned(response: String, answer: String): Then {
    assertEquals(response, context.cache.answer.checkAnswer(answer))
    return this
  }

  fun theFollowingMessageIsEchoed(expected: String): Then {
    assertEquals(expected, outContent())
    return this
  }

  fun theFollowingMessagesAreEchoed(vararg expected: String): Then {
    val actual = outContent().lines()
    assertEquals(expected.size, actual.size, "Found mismatched number of expected and actual messages")
    for (i in expected.indices) {
      assertEquals(expected[i], actual[i].trim())
    }
    return this
  }

  fun theFollowingRequestWasMade(expected: RequestPatternBuilder): Then {
    verify(expected)
    return this
  }

  fun noRequestsWereMadeForUrl(expectedUrl: String): Then {
    verify(exactly(0), anyRequestedFor(urlPathEqualTo(expectedUrl)))
    return this
  }

  fun todaysInputExists(): Then {
    assertEquals(testInput(), reader.read(files.inputFile()).trim())
    return this
  }

  fun todaysInputIsCached(): Then {
    GIVEN.todaysInputIsCached()
    return this
  }

  fun todaysReadmeIsCreatedCorrectly(markdown: String): Then {
    assertEquals(markdown.trim(), reader.read(files.readme()))
    return this
  }

  fun todaysReadmeHasBeenCached(expected: String): Then {
    assertEquals(expected, reader.read(files.readmeCacheFile()))
    return this
  }

  fun mainFileIsCreatedAsExpected(expected: String): Then {
    assertEquals(expected, files.mainFile().readText())
    return this
  }

  fun testFileIsCreatedAsExpected(expected: String): Then {
    assertEquals(expected, files.testFile().readText())
    return this
  }

  fun mainFileIsUnchanged(expected: String): Then {
    assertEquals(expected, files.mainFile().readText())
    return this
  }

  fun testFileIsUnchanged(expected: String): Then {
    assertEquals(expected, files.testFile().readText())
    return this
  }

  fun todaysExampleIsCreatedAsExpected(expected: File): Then {
    assertEquals(trimLines(expected), trimLines(files.exampleFile()))
    return this
  }

  fun theFollowingProfileIsActive(profile: String): Then {
    assertEquals(profile, props.aocProperties().active)
    val actual = props.activeProfile()
    val expected = reader.readYaml(files.activeProfileFile(), Profile::class.java)
    assertEquals(expected, actual)
    return this
  }

  fun theFollowingCommandWasExecuted(command: Array<String>): Then {
    verify { context.system.runtime.exec(command) }
    return this
  }

  fun theStackTraceIsLogged(): Then {
    val logFile = files.logFile()
    assertTrue { logFile.exists() }
    assertTrue { logFile.readText().isNotEmpty() }
    return this
  }

  fun theTokenHasBeenUpdatedTo(token: String): Then {
    assertEquals(token, props.aocProperties().session)
    return this
  }

  fun theStatsAreOutputToTheTerminal(): Then {
    val out = outContent()
    for (year in context.manager.date.yearRange()) {
      val completion = context.cache.answer.cache().year(year).completion()
      assertTrue { out.contains(year.toString()) }
      assertTrue { out.contains(completion.toString()) }
    }
    return this
  }

  fun theAnswerIsCachedFor(expected: String, year: Int, day: Int, level: Int): Then {
    val actual = context.cache.answer.cache().get(year, day, level)
    assertEquals(expected, actual)
    return this
  }

  fun theCurrentDayReadmeLevelIs(expected: Int): Then {
    val actual = context.cache.readme.readmeLevel(files.readmeCacheFile())
    assertEquals(expected, actual)
    return this
  }

  fun theReadmeLevelForDayIs(year: Int = props.year(), day: Int = props.day(), expected: Int): Then {
    val file = File(context.app.properties.files.readmeCache(year, day))
    val actual = context.cache.readme.readmeLevel(file)
    assertEquals(expected, actual)
    return this
  }

  private fun trimLines(file: File) = file
    .readLines().joinToString { it.trimEnd() }
}
