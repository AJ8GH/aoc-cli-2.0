package io.github.aj8gh.aoc.http

private const val README_ENDPOINT = ""

class ReadmeClient(private val aocClient: AocClient) {

  fun getReadme() = aocClient.get(README_ENDPOINT)
}
