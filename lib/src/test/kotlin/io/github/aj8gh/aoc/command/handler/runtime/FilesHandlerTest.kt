package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.command.FILES_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.context.PROPS
import io.github.aj8gh.aoc.test.context.PROPS_FILES
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class FilesHandlerTest : BaseTest() {

  private val command = arrayOf(PROPS.activeProfile().ide, PROPS_FILES.aocHomeDir())

  @Test
  fun files() {
    GIVEN
      .theRuntimeIsMocked(command)

    WHEN
      .theAppIsRunWithArg(FILES_SHORT)

    THEN
      .theFollowingCommandWasExecuted(command)
  }
}
