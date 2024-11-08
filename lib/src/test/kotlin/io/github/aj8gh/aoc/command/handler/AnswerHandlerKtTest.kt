package io.github.aj8gh.aoc.command.handler

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.command.*
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
class AnswerHandlerKtTest : BaseTest() {

  @Test
  fun answer_HappyPath_NoCache() {
    givenTheFollowingRequestStub(postMapping(CORRECT))
    andTheFollowingRequestStub(readmeRequestMapping(html()))
    andTheFollowingRequestStub(getInputMapping())

    whenAnswerIsCalledWith(ANSWER)

    thenTheFollowingRequestWasMade(postPattern())
    andCurrentYearDayAndLevelAre(Y15, D1, L2)
    andTheFollowingMessagesAreEchoed(CORRECT, getEchoMessage(Y15, D1, L2, KT_PROFILE))

    resetAllRequests()

    // Answer should now be cached
    givenCurrentYearDayAndLevelAre(Y15, D1, L1)

    whenAnswerIsCalledWith(ANSWER)

    thenNoRequestsWereMadeForUrl(DEFAULT_ANSWER_URL)
    andCurrentYearDayAndLevelAre(Y15, D1, L2)
    andTheFollowingMessagesAreEchoed(CORRECT, getEchoMessage(Y15, D1, L2, KT_PROFILE))
  }

  @Test
  fun answer_HappyPath_Cached() {
    givenCurrentYearDayAndLevelAre(year = Y15, day = D1, level = L2)
    andTheFollowingRequestStub(getInputMapping(Y15, D2))
    andTheFollowingRequestStub(readmeRequestMapping(html(Y15, D2), Y15, D2))

    whenAnswerIsCalledWith(ANSWER)

    thenNoRequestsWereMadeForUrl(DEFAULT_ANSWER_URL)
    andCurrentYearDayAndLevelAre(Y15, D2, L1)
    andTodaysReadmeIsCreatedCorrectly(markdown())
    andTodaysReadmeHasBeenCached(html())
    andTodaysInputExists()
    andTheFollowingMessagesAreEchoed(CORRECT, getEchoMessage(Y15, D2, L1, KT_PROFILE))
  }

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun answer_SadPath_Cached(answer: String, expectedResponse: String) {
    givenCurrentYearDayAndLevelAre(year = Y15, day = D1, level = L2)

    whenAnswerIsCalledWith(answer)

    thenNoRequestsWereMadeForUrl(DEFAULT_ANSWER_URL)
    andCurrentYearDayAndLevelAre(Y15, D1, L2)
    andTheFollowingMessageIsEchoed(expectedResponse)
  }

  @ParameterizedTest
  @ValueSource(strings = [TOO_LOW, TOO_HIGH, INCORRECT, WRONG_LEVEL, UNKNOWN])
  fun answer_SadPath_NoCache(expectedResponse: String) {
    givenTheFollowingRequestStub(postMapping(expectedResponse))

    whenAnswerIsCalledWith(ANSWER)

    thenTheFollowingRequestWasMade(postPattern())
    andCurrentYearDayAndLevelAre(Y15, D1, L1)
    andTheFollowingMessageIsEchoed(expectedResponse)
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
