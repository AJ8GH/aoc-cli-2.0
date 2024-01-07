package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.command.handler.create.README_CACHE_FILE_NAME
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.aocHome
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import java.io.File

private const val README_CACHE = "cache/files/"

fun cacheReadme(html: String) {
  val dir = File(readmeCacheDir())
  if (!dir.exists()) dir.mkdirs()
  write(File(readmeCacheFile()), html)
}

fun readmeCacheFile() = "${readmeCacheDir()}$README_CACHE_FILE_NAME"

private fun readmeCacheDir() = "${aocHome()}${README_CACHE}y${year()}/d${day()}/"
