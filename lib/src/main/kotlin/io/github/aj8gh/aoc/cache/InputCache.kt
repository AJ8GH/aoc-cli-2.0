package io.github.aj8gh.aoc.cache

import io.github.aj8gh.aoc.io.DirCreator
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer

class InputCache(
  private val files: FileManager,
  private val dirCreator: DirCreator,
  private val reader: Reader,
  private val writer: Writer,
  private val log: Logger,
) {

  fun getCachedInput(): String? {
    if (files.inputCacheFile().exists()) {
      log.info("Cached input found, reading from file ${files.inputCacheFile().absolutePath}...")
      return reader.read(files.inputCacheFile())
    }
    log.info("No cached input found at ${files.inputCacheFile().absolutePath}, retrieving from AoC...")
    return null
  }

  fun cacheInput(input: String) {
    log.info("Caching input to file ${files.inputCacheFile().absolutePath}...")
    dirCreator.mkdirs(files.inputCacheFile())
    writer.write(files.inputCacheFile(), input)
    log.info("Input cached to file ${files.inputCacheFile().absolutePath}")
  }
}
