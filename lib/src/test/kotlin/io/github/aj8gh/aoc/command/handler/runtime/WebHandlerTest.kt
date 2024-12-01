package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.command.WEB_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.context.props
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class WebHandlerTest : BaseTest() {

  private val command = arrayOf(
    "open",
    "${props.aocProperties().url}/20${props.year()}/day/${props.day()}"
  )

  @Test
  fun open() {
    GIVEN
      .theRuntimeIsMocked(command)

    WHEN
      .theAppIsRunWithArg(WEB_SHORT)

    THEN
      .theFollowingCommandWasExecuted(command)
  }
}
