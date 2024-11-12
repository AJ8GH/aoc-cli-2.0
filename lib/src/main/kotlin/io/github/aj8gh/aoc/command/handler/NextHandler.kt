package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D25
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.context.DateManager
import io.github.aj8gh.aoc.properties.Profile
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class NextHandler(
  private val props: PropertiesManager,
  private val echoHandler: EchoHandler,
  private val dateManager: DateManager,
) {

  fun handle(verbose: Boolean) = handle(true, verbose)

  fun handle(flag: Boolean, verbose: Boolean) = if (flag) {
    props.updateProfile(updateCurrentProperties())
    echoHandler.handle(verbose)
  } else Unit

  private fun updateCurrentProperties(): Profile {
    if (props.level() == L1) {
      props.activeProfile().current.level = L2
      return props.activeProfile()
    }

    if (props.day() == D25) {
      if (props.year() == dateManager.latestYear()) {
        logger.warn { "You're already as far as you can go!" }
      } else {
        props.activeProfile().current.year++
        props.activeProfile().current.day = D1
        props.activeProfile().current.level--
      }
      return props.activeProfile()
    }

    props.activeProfile().current.day++
    props.activeProfile().current.level--
    return props.activeProfile()
  }
}
