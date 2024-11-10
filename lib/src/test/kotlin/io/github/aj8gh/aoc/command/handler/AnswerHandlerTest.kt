package io.github.aj8gh.aoc.command.handler

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.github.aj8gh.aoc.command.*
import io.github.aj8gh.aoc.test.*
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

private const val LEVEL_KEY = "level"
private const val ANSWER_KEY = "answer"
private const val SESSION_KEY = "session"
private const val UNKNOWN = "UNKNOWN"
private const val DEFAULT_ANSWER_URL = "/20$Y15/day/$D1/answer"

@WireMockTest(httpPort = HTTP_PORT)
class AnswerHandlerTest : BaseTest() {

  @Test
  fun answer_HappyPath_NoCache() {
    GIVEN
      .theFollowingRequestStub(postMapping(CORRECT))
      .theFollowingRequestStub(readmeRequestMapping(html()))
      .theFollowingRequestStub(getInputMapping())

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .theFollowingRequestWasMade(postPattern())
      .currentYearDayAndLevelAre(Y15, D1, L2)
      .theFollowingMessagesAreEchoed(CORRECT, getEchoMessage(Y15, D1, L2, KT_PROFILE))

    resetAllRequests()
    // Answer should now be cached

    GIVEN
      .currentYearDayAndLevelAre(Y15, D1, L1)

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .noRequestsWereMadeForUrl(DEFAULT_ANSWER_URL)
      .currentYearDayAndLevelAre(Y15, D1, L2)
      .theFollowingMessagesAreEchoed(CORRECT, getEchoMessage(Y15, D1, L2, KT_PROFILE))
  }

  @Test
  fun answer_HappyPath_Cached() {
    GIVEN
      .currentYearDayAndLevelAre(year = Y15, day = D1, level = L2)
      .theFollowingRequestStub(getInputMapping(Y15, D2))
      .theFollowingRequestStub(readmeRequestMapping(html(Y15, D2), Y15, D2))

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .noRequestsWereMadeForUrl(DEFAULT_ANSWER_URL)
      .currentYearDayAndLevelAre(Y15, D2, L1)
      .todaysReadmeIsCreatedCorrectly(markdown())
      .todaysReadmeHasBeenCached(html())
      .todaysInputExists()
      .theFollowingMessagesAreEchoed(CORRECT, getEchoMessage(Y15, D2, L1, KT_PROFILE))
  }

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun answer_SadPath_Cached(answer: String, expectedResponse: String) {
    GIVEN
      .currentYearDayAndLevelAre(year = Y15, day = D1, level = L2)

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, answer))

    THEN
      .noRequestsWereMadeForUrl(DEFAULT_ANSWER_URL)
      .currentYearDayAndLevelAre(Y15, D1, L2)
      .theFollowingMessageIsEchoed(expectedResponse)
  }

  @ParameterizedTest
  @ValueSource(strings = [TOO_LOW, TOO_HIGH, INCORRECT, WRONG_LEVEL, UNKNOWN])
  fun answer_SadPath_NoCache(expectedResponse: String) {
    GIVEN
      .theFollowingRequestStub(postMapping(expectedResponse))

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .theFollowingRequestWasMade(postPattern())
      .currentYearDayAndLevelAre(Y15, D1, L1)
      .theFollowingMessageIsEchoed(expectedResponse)
  }

  private fun postMapping(response: String) = post(urlPathEqualTo(DEFAULT_ANSWER_URL))
    .withCookie(SESSION_KEY, matching(SESSION))
    .withFormParam(LEVEL_KEY, equalTo(L1.toString()))
    .withFormParam(ANSWER_KEY, equalTo(ANSWER))
    .willReturn(
      responseDefinition()
        .withStatus(200)
        .withResponseBody(Body(response))
    )

  private fun postPattern() = postRequestedFor(urlPathEqualTo(DEFAULT_ANSWER_URL))
    .withCookie(SESSION_KEY, matching(SESSION))
    .withFormParam(LEVEL_KEY, equalTo(L1.toString()))
    .withFormParam(ANSWER_KEY, equalTo(ANSWER))

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of("122", TOO_LOW),
      Arguments.of("124", TOO_HIGH),
      Arguments.of("abc", INCORRECT),
    )
  }
}
