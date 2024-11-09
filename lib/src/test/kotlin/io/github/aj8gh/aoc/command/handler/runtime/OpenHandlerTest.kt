package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.files
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import io.mockk.mockk
import kotlin.test.Test

class OpenHandlerTest : BaseTest() {

  private val command = arrayOf(activeProfile().ide, files().projectHome)
  private val runtime = mockk<Runtime>()

  @Test
  fun open() {
    GIVEN
      .theRuntimeIsMocked(runtime, command)

    WHEN
      .openIsCalled(runtime)

    THEN
      .theFollowingCommandWasExecuted(runtime, command)
  }
}
