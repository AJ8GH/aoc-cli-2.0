package io.github.aj8gh.aoc.context

import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D12
import io.github.aj8gh.aoc.command.D25
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.command.Y15
import io.github.aj8gh.aoc.command.Y25
import java.time.Clock
import java.time.LocalDate
import kotlin.math.min

private const val YEAR_2000 = 2000
private const val DECEMBER = 12
private const val ONE_YEAR = 1

class DateManager(
  private val clock: Clock,
) {

  fun isPuzzleAvailable(year: Int, day: Int) = year < latestYear()
      || (year == latestYear() && day <= latestDay())

  fun latestYear() = year(LocalDate.now(clock)) % YEAR_2000
  fun yearRange() = Y15..latestYear()
  fun dayRange() = D1..D25
  fun levelRange() = L1..L2

  private fun latestDay(): Int {
    val now = LocalDate.now(clock)
    val maxDay = if (now.year % YEAR_2000 < Y25) D25 else D12
    if (now.monthValue == DECEMBER) {
      return min(now.dayOfMonth, maxDay)
    }
    return maxDay
  }

  private fun year(today: LocalDate) =
    today.year.takeIf { today.monthValue == DECEMBER } ?: (today.year - ONE_YEAR)
}
