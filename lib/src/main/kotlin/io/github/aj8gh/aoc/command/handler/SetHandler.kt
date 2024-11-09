package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.current
import io.github.aj8gh.aoc.properties.updateProfile

private const val TWO_THOUSAND = 2_000

class SetHandler(private val echoHandler: EchoHandler) {

  fun handle(year: Int?, day: Int?, level: Int?, verbose: Boolean = false) {
    if (year == null && day == null && level == null) return

    setNewValues(year, day, level)
    updateProfile(activeProfile())
    echoHandler.handle(verbose)
  }

  private fun setNewValues(year: Int?, day: Int?, level: Int?) {
    year?.let { current().year = it.mod(TWO_THOUSAND) }
    day?.let { current().day = it }
    level?.let { current().level = it }
  }
}
