package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D25
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.properties.Profile
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.updateProfile
import io.github.aj8gh.aoc.util.latestYear
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class NextHandler(private val echoHandler: EchoHandler) {

  fun next(verbose: Boolean) = next(true, verbose)

  fun next(flag: Boolean, verbose: Boolean) = if (flag) {
    updateProfile(updateCurrentProperties())
    echoHandler.echoCurrent(verbose)
  } else Unit

  private fun updateCurrentProperties(): Profile {
    val properties = activeProfile()

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
}
