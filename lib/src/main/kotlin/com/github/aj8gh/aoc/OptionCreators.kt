package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.command.Set
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli

fun next(parser: ArgParser) = parser.option(
  ArgType.Boolean,
  shortName = "n",
  description = "Advance to next level"
)

fun answer(parser: ArgParser) = parser.option(
  ArgType.String,
  shortName = "a",
  description = "Submit answer"
)

@OptIn(ExperimentalCli::class)
fun set(parser: ArgParser) = parser.subcommands(Set())

fun year(parser: ArgParser) = parser.option(
  ArgType.Int,
  shortName = "y",
  description = "Year value to set"
)

fun day(parser: ArgParser) = parser.option(
  ArgType.Int,
  shortName = "d",
  description = "Day value to set"
)

fun level(parser: ArgParser) = parser.option(
  ArgType.Int,
  shortName = "l",
  description = "Level value to set"
)

fun echo(parser: ArgParser) = parser.option(
  ArgType.Boolean,
  shortName = "e",
  description = "Echo current year, day and level"
)

fun create(parser: ArgParser) = parser.option(
  ArgType.Boolean,
  shortName = "c",
  description = "Create readme, inputs and skeleton code for current level"
)

fun open(parser: ArgParser) = parser.option(
  ArgType.String,
  shortName = "o",
  description = "Open AoC project with given IDE"
)
