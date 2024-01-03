package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.properties.getActiveProperties
import com.github.aj8gh.aoc.properties.updateProperties

fun set(year: Int?, day: Int?, level: Int?) {
  val properties = getActiveProperties()

  year?.let { properties.current.year = it }
  day?.let { properties.current.day = it }
  level?.let { properties.current.level = it }

  updateProperties(properties)
  echoCurrent()
}
