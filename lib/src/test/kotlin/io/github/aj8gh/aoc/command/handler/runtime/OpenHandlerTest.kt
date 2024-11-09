package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.files
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class OpenHandlerTest : BaseTest() {

  private val command = arrayOf(activeProfile().ide, files().projectHome)

  @Test
  fun open() {
    GIVEN
      .theRuntimeIsMocked(command)

    WHEN
      .openIsCalled()

    THEN
      .theFollowingCommandWasExecuted(command)
  }
}
