package io.github.aj8gh.aoc.test.steps

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import io.github.aj8gh.aoc.io.EXTENSION
import io.github.aj8gh.aoc.properties.Profile
import io.github.aj8gh.aoc.test.context.CONTEXT
import io.github.aj8gh.aoc.test.context.FILES
import io.github.aj8gh.aoc.test.context.PROPS
import io.github.aj8gh.aoc.test.context.READER
import io.github.aj8gh.aoc.test.outContent
import io.github.aj8gh.aoc.test.testInput
import io.mockk.verify
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Then {

  fun currentYearDayAndLevelAre(expectedYear: Int, expectedDay: Int, expectedLevel: Int): Then {
    assertEquals(expectedYear, PROPS.year())
    assertEquals(expectedDay, PROPS.day())
    assertEquals(expectedLevel, PROPS.level())
    return this
  }

  fun theExpectedCheckCacheResponseIsReturned(response: String, answer: String): Then {
    assertEquals(response, CONTEXT.cache.answer.checkAnswer(answer))
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
    assertEquals(testInput(), READER.read(FILES.inputFile()).trim())
    return this
  }

  fun todaysInputIsCached(): Then {
    GIVEN.todaysInputIsCached()
    return this
  }

  fun todaysReadmeIsCreatedCorrectly(markdown: String): Then {
    assertEquals(markdown.trim(), READER.read(FILES.readmeFile()))
    return this
  }

  fun todaysReadmeHasBeenCached(expected: String): Then {
    assertEquals(expected, READER.read(FILES.readmeCacheFile()))
    return this
  }

  fun mainFileIsCreatedAsExpected(expected: String): Then {
    assertEquals(expected, FILES.mainFile().readText())
    return this
  }

  fun testFileIsCreatedAsExpected(expected: String): Then {
    assertEquals(expected, FILES.testFile().readText())
    return this
  }

  fun mainFileIsUnchanged(expected: String): Then {
    assertEquals(expected, FILES.mainFile().readText())
    return this
  }

  fun testFileIsUnchanged(expected: String): Then {
    assertEquals(expected, FILES.testFile().readText())
    return this
  }

  fun todaysExampleIsCreatedAsExpected(expected: File): Then {
    assertEquals(trimLines(expected), trimLines(FILES.exampleFile()))
    return this
  }

  fun theFollowingProfileIsActive(profile: String): Then {
    assertEquals(profile, PROPS.aocProperties().active)
    val actual = PROPS.activeProfile()
    val file = File("${io.github.aj8gh.aoc.test.AOC_HOME}$profile$EXTENSION")
    val expected = READER.readYaml(file, Profile::class.java)
    assertEquals(expected, actual)
    return this
  }

  fun theFollowingCommandWasExecuted(command: Array<String>): Then {
    verify { CONTEXT.system.runtime.exec(command) }
    return this
  }

  fun theStackTraceIsLogged(): Then {
    val logFile = FILES.logFile()
    assertTrue { logFile.exists() }
    assertTrue { logFile.readText().isNotEmpty() }
    return this
  }

  fun theTokenHasBeenUpdatedTo(token: String): Then {
    assertEquals(token, PROPS.aocProperties().session)
    return this
  }

  fun theStatsAreOutputToTheTerminal(): Then {
    val out = outContent()
    for (year in CONTEXT.manager.date.yearRange()) {
      val completion = CONTEXT.cache.answer.cache().year(year).completion()
      assertTrue { out.contains(year.toString()) }
      assertTrue { out.contains(completion.toString()) }
    }
    return this
  }

  private fun trimLines(file: File) = file
    .readLines().joinToString { it.trimEnd() }
}
