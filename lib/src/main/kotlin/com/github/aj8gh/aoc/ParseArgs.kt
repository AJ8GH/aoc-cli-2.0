package com.github.aj8gh.aoc

import kotlinx.cli.ArgParser

fun parseArgs(args: Array<String>) {
  val parser = ArgParser(NEXT)

  val next by next(parser)
  val answer by answer(parser)
  set(parser)
  val year by year(parser)
  val day by day(parser)
  val level by level(parser)
  val echo by echo(parser)
  val create by create(parser)
  val open by open(parser)

  parser.parse(args)

  val map = mapOf<String, Any?>(
    NEXT to next,
    ANSWER to answer,
    // SET to set,
    YEAR to year,
    DAY to day,
    LEVEL to level,
    ECHO to echo,
    CREATE to create,
    OPEN to open,
  )

  processArgs(map)
}
