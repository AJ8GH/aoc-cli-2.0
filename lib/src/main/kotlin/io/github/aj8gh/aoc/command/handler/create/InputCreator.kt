package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.cache.InputCache
import io.github.aj8gh.aoc.http.InputClient
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Writer

class InputCreator(
  private val cache: InputCache,
  private val client: InputClient,
  private val writer: Writer,
  private val files: FileManager,
  private val log: Logger,
) {

  fun create() {
    if (files.inputFile().exists()) {
      log.debug("File ${files.inputFile().absolutePath} already exists, skipping input generation")
      return
    }
    val input = cache.getCachedInput()
    files.createResourcesDirIfNotExists()
    writer.write(files.inputFile(), input ?: getAndCacheInput())
    log.info("Input written to file ${files.inputFile().absolutePath}")
  }

  private fun getAndCacheInput(): String {
    val input = client.getInput()
    cache.cacheInput(input)
    return input
  }
}
