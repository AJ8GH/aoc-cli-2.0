package io.github.aj8gh.aoc.http

private const val INPUT_ENDPOINT = "/input"

class InputClient(private val aocClient: AocClient) {

  fun getInput() = aocClient.get(INPUT_ENDPOINT)
}
