package io.github.aj8gh.aoc.command.handler.create

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter
import io.github.aj8gh.aoc.cache.ReadmeCache
import io.github.aj8gh.aoc.cache.answer.AnswerCache
import io.github.aj8gh.aoc.http.ReadmeClient
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
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

class ReadmeCreator(
  private val cache: ReadmeCache,
  private val client: ReadmeClient,
  private val answerCache: AnswerCache,
  private val reader: Reader,
  private val writer: Writer,
  private val files: FileManager,
  private val log: Logger,
) {

  fun create() {
    if (!isReadmeStale(files.readmeFile())) {
      log.debug("README file ${files.inputFile()} is up to date, skipping README generation")
      return
    }
    files.createResourcesDirIfNotExists()
    writer.write(files.readmeFile(), format(toMarkdown(checkCacheOrGet())))
  }

  private fun checkCacheOrGet() =
    if (isReadmeStale(files.readmeCacheFile())) getAndCacheReadme()
    else reader.read(files.readmeCacheFile())

  private fun isReadmeStale(file: File) =
    cache.readmeLevel(file) < answerCache.dayCompletion()

  private fun getAndCacheReadme(): String {
    val html = client.getReadme()
    cache.cacheReadme(html)
    return html
  }

  private fun toMarkdown(html: String) = FlexmarkHtmlConverter
    .builder()
    .build()
    .convert(html)

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
