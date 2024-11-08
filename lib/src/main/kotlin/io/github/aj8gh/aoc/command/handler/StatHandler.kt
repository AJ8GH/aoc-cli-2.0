package io.github.aj8gh.aoc.command.handler

import com.github.ajalt.mordant.table.Table
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.cache.answer.AnswerCacheManager
import io.github.aj8gh.aoc.command.YEAR_RANGE

class StatHandler(
  private val answerCacheManager: AnswerCacheManager,
  private val terminal: Terminal,
) {

  fun stats(flag: Boolean) {
    if (!flag) return
    terminal.println(buildTable())
  }

  private fun buildTable(): Table {
    val cache = answerCacheManager.answerCache()
    val stats = YEAR_RANGE.map { Stats(year = it, completion = cache.year(it).completion()) }
    val years = stats.map { "Yr ${it.year}" }
    val completions = stats.map { "${it.completion}/50" }

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
