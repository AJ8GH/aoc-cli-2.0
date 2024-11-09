package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.SESSION
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.api.Test

private const val NEW_TOKEN = "this-is-a-new-token"

class TokenTest : BaseTest() {

  @Test
  fun token() {
    GIVEN
      .theCurrentTokenIs(SESSION)

    WHEN
      .tokenIsCalled(NEW_TOKEN)

    THEN
      .theTokenHasBeenUpdatedTo(NEW_TOKEN)
  }
}
