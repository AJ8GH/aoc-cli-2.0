package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.properties.PropertiesManager

class ProfileHandler(private val props: PropertiesManager) {
  fun handle(profile: String?) {
    profile?.let {
      props.setActiveProfile(it)
      println("Active profile is now \"$it\"")
    }
  }
}
