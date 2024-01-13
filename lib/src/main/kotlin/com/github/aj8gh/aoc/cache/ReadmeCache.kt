package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.command.handler.create.PROVIDES_ONE_STAR
import com.github.aj8gh.aoc.command.handler.create.PROVIDES_TWO_STARS
import com.github.aj8gh.aoc.io.createResourcesCacheDirIfNotExists
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.io.readmeCacheFile
import com.github.aj8gh.aoc.io.write
import java.io.File

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
