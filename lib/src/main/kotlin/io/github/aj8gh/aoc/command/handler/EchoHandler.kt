package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager

const val PROPERTIES_HEADER = "AoC Properties"
const val PROFILE_HEADER = "Active Profile"

class EchoHandler(
  private val props: PropertiesManager,
  private val propsFiles: PropertyFileManager,
  private val reader: Reader,
  private val console: Console,
) {

  fun handle(verbose: Boolean) = handle(true, verbose)

  fun handle(flag: Boolean, verbose: Boolean) {
    if (!flag) return
    val aocProps = reader.read(propsFiles.aocPropertiesFile())
    val profile = reader.read(propsFiles.activeProfileFile())

    if (verbose) {
      console.echo("$PROPERTIES_HEADER\n${aocProps}\n$PROFILE_HEADER\n${profile}")
    } else {
      val env = props.activeProfile().current
      val active = props.aocProperties().active
      console.echo("You are on year ${env.year} day ${env.day} level ${env.level}. Active profile: $active")
    }
  }
}
