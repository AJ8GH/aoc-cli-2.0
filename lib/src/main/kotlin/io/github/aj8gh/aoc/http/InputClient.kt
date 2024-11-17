package io.github.aj8gh.aoc.http

class InputClient(
  private val aocClient: AocClient,
  private val endpoint: String,
) {

  fun getInput() = aocClient.get(endpoint)
}
