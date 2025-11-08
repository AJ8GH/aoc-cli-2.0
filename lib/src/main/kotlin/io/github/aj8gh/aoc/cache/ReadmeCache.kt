package io.github.aj8gh.aoc.cache

import io.github.aj8gh.aoc.io.DirCreator
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
import java.io.File

private const val PROVIDES_ONE_STAR = "It provides one gold star"
private const val PROVIDES_TWO_STARS = "They provide two gold stars"

class ReadmeCache(
  private val files: FileManager,
  private val dirCreator: DirCreator,
  private val writer: Writer,
  private val reader: Reader,
  private val log: Logger,
) {

  fun cacheReadme(html: String) {
    log.info("Caching README to file ${files.readmeCacheFile().absolutePath}...")
    dirCreator.mkdirs(files.readmeCacheFile())
    writer.write(files.readmeCacheFile(), html)
    log.info("README cached to file ${files.readmeCacheFile().absolutePath}")
  }

  fun readmeLevel(file: File): Int {
    if (!file.exists()) return -1
    val html = reader.read(file)
    return if (html.contains(PROVIDES_ONE_STAR)) 1
    else if (html.contains(PROVIDES_TWO_STARS)) 2
    else 0
  }
}
