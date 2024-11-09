package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.properties.setActiveProfile

class ProfileHandler {
  fun handle(profile: String?) {
    profile?.let {
      setActiveProfile(it)
      println("Active profile is now \"$it\"")
    }
  }
}
