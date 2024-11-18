package io.github.aj8gh.aoc.command.handler.runtime

class Executor(
  private val runtime: Runtime,
) {

  fun exec(command: Array<String>) = runtime.exec(command)
}
