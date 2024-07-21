package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.properties.activeProperties

fun echoCurrent() = echoCurrent(true)

fun echoCurrent(echo: Boolean) {
  if (echo) {
    val env = activeProperties().current
    println("You are on year ${env.year} day ${env.day} level ${env.level}")
  }
}
