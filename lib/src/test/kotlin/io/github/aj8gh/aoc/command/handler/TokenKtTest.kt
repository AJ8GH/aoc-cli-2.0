package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.*
import org.junit.jupiter.api.Test

class TokenKtTest : BaseTest() {

  @Test
  fun token() {
    givenTheCurrentTokenIs(SESSION)
    val newToken = "this-is-a-new-token"

    whenTokenIsCalled(newToken)

    thenTheTokenHasBeenUpdatedTo(newToken)
  }
}
