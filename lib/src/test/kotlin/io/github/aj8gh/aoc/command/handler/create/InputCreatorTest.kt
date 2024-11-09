package io.github.aj8gh.aoc.command.handler.create

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.http.SESSION_KEY
import io.github.aj8gh.aoc.test.*
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

@WireMockTest(httpPort = HTTP_PORT)
class InputCreatorTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun input(language: String, year: Int, day: Int, level: Int) {
    GIVEN
      .activeProfileIs(language)
      .currentYearDayAndLevelAre(year, day, level)
      .todaysInputFileDoesNotExist()
      .todaysInputIsNotCached()
      .theFollowingRequestStub(getInputMapping())

    WHEN
      .createInputIsCalled()

    THEN
      .theFollowingRequestWasMade(getRequestDefinition())
      .todaysInputExists()
      .todaysInputIsCached()
  }

  @ParameterizedTest
  @ValueSource(strings = [KT_PROFILE, GO_PROFILE])
  fun inputExistsAlready(file: String) {
    GIVEN
      .activeProfileIs(file)
      .currentYearDayAndLevelAre(Y15, D2, L1)
      .todaysInputIsNotCached()
      .todaysInputFileAlreadyExists()

    WHEN
      .createInputIsCalled()

    THEN
      .noRequestsWereMadeForUrl(inputUrl())
      .todaysInputExists()
  }

  @ParameterizedTest
  @ValueSource(strings = [KT_PROFILE, GO_PROFILE])
  fun inputFromCache(file: String) {
    GIVEN
      .activeProfileIs(file)
      .currentYearDayAndLevelAre(Y15, D3, L1)
      .todaysInputFileDoesNotExist()
      .todaysInputIsCached()

    WHEN
      .createInputIsCalled()

    THEN
      .todaysInputExists()
      .noRequestsWereMadeForUrl(inputUrl())
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
