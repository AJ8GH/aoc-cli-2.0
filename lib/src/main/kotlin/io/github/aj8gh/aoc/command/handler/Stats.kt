package io.github.aj8gh.aoc.command.handler

import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.cache.answer.answerCache
import io.github.aj8gh.aoc.command.YEAR_RANGE

data class Stats(
  val year: Int,
  val completion: Int,
)

fun stats(flag: Boolean, terminal: Terminal) {
  if (!flag) {
    return
  }

  val cache = answerCache()
  val stats = YEAR_RANGE.map { Stats(year = it, completion = cache.year(it).completion()) }
  val years = stats.map { "Yr ${it.year}" }
  val completions = stats.map { "${it.completion}/50" }
  val table = table {
    header {
      rowFrom(years)
    }
    body {
      rowFrom(completions)
    }
  }

  terminal.println(table)
}
