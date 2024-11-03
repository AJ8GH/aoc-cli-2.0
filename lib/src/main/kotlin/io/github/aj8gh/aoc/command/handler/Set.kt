package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.current
import io.github.aj8gh.aoc.properties.updateProperties

const val TWO_THOUSAND = 2_000

fun set(year: Int?, day: Int?, level: Int?) {
  if (year == null && day == null && level == null) return

  val properties = activeProfile()
  year?.let { current().year = it.mod(TWO_THOUSAND) }
  day?.let { current().day = it }
  level?.let { current().level = it }
  updateProperties(properties)
  echoCurrent()
}
