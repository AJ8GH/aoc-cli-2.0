package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.cache.InputCache
import io.github.aj8gh.aoc.http.InputClient
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Writer

class InputCreator(
  private val cache: InputCache,
  private val client: InputClient,
  private val writer: Writer,
  private val files: FileManager,
) {

  fun create() {
    if (files.inputFile().exists()) return
    val input = cache.getCachedInput()
    files.createResourcesDirIfNotExists()
    writer.write(files.inputFile(), input ?: getAndCacheInput())
  }

  private fun getAndCacheInput(): String {
    val input = client.getInput()
    cache.cacheInput(input)
    return input
  }
}
