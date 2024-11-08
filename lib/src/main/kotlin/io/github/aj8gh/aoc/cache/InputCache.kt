package io.github.aj8gh.aoc.cache

import io.github.aj8gh.aoc.io.createResourcesCacheDirIfNotExists
import io.github.aj8gh.aoc.io.inputCacheFile
import io.github.aj8gh.aoc.io.read
import io.github.aj8gh.aoc.io.write

class InputCache {

  fun getCachedInput(): String? =
    if (inputCacheFile().exists()) read(inputCacheFile())
    else null

  fun cacheInput(input: String) {
    createResourcesCacheDirIfNotExists()
    write(inputCacheFile(), input)
  }
}
