package io.github.aj8gh.aoc.http

import io.github.aj8gh.aoc.properties.aocProperties
import io.github.aj8gh.aoc.properties.current
import okhttp3.FormBody
import okhttp3.Request

private const val ANSWER_ENDPOINT = "/answer"

class AnswerClient(private val aocClient: AocClient) {

  fun postAnswer(answer: String) =
    aocClient.call(postRequest(answer))

  private fun postRequest(answer: String) = Request.Builder()
    .post(answerForm(answer))
    .url(aocClient.url(ANSWER_ENDPOINT))
    .addHeader(COOKIE, "$SESSION_KEY=${aocProperties().session}")
    .build()

  private fun answerForm(answer: String) = FormBody.Builder()
    .add(LEVEL_KEY, current().level.toString())
    .add(ANSWER_KEY, answer)
    .build()
}
