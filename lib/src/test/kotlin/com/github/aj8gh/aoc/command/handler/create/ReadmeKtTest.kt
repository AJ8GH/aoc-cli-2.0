package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.http.SESSION_KEY
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.util.*
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test

private const val HTML_DIR = "${TEST_RESOURCES_ROOT}html/"
private const val MARKDOWN_DIR = "${TEST_RESOURCES_ROOT}markdown/"

@WireMockTest(httpPort = HTTP_PORT)
class ReadmeKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun readme(year: Int, day: Int, level: Int) {
    givenCurrentYearDayAndLevelAre(year, day, level)
    andTheFollowingRequestStub(requestMapping(html()))
    andNoReadmeExistsForToday()
    andTodaysReadmeIsNotCached()

    whenCreateReadmeIsCalled()

    thenTheFollowingRequestWasMade(requestPattern())
    andTodaysReadmeIsCreatedCorrectly(markdown())
    andTodaysReadmeIsCached(html())
  }

  @Test
  fun readmeExists() {
    givenCurrentYearDayAndLevelAre(Y15, D2, L1)
    andTodaysReadmeExists()

    whenCreateReadmeIsCalled()

    thenNoRequestsWereMadeForUrl(url())
  }

  @Test
  fun readmeCached() {
    givenCurrentYearDayAndLevelAre(Y15, D3, L1)
    andNoReadmeExistsForToday()
    andTodaysReadmeIsCached(html())

    whenCreateReadmeIsCalled()

    thenNoRequestsWereMadeForUrl(url())
    andTodaysReadmeIsCreatedCorrectly(markdown())
  }

  private fun requestMapping(response: String) = get(url())
    .withCookie(SESSION_KEY, matching(SESSION))
    .willReturn(
      responseDefinition()
        .withResponseBody(Body(response))
    )

  private fun html() = read("${HTML_DIR}y${year()}/d${day()}.html")

  private fun markdown() = read("${MARKDOWN_DIR}y${year()}/d${day()}.md")

  private fun requestPattern() = getRequestedFor(urlEqualTo(url()))
    .withCookie(SESSION_KEY, matching(SESSION))

  private fun url() = "/20${year()}/day/${day()}"

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(Y15, D1, L1),
      Arguments.of(Y23, D5, L1),
      Arguments.of(Y23, D6, L1),
    )
  }
}
