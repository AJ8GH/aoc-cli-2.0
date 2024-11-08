package io.github.aj8gh.aoc.command.handler.create

class CreateHandler(
  private val inputCreator: InputCreator,
  private val readmeCreator: ReadmeCreator,
  private val exampleCreator: ExampleCreator,
  private val codeCreator: CodeCreator
) {

  fun create(create: Boolean) = if (create) create() else Unit

  fun create() {
    inputCreator.create()
    readmeCreator.create()
    exampleCreator.create()
    codeCreator.create()
  }
}
