package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.command.OPEN_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.context.props
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class ExceptionHandlingTest : BaseTest() {

  @Test
  fun open_SadPath() {
    GIVEN
      .theRuntimeWillThrowAnException(command(), message())

    WHEN
      .theAppIsRunWithArg(OPEN_SHORT)

    THEN
      .theFollowingCommandWasExecuted(command())
      .theFollowingMessageIsEchoed(logMessage())
      .theStackTraceIsLogged()
  }

  private fun logMessage() = "Error executing command: \"This is a test exception!\""
  private fun message() = "This is a test exception!"
  private fun command() = arrayOf(props.activeProfile().ide, props.files().projectHome)
}
