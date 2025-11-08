package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.properties.PropertiesManager

private const val TWO_THOUSAND = 2_000

class SetHandler(
  private val props: PropertiesManager,
  private val echoHandler: EchoHandler,
  private val log: Logger,
) {

  fun handle(year: Int?, day: Int?, level: Int?, verbose: Boolean = false) {
    if (year == null && day == null && level == null) return

    log.info(
      "Setting values, " +
          "current values: year ${props.year()} day ${props.day()} level ${props.level()}, " +
          "new values: year $year day $day level $level"
    )
    setNewValues(year, day, level)
    props.updateProfile(props.activeProfile())
    echoHandler.handle(verbose)
    log.info("Updated values: year ${props.year()} day ${props.day()} level ${props.level()}")
  }

  private fun setNewValues(year: Int?, day: Int?, level: Int?) {
    year?.let { props.current().year = it.mod(TWO_THOUSAND) }
    day?.let { props.current().day = it }
    level?.let { props.current().level = it }
  }
}
