package com.github.aj8gh.aoc.http

import okhttp3.FormBody
import okhttp3.Request

fun submitAnswer(answer: String, level: String, url: String, session: String) =
    call(request(answer, level, url, session))

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
