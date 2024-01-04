package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.properties.getActiveProperties
import com.github.aj8gh.aoc.properties.getCurrent
import com.github.aj8gh.aoc.properties.updateProperties

fun set(year: Int?, day: Int?, level: Int?) {
  if (year == null && day == null && level == null) return

  val properties = getActiveProperties()
  year?.let { getCurrent().year = it }
  day?.let { getCurrent().day = it }
  level?.let { getCurrent().level = it }
  updateProperties(properties)
  echoCurrent()
}
