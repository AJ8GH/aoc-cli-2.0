package io.github.aj8gh.aoc.cache

import io.github.aj8gh.aoc.io.createResourcesCacheDirIfNotExists
import io.github.aj8gh.aoc.io.read
import io.github.aj8gh.aoc.io.readmeCacheFile
import io.github.aj8gh.aoc.io.write
import java.io.File

private const val PROVIDES_ONE_STAR = "It provides one gold star"
private const val PROVIDES_TWO_STARS = "They provide two gold stars"

class ReadmeCache {

  fun cacheReadme(html: String) {
    createResourcesCacheDirIfNotExists()
    write(readmeCacheFile(), html)
  }

  fun readmeLevel(file: File): Int {
    if (!file.exists()) return -1
    val html = read(file)
    return if (html.contains(PROVIDES_ONE_STAR)) 1
    else if (html.contains(PROVIDES_TWO_STARS)) 2
    else 0
  }
}
