package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.BaseTest
import io.github.aj8gh.aoc.givenTheRuntimeIsMocked
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.aocProperties
import io.github.aj8gh.aoc.properties.files
import io.github.aj8gh.aoc.theFollowingCommandWasExecuted
import io.github.aj8gh.aoc.whenOpenIsCalled
import kotlin.test.Test

class OpenHandlerKtTest : BaseTest() {

  @Test
  fun open() {
    val command = arrayOf(activeProfile().ide, files().projectHome)
    val runtime = givenTheRuntimeIsMocked(command)

    whenOpenIsCalled(runtime)

    theFollowingCommandWasExecuted(runtime, command)
  }
}
