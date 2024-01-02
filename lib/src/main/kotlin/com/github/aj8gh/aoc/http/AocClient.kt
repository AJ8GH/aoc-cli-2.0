package com.github.aj8gh.aoc.http

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

private const val LEVEL = "level"
private const val ANSWER = "answer"
private const val COOKIE = "Cookie"

private val client = OkHttpClient()

fun answer(answer: String, level: String, url: String, session: String): String {
  val request = Request.Builder()
    .post(answerForm(answer, level))
    .url(url)
    .addHeader(COOKIE, "session=${session}")
    .build()

  return client.newCall(request)
    .execute()
    .use(::handle)
}

private fun handle(response: Response): String {
  if (!response.isSuccessful) {
    throw RuntimeException("${response.code} error, ${response.body?.string()}")
  }

  return response.body!!.string()
}

private fun answerForm(answer: String, level: String) = FormBody.Builder()
  .add(LEVEL, level)
  .add(ANSWER, answer)
  .build()
