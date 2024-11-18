package io.github.aj8gh.aoc.http

import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.properties.PropertiesManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

const val LEVEL_KEY = "level"
const val ANSWER_KEY = "answer"
const val SESSION_KEY = "session"
const val COOKIE = "Cookie"

class AocClient(
  private val props: PropertiesManager,
  private val log: Logger,
) {

  fun get(endpoint: String) = call(getRequest(endpoint))

  fun call(request: Request): String {
    log.info("Making ${request.method} request to ${request.url}")
    return OkHttpClient()
      .newCall(request)
      .execute()
      .use(::handle)
  }

  fun url(endpoint: String) =
    "${props.aocProperties().url}/20${props.year()}/day/${props.day()}$endpoint"

  private fun getRequest(endpoint: String) = Request.Builder()
    .get()
    .url(url(endpoint))
    .addHeader(COOKIE, "$SESSION_KEY=${props.aocProperties().session}")
    .build()

  private fun handle(response: Response): String {
    val code = response.code
    val method = response.request.method
    val body = response.body
    val url = response.request.url

    log.info("Received $code response from $method request to $url, ${response.message}")
    if (!response.isSuccessful) throw RuntimeException("$code error, ${response.message}")
    return body!!.string()
  }
}
