package io.github.aj8gh.aoc.command.handler.create

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.http.SESSION_KEY
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

@WireMockTest(httpPort = HTTP_PORT)
class InputCreateHandlerKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun input(language: String, year: Int, day: Int, level: Int) {
    givenActivePropertiesIsSetTo(language)
    givenCurrentYearDayAndLevelAre(year, day, level)
    andTodaysInputFileDoesNotExist()
    andTodaysInputIsNotCached()
    andTheFollowingRequestStub(getInputMapping())

    whenCreateInputIsCalled()

    thenTheFollowingRequestWasMade(getRequestDefinition())
    andTodaysInputExists()
    andTodaysInputIsCached()
  }

  @ParameterizedTest
  @ValueSource(strings = [KT_PROFILE, GO_PROFILE])
  fun inputExistsAlready(file: String) {
    givenActivePropertiesIsSetTo(file)
    givenCurrentYearDayAndLevelAre(Y15, D2, L1)
    andTodaysInputIsNotCached()
    andTodaysInputFileAlreadyExists()

    whenCreateInputIsCalled()

    thenNoRequestsWereMadeForUrl(inputUrl())
    andTodaysInputExists()
  }

  @ParameterizedTest
  @ValueSource(strings = [KT_PROFILE, GO_PROFILE])
  fun inputFromCache(file: String) {
    givenActivePropertiesIsSetTo(file)
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
      Arguments.of(KT_PROFILE, Y15, D1, L1),
      Arguments.of(KT_PROFILE, Y15, D25, L2),
      Arguments.of(KT_PROFILE, Y16, D1, L1),
      Arguments.of(KT_PROFILE, Y17, D3, L2),

      Arguments.of(GO_PROFILE, Y15, D1, L1),
      Arguments.of(GO_PROFILE, Y15, D25, L2),
      Arguments.of(GO_PROFILE, Y16, D1, L1),
      Arguments.of(GO_PROFILE, Y17, D3, L2),
    )
  }
}
