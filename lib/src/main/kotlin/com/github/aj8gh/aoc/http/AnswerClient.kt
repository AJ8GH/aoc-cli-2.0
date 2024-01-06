package com.github.aj8gh.aoc.http

import com.github.aj8gh.aoc.properties.aocProperties
import com.github.aj8gh.aoc.properties.current
import okhttp3.FormBody
import okhttp3.Request

private const val ANSWER_ENDPOINT = "/answer"

fun postAnswer(answer: String) =
    call(request(answer))

private fun request(answer: String) =
    Request.Builder()
        .post(answerForm(answer))
        .url(url(ANSWER_ENDPOINT))
        .addHeader(COOKIE, "$SESSION_KEY=${aocProperties().session}")
        .build()

private fun answerForm(answer: String) = FormBody.Builder()
    .add(LEVEL_KEY, current().level.toString())
    .add(ANSWER_KEY, answer)
    .build()
