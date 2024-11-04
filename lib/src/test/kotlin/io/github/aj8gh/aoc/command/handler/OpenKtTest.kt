package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.BaseTest
import io.github.aj8gh.aoc.givenTheRuntimeIsMocked
import io.github.aj8gh.aoc.theRuntimeIsInvoked
import io.github.aj8gh.aoc.whenOpenIsCalled
import kotlin.test.Test

class OpenKtTest : BaseTest() {

  @Test
  fun open() {
    val runtime = givenTheRuntimeIsMocked()

    whenOpenIsCalled(runtime)

    theRuntimeIsInvoked(runtime)
  }
}
