package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.cache.InputCache
import io.github.aj8gh.aoc.http.InputClient
import io.github.aj8gh.aoc.io.createResourcesDirIfNotExists
import io.github.aj8gh.aoc.io.inputFile
import io.github.aj8gh.aoc.io.write

class InputCreator(
  private val cache: InputCache,
  private val client: InputClient,
) {

  fun create() {
    if (inputFile().exists()) return
    val input = cache.getCachedInput()
    createResourcesDirIfNotExists()
    write(inputFile(), input ?: getAndCacheInput())
  }

  private fun getAndCacheInput(): String {
    val input = client.getInput()
    cache.cacheInput(input)
    return input
  }
}
