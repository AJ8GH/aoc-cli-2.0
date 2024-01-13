package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.io.createResourcesCacheDirIfNotExists
import com.github.aj8gh.aoc.io.inputCacheFile
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.io.write

fun getCachedInput(): String? =
  if (inputCacheFile().exists()) read(inputCacheFile())
  else null

fun cacheInput(input: String) {
  createResourcesCacheDirIfNotExists()
  write(inputCacheFile(), input)
}
