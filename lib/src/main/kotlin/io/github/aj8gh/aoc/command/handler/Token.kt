package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.io.aocPropertiesFile
import io.github.aj8gh.aoc.io.write
import io.github.aj8gh.aoc.properties.aocProperties
import io.github.aj8gh.aoc.properties.forceLoadAocProperties

fun token(token: String?) {
  token?.let {
    val props = aocProperties().copy(session = it)
    write(aocPropertiesFile(), props)
    forceLoadAocProperties()
  }
}
