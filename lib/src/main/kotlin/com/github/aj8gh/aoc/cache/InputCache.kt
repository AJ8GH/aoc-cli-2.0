package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.command.handler.create.INPUT_FILE_NAME
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.aocHome
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import java.io.File

private const val INPUT_CACHE = "cache/files/"

fun getCachedInput(): String? =
    if (File(inputCacheFile()).exists()) read(inputCacheFile())
    else null

fun cacheInput(input: String) {
  val dir = File(inputCacheDir())
  val file = File(inputCacheFile())
  if (!dir.exists()) dir.mkdirs()
  if (!file.exists()) file.createNewFile()
  write(file, input)
}

fun inputCacheFile() = "${inputCacheDir()}$INPUT_FILE_NAME"

private fun inputCacheDir() = "${aocHome()}${INPUT_CACHE}y${year()}/d${day()}/"
