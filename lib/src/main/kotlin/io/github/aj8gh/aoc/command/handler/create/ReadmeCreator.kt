package io.github.aj8gh.aoc.command.handler.create

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import io.github.aj8gh.aoc.cache.answer.dayCompletion
import io.github.aj8gh.aoc.cache.cacheReadme
import io.github.aj8gh.aoc.cache.readmeLevel
import io.github.aj8gh.aoc.http.getReadme
import io.github.aj8gh.aoc.io.*
import java.io.File

private const val TAIL_COMPLETE = "At this point, you should [return to your Advent calendar]"
private const val TAIL_INCOMPLETE = "To begin, [get your puzzle input]"
private const val TAIL_HALF_COMPLETE = "Answer:"
private const val ONE_STAR_OLD = "It provides one gold star: \\*"
private const val ONE_STAR_NEW = "It provides one gold star: ⭐"
private const val TWO_STAR_OLD = "They provide two gold stars: \\*\\*"
private const val TWO_STAR_NEW = "They provide two gold stars: ⭐ ⭐"
private const val HEADER_OLD = " {#part2}"
private const val HEADER_NEW = ""

class ReadmeCreator {

  fun create() {
    if (!isReadmeStale(readmeFile())) return
    createResourcesDirIfNotExists()
    write(readmeFile(), format(toMarkdown(checkCacheOrGet())))
  }

  private fun checkCacheOrGet() =
    if (isReadmeStale(readmeCacheFile())) getAndCacheReadme()
    else read(readmeCacheFile())

  private fun isReadmeStale(file: File) = readmeLevel(file) < dayCompletion()

  private fun getAndCacheReadme(): String {
    val html = getReadme()
    cacheReadme(html)
    return html
  }

  private fun toMarkdown(html: String) =
    FlexmarkHtmlConverter.builder().build().convert(html)

  private fun format(markdown: String) = "\\" + head() + markdown
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
}
