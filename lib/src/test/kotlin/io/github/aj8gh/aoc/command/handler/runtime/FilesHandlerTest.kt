package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.command.FILES_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.context.props
import io.github.aj8gh.aoc.test.context.appProps
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class FilesHandlerTest : BaseTest() {

  private val command = arrayOf(props.activeProfile().ide, appProps.files.dirs.home())

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
