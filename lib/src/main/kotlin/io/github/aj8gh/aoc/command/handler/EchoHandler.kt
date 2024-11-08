package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.io.activeProfileFile
import io.github.aj8gh.aoc.io.aocPropertiesFile
import io.github.aj8gh.aoc.io.read
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.aocProperties

const val PROPERTIES_HEADER = "AoC Properties"
const val PROFILE_HEADER = "Active Profile"

class EchoHandler {

  fun echoCurrent(verbose: Boolean) = echoCurrent(true, verbose)

  fun echoCurrent(echo: Boolean, verbose: Boolean) {
    if (!echo) return

    if (verbose) {
      val props = read(aocPropertiesFile())
      val profile = read(activeProfileFile())
      println("$PROPERTIES_HEADER\n${props}\n$PROFILE_HEADER\n${profile}")
    } else {
      val env = activeProfile().current
      println(
        "You are on year ${env.year} day ${env.day} level ${env.level}. "
            + "Active profile: ${aocProperties().active}"
      )
    }
  }
}
