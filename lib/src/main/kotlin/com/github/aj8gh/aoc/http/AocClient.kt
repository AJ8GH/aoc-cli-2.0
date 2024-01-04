package com.github.aj8gh.aoc.http

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

private const val LEVEL_KEY = "level"
private const val ANSWER_KEY = "answer"
private const val SESSION_KEY = "session"
private const val COOKIE = "Cookie"

fun submitAnswer(answer: String, level: String, url: String, session: String) =
    OkHttpClient()
        .newCall(request(answer, level, url, session))
        .execute()
        .use(::handle)

private fun request(answer: String, level: String, url: String, session: String) =
    Request.Builder()
        .post(answerForm(answer, level))
        .url(url)
        .addHeader(COOKIE, "$SESSION_KEY=$session")
        .build()

private fun answerForm(answer: String, level: String) = FormBody.Builder()
    .add(LEVEL_KEY, level)
    .add(ANSWER_KEY, answer)
    .build()

private fun handle(response: Response): String {
  if (!response.isSuccessful) {
    throw RuntimeException("${response.code} error, ${response.body?.string()}")
  }
  return response.body!!.string()
}
