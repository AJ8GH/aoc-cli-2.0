package com.github.aj8gh.aoc.command

import com.github.aj8gh.aoc.handler.set
import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class Aoc : CliktCommand(
  name = "aoc",
  invokeWithoutSubcommand = true,
) {

  private val year by option(
    names = arrayOf("-y", "--year"),
    help = "New year value to set, must be between 15 and current year",
    completionCandidates = CompletionCandidates.Fixed((15..23).map(Int::toString).toSet())
  ).int()

  private val day by option(
    names = arrayOf("-d", "--day"),
    help = "New day value to set, must be between 1 and 25",
    completionCandidates = CompletionCandidates.Fixed((1..2).map(Int::toString).toSet()),
  ).int()

  private val level by option(
    names = arrayOf("-l", "--level"),
    help = "New level value to set, must be 1 or 2",
    completionCandidates = CompletionCandidates.Fixed(setOf("1", "2"))
  ).int()

  private val create by option(
    names = arrayOf("-c", "--create"),
    help = "Create resources for current level",
  ).flag()

  private val next by option(
    names = arrayOf("-n", "--next"),
    help = "Advance to the next level",
  ).flag()

  private val echo by option(
    names = arrayOf("-e", "--echo"),
    help = "Echo the current year, day and level",
  ).flag()

  private val answer by option(
    names = arrayOf("-a", "--answer"),
    help = "Submit answer for current level",
  )

  override fun run() {
    echo(
      """
    * year $year
    * day $day
    * level $level
    * create $create
    * next $next
    * echo $echo
    * answer $answer
    """
    )

    set(
      year = year,
      day = day,
      level = level
    )
  }
}
