package io.github.aj8gh.aoc

import com.github.ajalt.clikt.core.main
import io.github.aj8gh.aoc.command.Aoc
import io.github.aj8gh.aoc.context.ApplicationContext
import io.github.aj8gh.aoc.context.ContextBuilder
import io.github.aj8gh.aoc.context.ContextManager

private const val MESSAGE = "Error executing command: \"%s\""

class Runner(
  private val contextManager: ContextManager = ContextManager(ContextBuilder()),
  private val context: ApplicationContext = contextManager.context(),
  private val aoc: Aoc = Aoc(context)
) {

  fun run(args: Array<String>) = run(args.toList())

  fun run(args: List<String>) {
    try {
      aoc.main(args)
    } catch (e: Exception) {
      context.io.console.echo(MESSAGE.format(e.message))
      context.io.log.of(this::class.simpleName).error(e)
    }
  }
}
