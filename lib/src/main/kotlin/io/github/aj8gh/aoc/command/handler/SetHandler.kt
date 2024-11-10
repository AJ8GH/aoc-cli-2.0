package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.properties.PropertiesManager

private const val TWO_THOUSAND = 2_000

class SetHandler(
  private val props: PropertiesManager,
  private val echoHandler: EchoHandler
) {

  fun handle(year: Int?, day: Int?, level: Int?, verbose: Boolean = false) {
    if (year == null && day == null && level == null) return

    setNewValues(year, day, level)
    props.updateProfile(props.activeProfile())
    echoHandler.handle(verbose)
  }

  private fun setNewValues(year: Int?, day: Int?, level: Int?) {
    year?.let { props.current().year = it.mod(TWO_THOUSAND) }
    day?.let { props.current().day = it }
    level?.let { props.current().level = it }
  }
}
