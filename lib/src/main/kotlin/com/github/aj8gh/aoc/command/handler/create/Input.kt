package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.cache.cacheInput
import com.github.aj8gh.aoc.cache.getCachedInput
import com.github.aj8gh.aoc.http.getInput
import com.github.aj8gh.aoc.io.write
import java.io.File

const val INPUT_FILE_NAME = "input.txt"

fun input() {
  if (inputFile().exists()) return
  val input = getCachedInput()
  write(getAndCreateFile(), input ?: getAndCacheInput())
}

fun inputFile() = File("${getResourcesDir()}/$INPUT_FILE_NAME")

private fun getAndCreateFile(): File {
  val dir = getResourcesDir()
  val file = inputFile()
  if (!dir.exists()) dir.mkdirs()
  if (!file.exists()) file.createNewFile()
  return file
}

private fun getAndCacheInput(): String {
  val input = getInput()
  cacheInput(input)
  return input
}
