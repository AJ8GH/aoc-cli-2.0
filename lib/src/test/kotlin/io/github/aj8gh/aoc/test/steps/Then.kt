package io.github.aj8gh.aoc.test.steps

import com.github.ajalt.mordant.table.Table
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import io.github.aj8gh.aoc.io.*
import io.github.aj8gh.aoc.properties.*
import io.github.aj8gh.aoc.test.context.CONTEXT
import io.github.aj8gh.aoc.test.outContent
import io.github.aj8gh.aoc.test.testInput
import io.mockk.verify
import java.io.File
import kotlin.test.assertEquals

class Then {

  fun currentYearDayAndLevelAre(expectedYear: Int, expectedDay: Int, expectedLevel: Int): Then {
    assertEquals(expectedYear, year())
    assertEquals(expectedDay, day())
    assertEquals(expectedLevel, level())
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
    assertEquals(testInput(), read(inputFile()).trim())
    return this
  }

  fun todaysInputIsCached(): Then {
    GIVEN.todaysInputIsCached()
    return this
  }

  fun todaysReadmeIsCreatedCorrectly(markdown: String): Then {
    assertEquals(markdown.trim(), read(readmeFile()))
    return this
  }

  fun todaysReadmeHasBeenCached(expected: String): Then {
    assertEquals(expected, read(readmeCacheFile()))
    return this
  }

  fun mainFileIsCreatedAsExpected(expected: String): Then {
    assertEquals(expected, mainFile().readText())
    return this
  }

  fun testFileIsCreatedAsExpected(expected: String): Then {
    assertEquals(expected, testFile().readText())
    return this
  }

  fun mainFileIsUnchanged(expected: String): Then {
    assertEquals(expected, mainFile().readText())
    return this
  }

  fun testFileIsUnchanged(expected: String): Then {
    assertEquals(expected, testFile().readText())
    return this
  }

  fun todaysExampleIsCreatedAsExpected(expected: File): Then {
    assertEquals(trimLines(expected), trimLines(exampleFile()))
    return this
  }

  fun theFollowingProfileIsActive(profile: String): Then {
    assertEquals(profile, aocProperties().active)
    val actual = activeProfile()
    val expected = readYaml(File("${io.github.aj8gh.aoc.test.AOC_HOME}$profile$EXTENSION"), Profile::class.java)
    assertEquals(expected, actual)
    return this
  }

  fun theFollowingCommandWasExecuted(command: Array<String>): Then {
    verify { CONTEXT.exec.runtime.exec(command) }
    return this
  }

  fun theStatsArePrintedAsExpected(): Then {
    verify { CONTEXT.exec.terminal.println(any(Table::class)) }
    return this
  }

  fun theTerminalWasNotInvoked(): Then {
    verify(exactly = 0) { CONTEXT.exec.terminal.println(any(String::class)) }
    return this
  }

  fun theTokenHasBeenUpdatedTo(token: String): Then {
    assertEquals(token, aocProperties().session)
    return this
  }

  private fun trimLines(file: File) = file
    .readLines().joinToString { it.trimEnd() }
}
