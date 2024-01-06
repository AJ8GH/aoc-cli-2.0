package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.BaseTest
import com.github.aj8gh.aoc.HTTP_PORT
import com.github.aj8gh.aoc.SESSION
import com.github.aj8gh.aoc.TEST_RESOURCES_ROOT
import com.github.aj8gh.aoc.andNoReadmeExistsForToday
import com.github.aj8gh.aoc.givenTheFollowingRequestStub
import com.github.aj8gh.aoc.http.SESSION_KEY
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.thenTheFollowingRequestWasMade
import com.github.aj8gh.aoc.whenCreateReadmeIsCalled
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import kotlin.test.Test

private const val RESPONSE_DIR = "${TEST_RESOURCES_ROOT}response/"

@WireMockTest(httpPort = HTTP_PORT)
class ReadmeKtTest : BaseTest() {

  @Test
  fun readme() {
    val response = response()
    givenTheFollowingRequestStub(requestMapping(response))
    andNoReadmeExistsForToday()

    whenCreateReadmeIsCalled()

    thenTheFollowingRequestWasMade(requestPattern())
  }

  private fun requestMapping(response: String) = get(url())
      .withCookie(SESSION_KEY, matching(SESSION))
      .willReturn(responseDefinition()
          .withResponseBody(Body(response)))

  private fun response() = read("${RESPONSE_DIR}y${year()}/day/${day()}")

  private fun requestPattern() = getRequestedFor(urlEqualTo(url()))
      .withCookie(SESSION_KEY, matching(SESSION))

  private fun url() = "/20${year()}/day/${day()}"
}
