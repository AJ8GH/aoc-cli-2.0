package io.github.aj8gh.aoc.http

import io.github.aj8gh.aoc.properties.PropertiesManager
import okhttp3.FormBody
import okhttp3.Request

class AnswerClient(
  private val aocClient: AocClient,
  private val props: PropertiesManager,
  private val endpoint: String
) {

  fun postAnswer(answer: String) =
    aocClient.call(postRequest(answer))

  private fun postRequest(answer: String) = Request.Builder()
    .post(answerForm(answer))
    .url(aocClient.url(endpoint))
    .addHeader(COOKIE, "$SESSION_KEY=${props.aocProperties().session}")
    .build()

  private fun answerForm(answer: String) = FormBody.Builder()
    .add(LEVEL_KEY, props.current().level.toString())
    .add(ANSWER_KEY, answer)
    .build()
}
