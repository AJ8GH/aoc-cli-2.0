package io.github.aj8gh.aoc.http

import io.github.aj8gh.aoc.properties.PropertiesManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

const val LEVEL_KEY = "level"
const val ANSWER_KEY = "answer"
const val SESSION_KEY = "session"
const val COOKIE = "Cookie"

class AocClient(
  private val props: PropertiesManager
) {

  fun get(endpoint: String) = call(getRequest(endpoint))

  fun call(request: Request) = OkHttpClient()
    .newCall(request)
    .execute()
    .use(::handle)

  fun url(endpoint: String) =
    "${props.aocProperties().url}/20${props.year()}/day/${props.day()}$endpoint"

  private fun getRequest(endpoint: String) = Request.Builder()
    .get()
    .url(url(endpoint))
    .addHeader(COOKIE, "$SESSION_KEY=${props.aocProperties().session}")
    .build()

  private fun handle(response: Response) =
    if (!response.isSuccessful)
      throw RuntimeException("${response.code} error, ${response.body?.string()}")
    else response.body!!.string()
}
