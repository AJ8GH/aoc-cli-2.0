package io.github.aj8gh.aoc.context

import io.github.aj8gh.aoc.command.*
import java.time.Clock
import java.time.LocalDate

private const val YEAR_2000 = 2000
private const val DECEMBER = 12
private const val ONE_YEAR = 1

class DateManager(private val clock: Clock) {

  fun latestYear() = year(LocalDate.now(clock)) % YEAR_2000
  fun yearRange() = Y15..latestYear()
  fun dayRange() = D1..D25
  fun levelRange() = L1..L2

  private fun year(today: LocalDate) =
    today.year.takeIf { today.monthValue == DECEMBER } ?: (today.year - ONE_YEAR)
}
