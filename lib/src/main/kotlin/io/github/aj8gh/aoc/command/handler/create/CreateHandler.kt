package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.io.Logger

class CreateHandler(
  private val inputCreator: InputCreator,
  private val readmeCreator: ReadmeCreator,
  private val exampleCreator: ExampleCreator,
  private val codeCreator: CodeCreator,
  private val log: Logger,
) {

  fun handle(create: Boolean) = if (create) handle() else Unit

  fun handle() {
    log.info("Creating input, README, example, and code files...")
    inputCreator.create()
    readmeCreator.create()
    exampleCreator.create()
    codeCreator.create()
  }
}
