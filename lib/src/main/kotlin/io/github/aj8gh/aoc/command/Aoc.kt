package io.github.aj8gh.aoc.command

import com.github.ajalt.clikt.completion.CompletionCandidates.Fixed
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.command.Command.*
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.create
import io.github.aj8gh.aoc.command.handler.runtime.home
import io.github.aj8gh.aoc.command.handler.runtime.open
import io.github.aj8gh.aoc.util.latestYear

val YEAR_RANGE = Y15..latestYear()
val DAY_RANGE = D1..D25
val LEVEL_RANGE = L1..L2

class Aoc : CliktCommand(name = "aoc", invokeWithoutSubcommand = true) {

  private val year by toOption(YEAR.names, YEAR.help, YEAR.completion)
    .choice(*toStringArray(YEAR_RANGE))
    .int()

  private val day by toOption(DAY.names, DAY.help, DAY.completion)
    .choice(*toStringArray(DAY_RANGE))
    .int()

  private val level by toOption(LEVEL.names, LEVEL.help, LEVEL.completion)
    .choice(*toStringArray(LEVEL_RANGE))
    .int()

  private val create by toOption(CREATE.names, CREATE.help).flag()
  private val next by toOption(NEXT.names, NEXT.help).flag()
  private val echo by toOption(ECHO.names, ECHO.help).flag()
  private val answer by toOption(ANSWER.names, ANSWER.help)
  private val profile by toOption(PROFILE.names, PROFILE.help)
  private val open by toOption(OPEN.names, OPEN.help).flag()
  private val files by toOption(FILES.names, FILES.help).flag()
  private val token by toOption(TOKEN.names, TOKEN.help)
  private val verbose by toOption(VERBOSE.names, VERBOSE.help).flag()
  private val stats by toOption(STATS.names, STATS.help).flag()

  override fun run() {
    profile(profile)
    set(year = year, day = day, level = level, verbose = verbose)
    next(flag = next, verbose = verbose)
    token(token)
    if (files) home(Runtime.getRuntime())
    answer(answer = answer, verbose = verbose)
    create(create)
    echoCurrent(echo = echo, verbose = verbose)
    stats(stats, Terminal(width = 100))
    if (open) open(Runtime.getRuntime())
  }

  private fun toOption(
    names: Array<String>,
    help: String,
    completion: Fixed? = null
  ) = option(names = names, help = help, completionCandidates = completion)

  private fun toStringArray(ints: IntRange) = ints.map { it.toString() }.toTypedArray()
}
