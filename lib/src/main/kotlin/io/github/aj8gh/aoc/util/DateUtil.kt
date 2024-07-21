package io.github.aj8gh.aoc.util

import java.time.Clock
import java.time.Clock.systemUTC
import java.time.LocalDate

private const val YEAR_2000 = 2000
private const val DECEMBER = 12
private const val ONE_YEAR = 1

fun latestYear() = latestYear(systemUTC())

fun latestYear(clock: Clock) = year(LocalDate.now(clock)) % YEAR_2000

private fun year(today: LocalDate) = today.year
  .takeIf { today.monthValue == DECEMBER }
  ?: (today.year - ONE_YEAR)
