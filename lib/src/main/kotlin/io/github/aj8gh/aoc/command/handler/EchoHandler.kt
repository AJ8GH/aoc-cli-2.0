package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager

const val PROPERTIES_HEADER = "AoC Properties"
const val PROFILE_HEADER = "Active Profile"

class EchoHandler(
  private val props: PropertiesManager,
  private val propsFiles: PropertyFileManager,
  private val reader: Reader,
) {

  fun handle(verbose: Boolean) = handle(true, verbose)

  fun handle(echo: Boolean, verbose: Boolean) {
    if (!echo) return
    val aocProps = reader.read(propsFiles.aocPropertiesFile())
    val profile = reader.read(propsFiles.activeProfileFile())

    if (verbose) {
      println("$PROPERTIES_HEADER\n${aocProps}\n$PROFILE_HEADER\n${profile}")
    } else {
      val env = props.activeProfile().current
      val active = props.aocProperties().active
      println("You are on year ${env.year} day ${env.day} level ${env.level}. Active profile: $active")
    }
  }
}
