package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.properties.getActiveProperties

fun echoCurrent() = echoCurrent(true)

fun echoCurrent(echo: Boolean) {
  if (echo) {
    val env = getActiveProperties().current
    println("You are on year ${env.year} day ${env.day} level ${env.level}")
  }
}
