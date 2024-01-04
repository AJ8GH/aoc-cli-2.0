package com.github.aj8gh.aoc.http

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

const val LEVEL_KEY = "level"
const val ANSWER_KEY = "answer"
const val SESSION_KEY = "session"
const val COOKIE = "Cookie"

fun call(request: Request) = OkHttpClient()
    .newCall(request)
    .execute()
    .use(::handle)

private fun handle(response: Response) =
    if (!response.isSuccessful) {
      throw RuntimeException("${response.code} error, ${response.body?.string()}")
    } else response.body!!.string()
