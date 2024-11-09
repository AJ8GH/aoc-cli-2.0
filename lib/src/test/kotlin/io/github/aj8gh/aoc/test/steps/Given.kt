package io.github.aj8gh.aoc.test.steps

import com.github.ajalt.mordant.table.Table
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import io.github.aj8gh.aoc.cache.ReadmeCache
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.handler.NOT_CACHED
import io.github.aj8gh.aoc.io.*
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.forceLoadActiveProfile
import io.github.aj8gh.aoc.properties.setActiveProfile
import io.github.aj8gh.aoc.test.ANSWER
import io.github.aj8gh.aoc.test.context.CONTEXT
import io.github.aj8gh.aoc.test.stubOutStream
import io.github.aj8gh.aoc.test.testInput
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Given {

  fun theFollowingRequestStub(stub: MappingBuilder): Given {
    stubFor(stub)
    return this
  }

  fun activeProfileIs(profile: String): Given {
    setActiveProfile(profile)
    return this
  }

  fun theCurrentTokenIs(token: String): Given {
    THEN.theTokenHasBeenUpdatedTo(token)
    return this
  }

  fun todaysInputFileDoesNotExist(): Given {
    assertFalse { inputFile().exists() }
    return this
  }

  fun todaysInputIsCached(): Given {
    assertEquals(testInput(), read(inputCacheFile()).trim())
    return this
  }

  fun todaysInputIsNotCached(): Given {
    assertFalse(inputCacheFile().exists())
    return this
  }

  fun noReadmeExistsForToday(): Given {
    assertFalse { readmeFile().exists() }
    return this
  }

  fun todaysReadmeIsNotCached(): Given {
    assertFalse { readmeCacheFile().exists() }
    return this
  }

  fun todaysReadmeIsCached(html: String): Given {
    ReadmeCache().cacheReadme(html)
    return this
  }

  fun noExampleExistsForToday(): Given {
    assertFalse { exampleFile().exists() }
    return this
  }

  fun currentYearDayAndLevelAre(year: Int, day: Int): Given {
    CONTEXT.handler.set.handle(year = year, day = day, level = L1, false)
    stubOutStream()
    return this
  }

  fun currentYearDayAndLevelAre(year: Int, day: Int, level: Int): Given {
    CONTEXT.handler.set.handle(year = year, day = day, level = level, false)
    stubOutStream()
    return this
  }

  fun todaysAnswerIsNotCached(answer: String): Given {
    assertEquals(NOT_CACHED, CONTEXT.cache.answer.checkAnswer(answer))
    return this
  }

  fun theRuntimeIsMocked(command: Array<String>): Given {
    val process = mockk<Process>()
    every { CONTEXT.exec.runtime.exec(command) } returns process
    return this
  }

  fun theTerminalIsMocked(): Given {
    justRun { CONTEXT.exec.terminal.println(any(Table::class)) }
    return this
  }


  fun writeAnswerInCodeIsSetTo(bool: Boolean): Given {
    write(activeProfile().copy(writeAnswerInCode = bool))
    forceLoadActiveProfile()
    return this
  }

  fun todaysInputFileAlreadyExists(): Given {
    assertEquals(testInput(), read(inputFile()).trim())
    return this
  }

  fun todaysReadmeExists(markdown: String): Given {
    createResourcesDirIfNotExists()
    write(readmeFile(), markdown)
    return this
  }

  fun todaysCompletionLevelIs(level: Int): Given {
    if (level == 0) CONTEXT.cache.answer.clearCacheForDay()
    else (1..level).forEach { CONTEXT.cache.answer.cacheAnswer(it, ANSWER) }
    return this
  }

  fun codeFilesDoNotExistForToday(): Given {
    assertFalse { mainFile().exists() }
    assertFalse { testFile().exists() }
    return this
  }

  fun codeFilesExistForToday(): Given {
    assertTrue { mainFile().exists() }
    assertTrue { testFile().exists() }
    return this
  }
}
