package io.github.aj8gh.aoc.http

class ReadmeClient(
  private val aocClient: AocClient,
  private val endpoint: String,
) {

  fun getReadme() = aocClient.get(endpoint)
}
