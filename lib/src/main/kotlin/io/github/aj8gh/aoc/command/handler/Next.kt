package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D25
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.properties.Properties
import io.github.aj8gh.aoc.properties.activeProperties
import io.github.aj8gh.aoc.properties.updateProperties
import io.github.aj8gh.aoc.util.*
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

fun next() = next(true)

fun next(next: Boolean) = if (next) {
  updateProperties(updateCurrentProperties())
  echoCurrent()
} else Unit

private fun updateCurrentProperties(): Properties {
  val properties = activeProperties()

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
