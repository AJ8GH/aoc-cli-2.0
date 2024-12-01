package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.properties.PropertiesManager

private const val OPEN = "open"

class WebHandler(
  private val props: PropertiesManager,
  private val executor: Executor,
  private val console: Console,
) {

  fun handle(flag: Boolean) {
    if (!flag) return

    console.echo("Opening AoC web page...")
    val url = "${props.aocProperties().url}/20${props.year()}/day/${props.day()}"
    executor.exec(arrayOf(OPEN, url))
  }
}
