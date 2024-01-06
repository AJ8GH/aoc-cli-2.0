package com.github.aj8gh.aoc.http

import com.github.aj8gh.aoc.properties.aocProperties
import okhttp3.Request

private const val INPUT_ENDPOINT = "/input"

fun getInput() = call(request())

private fun request() =
  Request.Builder()
    .get()
    .url(url(INPUT_ENDPOINT))
    .addHeader(COOKIE, "$SESSION_KEY=${aocProperties().session}")
    .build()
