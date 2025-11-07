package io.github.aj8gh.aoc.test.steps

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.handler.NOT_CACHED
import io.github.aj8gh.aoc.test.ANSWER
import io.github.aj8gh.aoc.test.context.*
import io.github.aj8gh.aoc.test.stubOutStream
import io.github.aj8gh.aoc.test.testInput
import io.mockk.every
import io.mockk.mockk
import java.time.Instant.parse
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Given {

  fun theFollowingRequestStub(stub: MappingBuilder): Given {
    stubFor(stub)
    return this
  }

  fun activeProfileIs(profile: String): Given {
    props.setActiveProfile(profile)
    return this
  }

  fun theCurrentTokenIs(token: String): Given {
    THEN.theTokenHasBeenUpdatedTo(token)
    return this
  }

  fun todaysInputFileDoesNotExist(): Given {
    assertFalse { files.inputFile().exists() }
    return this
  }

  fun todaysInputIsCached(): Given {
    assertEquals(testInput(), reader.read(files.inputCacheFile()).trim())
    return this
  }

  fun todaysInputIsNotCached(): Given {
    assertFalse(files.inputCacheFile().exists())
    return this
  }

  fun noReadmeExistsForToday(): Given {
    assertFalse { files.readme().exists() }
    return this
  }

  fun todaysReadmeIsNotCached(): Given {
    assertFalse { files.readmeCacheFile().exists() }
    return this
  }

  fun todaysReadmeIsCached(html: String): Given {
    context.cache.readme.cacheReadme(html)
    return this
  }

  fun noExampleExistsForToday(): Given {
    assertFalse { files.exampleFile().exists() }
    return this
  }

  fun currentYearDayAndLevelAre(year: Int, day: Int): Given {
    context.handler.set.handle(year = year, day = day, level = L1, false)
    stubOutStream()
    return this
  }

  fun currentYearDayAndLevelAre(year: Int, day: Int, level: Int): Given {
    context.handler.set.handle(year = year, day = day, level = level, false)
    stubOutStream()
    return this
  }

  fun currentDateTimeIs(value: String): Given {
    every { context.system.clock.instant() } returns parse(value)
    return this
  }

  fun todaysAnswerIsNotCached(answer: String): Given {
    assertEquals(NOT_CACHED, context.cache.answer.checkAnswer(answer))
    return this
  }

  fun theRuntimeIsMocked(command: Array<String>): Given {
    val process = mockk<Process>()
    every { context.system.runtime.exec(command) } returns process
    return this
  }

  fun theRuntimeWillThrowAnException(command: Array<String>, message: String): Given {
    val exception = RuntimeException(message)
    every { context.system.runtime.exec(command) } throws exception
    return this
  }

  fun writeAnswerInCodeIsSetTo(bool: Boolean): Given {
    writer.write(files.activeProfileFile(), props.activeProfile().copy(writeAnswerInCode = bool))
    props.forceLoadActiveProfile()
    return this
  }

  fun todaysInputFileAlreadyExists(): Given {
    assertEquals(testInput(), reader.read(files.inputFile()).trim())
    return this
  }

  fun todaysReadmeExists(markdown: String): Given {
    context.io.dirCreator.mkdirs(files.readme())
    writer.write(files.readme(), markdown)
    return this
  }

  fun todaysCompletionLevelIs(level: Int): Given {
    if (level == 0) context.cache.answer.clearCacheForDay()
    else (1..level).forEach { context.cache.answer.cacheAnswer(it, ANSWER) }
    return this
  }

  fun codeFilesDoNotExistForToday(): Given {
    assertFalse { files.mainFile().exists() }
    assertFalse { files.testFile().exists() }
    return this
  }

  fun codeFilesExistForToday(): Given {
    assertTrue { files.mainFile().exists() }
    assertTrue { files.testFile().exists() }
    return this
  }
}
