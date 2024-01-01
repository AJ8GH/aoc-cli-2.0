package com.github.aj8gh.aoc.command.handler

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.http.RequestMethod
import com.github.tomakehurst.wiremock.http.RequestMethod.POST
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.marcinziolo.kotlin.wiremock.*
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest

private const val YEAR = 15
private const val DAY = 1
private const val LEVEL = "1"
private const val ANSWER = "123"
private const val TOKEN = "TOKEN"
private const val TOO_HIGH = "That's not the right answer; your answer is too high."
private const val TOO_LOW = "That's not the right answer; your answer is too low."
private const val WRONG_LEVEL = "You don't seem to be solving the right level.  Did you already complete it?"
private const val CORRECT = "Congratulations, that's the correct answer!"

@WireMockTest
class AnswerKtTest: BaseTest() {
  private lateinit var wiremock: WireMock
  private lateinit var baseUrl: String

  @BeforeTest
  fun setUp(wireMockRuntimeInfo: WireMockRuntimeInfo) {
    wiremock = wireMockRuntimeInfo.wireMock
    baseUrl = wireMockRuntimeInfo.httpBaseUrl
  }

  @Test
  fun answerTest() {
    // Given
    val expectedUrl = "20${YEAR}/day/${DAY}/answer"

    wiremock.post {
      url equalTo expectedUrl
      queryParams["level"] = EqualTo(LEVEL)
      queryParams["answer"] = EqualTo(ANSWER)
      cookies["session"] = EqualTo(TOKEN)
    } returns {
      statusCode = 200
      body = CORRECT
    }

    // When
    answer(ANSWER)

    // Then
    wiremock.verify {
      method = POST
      url equalTo expectedUrl
      queryParams["level"] = EqualTo(LEVEL)
      queryParams["answer"] = EqualTo(ANSWER)
      cookies["session"] = EqualTo(TOKEN)
    }
  }
}
