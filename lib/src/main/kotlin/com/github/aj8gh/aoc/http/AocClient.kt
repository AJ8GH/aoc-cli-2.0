package com.github.aj8gh.aoc.http

import com.github.aj8gh.aoc.properties.aocProperties
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
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

fun url(endpoint: String) =
  "${aocProperties().url}/20${year()}/day/${day()}$endpoint"

fun getRequest(endpoint: String) = Request.Builder()
  .get()
  .url(url(endpoint))
  .addHeader(COOKIE, "$SESSION_KEY=${aocProperties().session}")
  .build()

private fun handle(response: Response) =
  if (!response.isSuccessful)
    throw RuntimeException("${response.code} error, ${response.body?.string()}")
  else response.body!!.string()
