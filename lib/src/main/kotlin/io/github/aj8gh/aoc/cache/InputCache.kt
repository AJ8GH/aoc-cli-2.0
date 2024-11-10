package io.github.aj8gh.aoc.cache

import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer

class InputCache(
  private val files: FileManager,
  private val reader: Reader,
  private val writer: Writer,
) {

  fun getCachedInput(): String? =
    if (files.inputCacheFile().exists()) reader.read(files.inputCacheFile())
    else null

  fun cacheInput(input: String) {
    files.createResourcesCacheDirIfNotExists()
    writer.write(files.inputCacheFile(), input)
  }
}
