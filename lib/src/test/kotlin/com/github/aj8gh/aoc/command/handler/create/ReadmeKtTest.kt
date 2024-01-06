package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.http.SESSION_KEY
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import kotlin.test.Test

private const val HTML_DIR = "${TEST_RESOURCES_ROOT}html/"

@WireMockTest(httpPort = HTTP_PORT)
class ReadmeKtTest : BaseTest() {

  @Test
  fun readme() {
    givenTheFollowingRequestStub(requestMapping(html()))
    andNoReadmeExistsForToday()
    andTodaysReadmeIsNotCached()

    whenCreateReadmeIsCalled()

    thenTheFollowingRequestWasMade(requestPattern())
    andTodaysReadmeIsCreatedCorrectly()
    andTodaysReadmeIsCached()
  }

  private fun requestMapping(response: String) = get(url())
    .withCookie(SESSION_KEY, matching(SESSION))
    .willReturn(
      responseDefinition()
        .withResponseBody(Body(response))
    )

  private fun html() = read("${HTML_DIR}y${year()}/day/${day()}")

  private fun requestPattern() = getRequestedFor(urlEqualTo(url()))
    .withCookie(SESSION_KEY, matching(SESSION))

  private fun url() = "/20${year()}/day/${day()}"
}
