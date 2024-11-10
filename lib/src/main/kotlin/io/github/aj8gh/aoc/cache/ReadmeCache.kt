package io.github.aj8gh.aoc.cache

import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
import java.io.File

private const val PROVIDES_ONE_STAR = "It provides one gold star"
private const val PROVIDES_TWO_STARS = "They provide two gold stars"

class ReadmeCache(
  private val files: FileManager,
  private val writer: Writer,
  private val reader: Reader,
) {

  fun cacheReadme(html: String) {
    files.createResourcesCacheDirIfNotExists()
    writer.write(files.readmeCacheFile(), html)
  }

  fun readmeLevel(file: File): Int {
    if (!file.exists()) return -1
    val html = reader.read(file)
    return if (html.contains(PROVIDES_ONE_STAR)) 1
    else if (html.contains(PROVIDES_TWO_STARS)) 2
    else 0
  }
}
