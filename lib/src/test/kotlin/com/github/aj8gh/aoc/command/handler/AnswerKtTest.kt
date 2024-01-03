package com.github.aj8gh.aoc.command.handler

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.Test

private const val ANSWER = "123"
private const val LEVEL_KEY = "level"
private const val ANSWER_KEY = "answer"
private const val SESSION_KEY = "session"
private const val TOO_HIGH = "That's not the right answer; your answer is too high."
private const val TOO_LOW = "That's not the right answer; your answer is too low."
private const val WRONG_LEVEL = "You don't seem to be solving the right level.  Did you already complete it?"
private const val CORRECT = "Congratulations, that's the correct answer!"

@WireMockTest(httpPort = HTTP_PORT)
class AnswerKtTest : BaseTest() {

  @Test
  fun answer_HappyPath_NoCache() {
    val expectedUrl = expectedUrl(DEFAULT_YEAR, DEFAULT_DAY)
    givenTheFollowingRequestStub(
      post(urlPathEqualTo(expectedUrl))
        .withCookie(SESSION_KEY, matching(SESSION))
        .withFormParam(LEVEL_KEY, equalTo(DEFAULT_LEVEL.toString()))
        .withFormParam(ANSWER_KEY, equalTo(ANSWER))
        .willReturn(
          responseDefinition()
            .withStatus(200)
            .withResponseBody(Body(CORRECT))))

    whenAnswerIsCalledWith(ANSWER)

    thenTheFollowingRequestWasMade(
      postRequestedFor(urlPathEqualTo(expectedUrl))
        .withCookie("session", matching(SESSION))
        .withFormParam("level", equalTo(DEFAULT_LEVEL.toString()))
        .withFormParam("answer", equalTo(ANSWER)))

    thenCurrentYearDayAndLevelAre(DEFAULT_YEAR, DEFAULT_DAY, DEFAULT_LEVEL + 1)
    thenTheFollowingMessagesAreEchoed(
      CORRECT,
      getEchoMessage(DEFAULT_YEAR, DEFAULT_DAY, DEFAULT_LEVEL + 1))
  }

  @Test
  fun answer_HappyPath_Cached() {
    // Given
    val year = 16
    val day = 1
    val level = 1
    givenCurrentYearDayAndLevelAre(year = year, day = day, level = level)
    givenTheFollowingRequestStub(
      post(urlPathEqualTo(expectedUrl(year, day)))
        .withCookie("session", matching(SESSION))
        .withFormParam("level", equalTo(DEFAULT_LEVEL.toString()))
        .withFormParam("answer", equalTo(ANSWER))
        .willReturn(
          responseDefinition()
            .withStatus(200)
            .withResponseBody(Body(CORRECT))))

    // When
    whenAnswerIsCalledWith(ANSWER)

    // Then
    thenNoRequestsWereMadeForUrl(expectedUrl(year, day))
    thenCurrentYearDayAndLevelAre(year, day, level + 1)
    thenTheFollowingMessagesAreEchoed(
      CORRECT,
      getEchoMessage(year, day, level + 1))
  }

  private fun expectedUrl(year: Int, day: Int) = "/20$year/day/$day/answer"
}
