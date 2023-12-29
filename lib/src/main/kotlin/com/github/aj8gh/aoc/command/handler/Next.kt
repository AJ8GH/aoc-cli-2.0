package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.properties.getActiveProperties

fun next() {
  val properties = getActiveProperties()

  val year = properties.current.year
  val day = properties.current.day
  val level = properties.current.level

}
