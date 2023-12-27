package com.github.aj8gh.aoc.command

import com.github.aj8gh.aoc.DAY
import com.github.aj8gh.aoc.LEVEL
import com.github.aj8gh.aoc.SET
import com.github.aj8gh.aoc.YEAR
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class Set: Subcommand(SET, "Set year, level and/or day") {
  val year by option(ArgType.Int, YEAR, "y", "Year value to set")
  val day by option(ArgType.Int, DAY, "d", "Day value to set")
  val level by option(ArgType.Int, LEVEL, "l", "Level value to set")

  override fun execute() {
    println("year: $year, day: $day, level: $level")
  }
}
