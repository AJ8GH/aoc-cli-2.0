package com.github.aj8gh.aoc.command.handler

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.Test

private const val YEAR = 15
private const val DAY = 1
private const val LEVEL = "1"
private const val ANSWER = "123"
private const val TOKEN = "token"
private const val TOO_HIGH = "That's not the right answer; your answer is too high."
private const val TOO_LOW = "That's not the right answer; your answer is too low."
private const val WRONG_LEVEL = "You don't seem to be solving the right level.  Did you already complete it?"
private const val CORRECT = "Congratulations, that's the correct answer!"

@WireMockTest(httpPort = 80)
class AnswerKtTest : BaseTest() {

  @Test
  fun answer_HappyPath_NoCache() {
    // Given
    val expectedUrl = "/20${YEAR}/day/${DAY}/answer"

    stubFor(
      post(urlPathEqualTo(expectedUrl))
        .withCookie("session", matching(TOKEN))
        .withFormParam("level", equalTo(LEVEL))
        .withFormParam("answer", equalTo(ANSWER))
        .willReturn(
          responseDefinition()
            .withStatus(200)
            .withResponseBody(Body(CORRECT))
        )
    )

    // When
    answer(ANSWER)

    // Then
    verify(
      postRequestedFor(urlPathEqualTo(expectedUrl))
        .withCookie("session", matching(TOKEN))
        .withFormParam("level", equalTo(LEVEL))
        .withFormParam("answer", equalTo(ANSWER))
    )
  }
}
