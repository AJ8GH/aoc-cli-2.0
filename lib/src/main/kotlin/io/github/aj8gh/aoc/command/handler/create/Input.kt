package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.cache.cacheInput
import io.github.aj8gh.aoc.cache.getCachedInput
import io.github.aj8gh.aoc.http.getInput
import io.github.aj8gh.aoc.io.createResourcesDirIfNotExists
import io.github.aj8gh.aoc.io.inputFile
import io.github.aj8gh.aoc.io.write

fun input() {
  if (inputFile().exists()) return
  val input = getCachedInput()
  createResourcesDirIfNotExists()
  write(inputFile(), input ?: getAndCacheInput())
}

private fun getAndCacheInput(): String {
  val input = getInput()
  cacheInput(input)
  return input
}
