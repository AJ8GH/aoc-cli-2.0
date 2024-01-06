package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.properties.activeProperties
import com.github.aj8gh.aoc.properties.current
import com.github.aj8gh.aoc.properties.updateProperties

fun set(year: Int?, day: Int?, level: Int?) {
  if (year == null && day == null && level == null) return

  val properties = activeProperties()
  year?.let { current().year = it }
  day?.let { current().day = it }
  level?.let { current().level = it }
  updateProperties(properties)
  echoCurrent()
}
