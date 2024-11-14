package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.command.OPEN_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.context.FILES
import io.github.aj8gh.aoc.test.context.PROPS
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import kotlin.test.Test

class ExceptionHandlingTest : BaseTest() {

  private val logMessage = "ERROR - 2024-01-01T00:00:00Z - Error executing command: \"This is a test exception!\". View stack trace in log file: ${FILES.logFile()}"
  private val message = "This is a test exception!"
  private val command = arrayOf(PROPS.activeProfile().ide, PROPS.files().projectHome)

  @Test
  fun open_SadPath() {
    GIVEN
      .theRuntimeWillThrowAnException(command, message)

    WHEN
      .theAppIsRunWithArg(OPEN_SHORT)

    THEN
      .theFollowingCommandWasExecuted(command)
      .theFollowingMessagesAreEchoed(logMessage)
      .theStackTraceIsLogged()
  }
}
