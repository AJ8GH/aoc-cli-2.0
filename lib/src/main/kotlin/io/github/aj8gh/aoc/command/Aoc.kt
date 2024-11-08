package io.github.aj8gh.aoc.command

import com.github.ajalt.clikt.completion.CompletionCandidates.Fixed
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import io.github.aj8gh.aoc.command.Command.*
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.CreateHandler
import io.github.aj8gh.aoc.command.handler.runtime.FileHandler
import io.github.aj8gh.aoc.command.handler.runtime.OpenHandler
import io.github.aj8gh.aoc.util.latestYear

private const val APP_NAME = "aoc"

val YEAR_RANGE = Y15..latestYear()
val DAY_RANGE = D1..D25
val LEVEL_RANGE = L1..L2

class Aoc(
  private val setHandler: SetHandler,
  private val statHandler: StatHandler,
  private val tokenHandler: TokenHandler,
  private val profileHandler: ProfileHandler,
  private val nextHandler: NextHandler,
  private val echoHandler: EchoHandler,
  private val createHandler: CreateHandler,
  private val fileHandler: FileHandler,
  private val openHandler: OpenHandler,
  private val answerHandler: AnswerHandler,
) : CliktCommand(
  name = APP_NAME,
  invokeWithoutSubcommand = true,
) {

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
    profileHandler.profile(profile)
    setHandler.set(year = year, day = day, level = level, verbose = verbose)
    nextHandler.next(flag = next, verbose = verbose)
    tokenHandler.token(token)
    fileHandler.files(files)
    answerHandler.answer(answer = answer, verbose = verbose)
    createHandler.create(create)
    echoHandler.echoCurrent(echo = echo, verbose = verbose)
    statHandler.stats(stats)
    openHandler.open(open)
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
