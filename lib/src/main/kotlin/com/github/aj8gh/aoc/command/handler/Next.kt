package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.properties.Properties
import com.github.aj8gh.aoc.properties.getActiveProperties
import com.github.aj8gh.aoc.properties.updateProperties
import com.github.aj8gh.aoc.util.*
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

fun next() = next(true)

fun next(next: Boolean) {
  if (next) {
    updateProperties(updateCurrentProperties())
    echoCurrent()
  }
}

private fun updateCurrentProperties(): Properties {
  val properties = getActiveProperties()

  val year = properties.current.year
  val day = properties.current.day
  val level = properties.current.level

  if (level == L1) {
    properties.current.level = L2
    return properties
  }

  if (day == D25) {
    if (year == latestYear()) {
      logger.warn { "You're already as far as you can go!" }
    } else {
      properties.current.year++
      properties.current.day = D1
      properties.current.level--
    }
    return properties
  }

  properties.current.day++
  properties.current.level--

  return properties
}
