package io.github.aj8gh.aoc.cache

import io.github.aj8gh.aoc.io.DirCreator
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer

class InputCache(
  private val files: FileManager,
  private val dirCreator: DirCreator,
  private val reader: Reader,
  private val writer: Writer,
) {

  fun getCachedInput(): String? =
    if (files.inputCacheFile().exists()) reader.read(files.inputCacheFile())
    else null

  fun cacheInput(input: String) {
    dirCreator.mkdirs(files.inputCacheFile())
    writer.write(files.inputCacheFile(), input)
  }
}
