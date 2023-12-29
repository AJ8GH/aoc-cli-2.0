package com.github.aj8gh.aoc.util

import java.time.LocalDate
import java.time.ZoneId

fun latestYear() = println(LocalDate.now(ZoneId.of("EST/EDT")).year)
