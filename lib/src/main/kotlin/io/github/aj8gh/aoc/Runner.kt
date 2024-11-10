package io.github.aj8gh.aoc

import com.github.ajalt.clikt.core.main
import io.github.aj8gh.aoc.command.Aoc
import io.github.aj8gh.aoc.context.ApplicationContext
import io.github.aj8gh.aoc.context.ContextManager

class Runner(
  private val contextManager: ContextManager = ContextManager(),
  private val context: ApplicationContext = contextManager.context(),
  private val aoc: Aoc = Aoc(context)
) {

  fun run(args: Array<String>) = run(args.toList())

  fun run(args: List<String>) {
    try {
      aoc.main(args)
    } catch (e: Exception) {
      context.io.log.error(e)
    }
  }
}
