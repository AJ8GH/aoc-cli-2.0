package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.context.DateManager
import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.properties.PropertiesManager

class CreateHandler(
  private val inputCreator: InputCreator,
  private val readmeCreator: ReadmeCreator,
  private val exampleCreator: ExampleCreator,
  private val codeCreator: CodeCreator,
  private val log: Logger,
  private val props: PropertiesManager,
  private val dateManager: DateManager,
  private val console: Console,
) {

  fun handle(create: Boolean) = if (create) handle() else Unit

  fun handle() {
    log.info("Creating input, README, example, and code files...")
    if (dateManager.isPuzzleAvailable(props.year(), props.day())) {
      inputCreator.create()
      readmeCreator.create()
      exampleCreator.create()
    } else {
      console.echo("Next puzzle not yet available! Skipping file creation.")
      log.info("Next puzzle not yet available, skipping, input, README and example file creation.")
    }
    codeCreator.create()
  }
}
