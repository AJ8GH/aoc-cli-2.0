package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.properties.PropertiesManager

class FilesHandler(
  private val props: PropertiesManager,
  private val executor: Executor,
  private val aocHome: String,
  private val console: Console,
) {

  fun handle(flag: Boolean) {
    if (!flag) return

    console.echo("Opening AoC Home $aocHome with program ${props.activeProfile().ide}")
    executor.exec(arrayOf(props.activeProfile().ide, aocHome))
  }
}
