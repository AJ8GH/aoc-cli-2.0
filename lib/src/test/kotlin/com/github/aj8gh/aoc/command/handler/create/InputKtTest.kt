package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.BaseTest
import com.github.aj8gh.aoc.HTTP_PORT
import com.github.aj8gh.aoc.SESSION
import com.github.aj8gh.aoc.andNoRequestsWereMadeForUrl
import com.github.aj8gh.aoc.andTheFollowingRequestStub
import com.github.aj8gh.aoc.andTodaysInputExists
import com.github.aj8gh.aoc.andTodaysInputFileAlreadyExists
import com.github.aj8gh.aoc.andTodaysInputFileDoesNotExist
import com.github.aj8gh.aoc.andTodaysInputIsCached
import com.github.aj8gh.aoc.givenCurrentYearDayAndLevelAre
import com.github.aj8gh.aoc.http.SESSION_KEY
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.testInput
import com.github.aj8gh.aoc.thenNoRequestsWereMadeForUrl
import com.github.aj8gh.aoc.thenTheFollowingRequestWasMade
import com.github.aj8gh.aoc.thenTodaysInputExists
import com.github.aj8gh.aoc.util.D1
import com.github.aj8gh.aoc.util.D2
import com.github.aj8gh.aoc.util.D25
import com.github.aj8gh.aoc.util.D3
import com.github.aj8gh.aoc.util.L1
import com.github.aj8gh.aoc.util.L2
import com.github.aj8gh.aoc.util.Y15
import com.github.aj8gh.aoc.util.Y16
import com.github.aj8gh.aoc.util.Y17
import com.github.aj8gh.aoc.whenCreateInputIsCalled
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test

@WireMockTest(httpPort = HTTP_PORT)
class InputKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun input(year: Int, day: Int, level: Int) {
    givenCurrentYearDayAndLevelAre(year, day, level)
    andTodaysInputFileDoesNotExist()
    andTheFollowingRequestStub(getInputMapping())

    whenCreateInputIsCalled()

    thenTheFollowingRequestWasMade(getRequestDefinition())
    andTodaysInputExists()
    andTodaysInputIsCached()
  }

  @Test
  fun inputExistsAlready() {
    givenCurrentYearDayAndLevelAre(Y15, D2, L1)
    andTodaysInputFileAlreadyExists()

    whenCreateInputIsCalled()

    thenNoRequestsWereMadeForUrl(url())
  }

  @Test
  fun inputFromCache() {
    givenCurrentYearDayAndLevelAre(Y15, D3, L1)
    andTodaysInputFileDoesNotExist()
    andTodaysInputIsCached()

    whenCreateInputIsCalled()

    thenTodaysInputExists()
    andNoRequestsWereMadeForUrl(url())
  }

  private fun getInputMapping(): MappingBuilder =
      get(url())
          .withCookie(SESSION_KEY, matching(SESSION))
          .willReturn(ResponseDefinitionBuilder.responseDefinition()
              .withStatus(200)
              .withResponseBody(Body(testInput())))

  private fun getRequestDefinition(): RequestPatternBuilder =
      getRequestedFor(urlEqualTo(url()))
          .withCookie(SESSION_KEY, matching(SESSION))

  private fun url() = "/20${year()}/day/${day()}/input"

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
        Arguments.of(Y15, D1, L1),
        Arguments.of(Y15, D25, L2),
        Arguments.of(Y16, D1, L1),
        Arguments.of(Y17, D3, L2),
    )
  }
}
