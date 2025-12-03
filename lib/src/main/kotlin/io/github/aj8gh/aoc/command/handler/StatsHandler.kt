package io.github.aj8gh.aoc.command.handler

import com.github.ajalt.mordant.table.Table
import com.github.ajalt.mordant.table.table
import io.github.aj8gh.aoc.cache.answer.AnswerCache
import io.github.aj8gh.aoc.command.Y25
import io.github.aj8gh.aoc.context.DateManager
import io.github.aj8gh.aoc.io.Console

private const val LONG_FORMAT_STARS = 50
private const val SHORT_FORMAT_STARS = 24

class StatsHandler(
  private val answerCache: AnswerCache,
  private val console: Console,
  private val dateManager: DateManager,
) {

  fun handle(flag: Boolean) {
    if (!flag) return
    console.echo(buildTable())
  }

  private fun buildTable(): Table {
    val cache = answerCache.cache()
    val stats = dateManager.yearRange().map { Stats(it, cache.year(it).completion()) }
    val years = stats.map { "Yr ${it.year}" }
    val completions = stats.map { "${it.completion}/${stars(it.year)}" }

    return table {
      header {
        rowFrom(years)
      }
      body {
        rowFrom(completions)
      }
    }
  }
}

private data class Stats(
  val year: Int,
  val completion: Int,
)

fun stars(year: Int) =
  if (year < Y25) LONG_FORMAT_STARS
  else SHORT_FORMAT_STARS
