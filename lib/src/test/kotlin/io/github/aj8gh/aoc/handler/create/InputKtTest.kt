package io.github.aj8gh.aoc.handler.create

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.http.SESSION_KEY
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
    andTodaysInputIsNotCached()
    andTheFollowingRequestStub(getInputMapping())

    whenCreateInputIsCalled()

    thenTheFollowingRequestWasMade(getRequestDefinition())
    andTodaysInputExists()
    andTodaysInputIsCached()
  }

  @Test
  fun inputExistsAlready() {
    givenCurrentYearDayAndLevelAre(Y15, D2, L1)
    andTodaysInputIsNotCached()
    andTodaysInputFileAlreadyExists()

    whenCreateInputIsCalled()

    thenNoRequestsWereMadeForUrl(inputUrl())
    andTodaysInputExists()
  }

  @Test
  fun inputFromCache() {
    givenCurrentYearDayAndLevelAre(Y15, D3, L1)
    andTodaysInputFileDoesNotExist()
    andTodaysInputIsCached()

    whenCreateInputIsCalled()

    thenTodaysInputExists()
    andNoRequestsWereMadeForUrl(inputUrl())
  }

  private fun getRequestDefinition(): RequestPatternBuilder =
    getRequestedFor(urlEqualTo(inputUrl()))
      .withCookie(SESSION_KEY, matching(SESSION))

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
