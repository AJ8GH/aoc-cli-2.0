package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.http.SESSION_KEY
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.util.*
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.*
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
    givenTodaysInputFileDoesNotExist()
    givenTheFollowingRequestStub(getInputMapping())
    whenCreateInputIsCalled()
    thenTheFollowingRequestWasMade(getRequestDefinition())
    thenTodaysInputExists()
  }

  @Test
  fun inputExistsAlready() {
    givenCurrentYearDayAndLevelAre(Y15, D2, L1)
    givenTodaysInputFileAlreadyExists()
    whenCreateInputIsCalled()
    thenNoRequestsWereMadeForUrl(url())
  }

  private fun getInputMapping(): MappingBuilder =
      get(url())
          .withCookie(SESSION_KEY, matching(SESSION))
          .willReturn(ResponseDefinitionBuilder.responseDefinition()
              .withStatus(200)
              .withResponseBody(Body(INPUT)))

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
