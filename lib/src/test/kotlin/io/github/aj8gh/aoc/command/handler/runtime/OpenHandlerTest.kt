package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.command.OPEN_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.context.props
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class OpenHandlerTest : BaseTest() {

  private val command = arrayOf(props.activeProfile().ide, props.files().projectHome)

  @Test
  fun open() {
    GIVEN
      .theRuntimeIsMocked(command)

    WHEN
      .theAppIsRunWithArg(OPEN_SHORT)

    THEN
      .theFollowingCommandWasExecuted(command)
  }
}
