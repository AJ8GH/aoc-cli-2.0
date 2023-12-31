package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.cache.cacheReadme
import com.github.aj8gh.aoc.cache.dayCompletion
import com.github.aj8gh.aoc.cache.readmeCacheFile
import com.github.aj8gh.aoc.cache.readmeLevel
import com.github.aj8gh.aoc.http.getReadme
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.io.write
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import java.io.File

const val README_CACHE_FILE_NAME = "README.html"
const val README_FILE_NAME = "README.md"
const val TAIL_COMPLETE = "At this point, you should [return to your Advent calendar]"
const val TAIL_INCOMPLETE = "To begin, [get your puzzle input]"
const val TAIL_HALF_COMPLETE = "Answer:"
const val PROVIDES_ONE_STAR = "It provides one gold star"
const val PROVIDES_TWO_STARS = "They provide two gold stars"
const val ONE_STAR_OLD = "It provides one gold star: \\*"
const val ONE_STAR_NEW = "It provides one gold star: ⭐"
const val TWO_STAR_OLD = "They provide two gold stars: \\*\\*"
const val TWO_STAR_NEW = "They provide two gold stars: ⭐ ⭐"
const val HEADER_OLD = " {#part2}"
const val HEADER_NEW = ""

fun readme() {
  if (!isReadmeStale(readmeFile())) return

  val dir = resourcesDir()
  if (!dir.exists()) dir.mkdirs()
  write(readmeFile(), format(toMarkdown(checkCacheOrGet())))
}

fun readmeFile() = File("${resourcesDir()}/${README_FILE_NAME}")

private fun checkCacheOrGet() =
  if (isReadmeStale(File(readmeCacheFile()))) getAndCacheReadme()
  else read(readmeCacheFile())

private fun isReadmeStale(file: File) = readmeLevel(file) < dayCompletion()

private fun getAndCacheReadme(): String {
  val html = getReadme()
  cacheReadme(html)
  return html
}

private fun toMarkdown(html: String) =
  FlexmarkHtmlConverter.builder().build().convert(html)

private fun format(markdown: String) = head() + markdown
  .substringAfter(head())
  .substringAfter(head())
  .substringBefore(TAIL_HALF_COMPLETE)
  .substringBefore(TAIL_INCOMPLETE)
  .substringBefore(TAIL_COMPLETE)
  .replace(ONE_STAR_OLD, ONE_STAR_NEW)
  .replace(TWO_STAR_OLD, TWO_STAR_NEW)
  .replace(HEADER_OLD, HEADER_NEW)
  .trim()

private fun head() = "--- Day "
