package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.properties.PropertiesManager

class OpenHandler(
  private val props: PropertiesManager,
  private val executor: Executor,
  private val console: Console,
) {

  fun handle(flag: Boolean) {
    if (!flag) return

    console.echo("Opening project $${props.files().projectHome} with program ${props.activeProfile().ide}")
    executor.exec(arrayOf(props.activeProfile().ide, props.files().projectHome))
  }
}
