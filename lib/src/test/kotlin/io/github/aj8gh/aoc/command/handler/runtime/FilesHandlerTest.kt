package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.io.aocHomeDir
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import io.mockk.mockk
import kotlin.test.Test

class FilesHandlerTest : BaseTest() {

  private val command = arrayOf(activeProfile().ide, aocHomeDir())
  private val runtime = mockk<Runtime>()

  @Test
  fun files() {
    GIVEN
      .theRuntimeIsMocked(runtime, command)

    WHEN
      .configFileIsCalled(runtime)

    THEN
      .theFollowingCommandWasExecuted(runtime, command)
  }
}
