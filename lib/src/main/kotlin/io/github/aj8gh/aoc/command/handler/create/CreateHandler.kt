package io.github.aj8gh.aoc.command.handler.create

class CreateHandler(
  private val inputCreator: InputCreator,
  private val readmeCreator: ReadmeCreator,
  private val exampleCreator: ExampleCreator,
  private val codeCreator: CodeCreator
) {

  fun handle(create: Boolean) = if (create) handle() else Unit

  fun handle() {
    inputCreator.create()
    readmeCreator.create()
    exampleCreator.create()
    codeCreator.create()
  }
}
