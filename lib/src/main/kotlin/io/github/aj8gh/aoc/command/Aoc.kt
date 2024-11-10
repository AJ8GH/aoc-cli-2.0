package io.github.aj8gh.aoc.command

import com.github.ajalt.clikt.completion.CompletionCandidates.Fixed
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import io.github.aj8gh.aoc.command.Command.*
import io.github.aj8gh.aoc.context.ApplicationContext
import io.github.aj8gh.aoc.util.latestYear

private const val APP_NAME = "aoc"

val YEAR_RANGE = Y15..latestYear()
val DAY_RANGE = D1..D25
val LEVEL_RANGE = L1..L2

class Aoc(private val context: ApplicationContext) : CliktCommand(name = APP_NAME) {

  private val year by toIntOption(YEAR.names, YEAR.help, YEAR.completion, YEAR_RANGE)
  private val day by toIntOption(DAY.names, DAY.help, DAY.completion, DAY_RANGE)
  private val level by toIntOption(LEVEL.names, LEVEL.help, LEVEL.completion, LEVEL_RANGE)
  private val token by toOption(TOKEN.names, TOKEN.help)
  private val answer by toOption(ANSWER.names, ANSWER.help)
  private val profile by toOption(PROFILE.names, PROFILE.help)
  private val next by toOption(NEXT.names, NEXT.help).flag()
  private val echo by toOption(ECHO.names, ECHO.help).flag()
  private val open by toOption(OPEN.names, OPEN.help).flag()
  private val files by toOption(FILES.names, FILES.help).flag()
  private val stats by toOption(STATS.names, STATS.help).flag()
  private val create by toOption(CREATE.names, CREATE.help).flag()
  private val verbose by toOption(VERBOSE.names, VERBOSE.help).flag()

  override fun run() {
    val h = context.handler
    h.profile.handle(profile)
    h.set.handle(year = year, day = day, level = level, verbose = verbose)
    h.next.handle(flag = next, verbose = verbose)
    h.token.handle(token)
    h.files.handle(files)
    h.answer.handle(answer = answer, verbose = verbose)
    h.create.handle(create)
    h.echo.handle(echo = echo, verbose = verbose)
    h.stats.handle(stats)
    h.open.handle(open)
  }

  private fun toIntOption(
    names: Array<String>,
    help: String,
    completion: Fixed?,
    choices: IntRange,
  ) = toOption(names = names, help = help, completion = completion)
    .choice(*toStringArray(choices))
    .int()

  private fun toOption(
    names: Array<String>,
    help: String,
    completion: Fixed? = null,
  ) = option(names = names, help = help, completionCandidates = completion)

  private fun toStringArray(ints: IntRange) = ints
    .map { it.toString() }
    .toTypedArray()
}
