package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.http.SESSION_KEY
import com.github.aj8gh.aoc.util.D1
import com.github.aj8gh.aoc.util.Y15
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import kotlin.test.Test

private const val INPUT = """
      1 2 3
      4 5 6
      7 8 9
    """

@WireMockTest(httpPort = HTTP_PORT)
class InputKtTest : BaseTest() {

  @Test
  fun input() {
    val url = "/20$Y15/day/$D1/input"
    givenTheFollowingRequestStub(
      get(url)
        .withCookie(SESSION_KEY, matching(SESSION))
        .willReturn(
          ResponseDefinitionBuilder.responseDefinition()
            .withStatus(200)
            .withResponseBody(Body(INPUT))
        )
    )
    whenCreateInputIsCalled()
    thenTheFollowingRequestWasMade(
      getRequestedFor(urlEqualTo(url))
        .withCookie(SESSION_KEY, matching(SESSION))
    )
    thenTodaysInputIs(INPUT)
  }
}
