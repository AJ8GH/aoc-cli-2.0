package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.BaseTest
import io.github.aj8gh.aoc.givenTheRuntimeIsMocked
import io.github.aj8gh.aoc.io.aocHomeDir
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.theFollowingCommandWasExecuted
import io.github.aj8gh.aoc.whenConfigFileIsCalled
import kotlin.test.Test

class HomeKtTest : BaseTest() {

  @Test
  fun home() {
    val command = arrayOf(activeProfile().ide, aocHomeDir())
    val runtime = givenTheRuntimeIsMocked(command)

    whenConfigFileIsCalled(runtime)

    theFollowingCommandWasExecuted(runtime, command)
  }
}
