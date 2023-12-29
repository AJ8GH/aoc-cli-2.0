package com.github.aj8gh.aoc.handler

import com.github.aj8gh.aoc.properties.getActiveProperties
import com.github.aj8gh.aoc.properties.updateProperties
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

fun set(year: Int?, day: Int?, level: Int?) {
  val properties = getActiveProperties()

  year?.let { properties.current.year = it }
  day?.let { properties.current.day = it }
  level?.let { properties.current.level = it }

  updateProperties(properties)
  logger.info { getActiveProperties() }
}
