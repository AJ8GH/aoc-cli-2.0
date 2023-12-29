package com.github.aj8gh.aoc.util

import java.time.Clock
import java.time.LocalDate

val CLOCK: Clock = Clock.systemUTC()

fun latestYear() = latestYear(CLOCK)

fun latestYear(clock: Clock) = year(LocalDate.now(clock)) % 2000

private fun year(today: LocalDate) = today.year.takeIf {
  today.monthValue == 12
} ?: (today.year - 1)
