package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D12
import io.github.aj8gh.aoc.command.D25
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.command.Y25
import io.github.aj8gh.aoc.context.DateManager
import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.properties.Profile
import io.github.aj8gh.aoc.properties.PropertiesManager

class NextHandler(
  private val props: PropertiesManager,
  private val echoHandler: EchoHandler,
  private val dateManager: DateManager,
  private val console: Console,
  private val log: Logger,
) {

  fun handle(verbose: Boolean) = handle(true, verbose)

  fun handle(flag: Boolean, verbose: Boolean) {
    if (!flag) {
      log.debug("Skipping handling of 'next' command as flag is false")
      return
    }

    log.info("Handling 'next' command, current: year ${props.year()} day ${props.day()} level ${props.level()}")
    updateCurrentProperties()?.let {
      props.updateProfile(it)
      echoHandler.handle(verbose)
    }
    log.info("'next' command handled, now at: year ${props.year()} day ${props.day()} level ${props.level()}")
  }

  private fun updateCurrentProperties(): Profile? {
    if (props.level() == L1) {
      props.activeProfile().current.level = L2
      return props.activeProfile()
    }

    if (
      (props.year() >= Y25 && props.day() < D12)
      || (props.year() < Y25 && props.day() < D25)
    ) {
      props.activeProfile().current.day++
      props.activeProfile().current.level--
      return props.activeProfile()
    }

    return if (props.year() == dateManager.latestYearEst()) {
      console.echo("You're already as far as you can go!")
      log.warn("Unable to process 'next' command as already at maximum year, day and level")
      null
    } else {
      props.activeProfile().current.year++
      props.activeProfile().current.day = D1
      props.activeProfile().current.level--
      props.activeProfile()
    }
  }
}
