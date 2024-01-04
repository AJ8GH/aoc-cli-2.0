package com.github.aj8gh.aoc.command

import com.github.aj8gh.aoc.command.Command.*
import com.github.aj8gh.aoc.command.handler.*
import com.github.aj8gh.aoc.command.handler.create.create
import com.github.ajalt.clikt.completion.CompletionCandidates.Fixed
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class Aoc : CliktCommand(name = "aoc", invokeWithoutSubcommand = true) {

  private val year by toOption(YEAR.names, YEAR.help, YEAR.completion).int()
  private val day by toOption(DAY.names, DAY.help, DAY.completion).int()
  private val level by toOption(LEVEL.names, LEVEL.help, LEVEL.completion).int()
  private val create by toOption(CREATE.names, CREATE.help).flag()
  private val next by toOption(NEXT.names, NEXT.help).flag()
  private val echo by toOption(ECHO.names, ECHO.help).flag()
  private val answer by toOption(ANSWER.names, ANSWER.help)

  override fun run() {
    set(year = year, day = day, level = level)
    next(next)
    answer(answer)
    create(create)
    echoCurrent(echo)
  }

  private fun toOption(
      names: Array<String>,
      help: String,
      completion: Fixed? = null
  ) = option(names = names, help = help, completionCandidates = completion)
}
