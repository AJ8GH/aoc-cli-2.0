package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D12
import io.github.aj8gh.aoc.command.D25
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.command.Y25
import io.github.aj8gh.aoc.context.DateManager
import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.properties.Profile
import io.github.aj8gh.aoc.properties.PropertiesManager

class NextHandler(
  private val props: PropertiesManager,
  private val echoHandler: EchoHandler,
  private val dateManager: DateManager,
  private val console: Console,
) {

  fun handle(verbose: Boolean) = handle(true, verbose)

  fun handle(flag: Boolean, verbose: Boolean) {
    if (!flag) return

    updateCurrentProperties()?.let {
      props.updateProfile(it)
      echoHandler.handle(verbose)
    }
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

    return if (props.year() == dateManager.latestYear()) {
      console.echo("You're already as far as you can go!")
      null
    } else {
      props.activeProfile().current.year++
      props.activeProfile().current.day = D1
      props.activeProfile().current.level--
      props.activeProfile()
    }
  }
}
