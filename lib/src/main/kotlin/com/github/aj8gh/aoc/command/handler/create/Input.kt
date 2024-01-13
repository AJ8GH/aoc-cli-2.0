package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.cache.cacheInput
import com.github.aj8gh.aoc.cache.getCachedInput
import com.github.aj8gh.aoc.http.getInput
import com.github.aj8gh.aoc.io.createResourcesDirIfNotExists
import com.github.aj8gh.aoc.io.inputFile
import com.github.aj8gh.aoc.io.write

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
