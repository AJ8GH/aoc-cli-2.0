package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.properties.PropertiesManager

class ProfileHandler(
  private val props: PropertiesManager,
  private val console: Console,
) {

  fun handle(profile: String?) {
    profile?.let {
      props.setActiveProfile(it)
      console.echo("Active profile is now \"$it\"")
    }
  }
}
