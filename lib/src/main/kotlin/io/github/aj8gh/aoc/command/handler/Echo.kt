package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.aocProperties

fun echoCurrent() = echoCurrent(true)

fun echoCurrent(echo: Boolean) {
  if (echo) {
    val env = activeProfile().current
    println("You are on year ${env.year} day ${env.day} level ${env.level}. Active profile: ${aocProperties().active}")
  }
}
