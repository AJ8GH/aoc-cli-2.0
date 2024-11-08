package io.github.aj8gh.aoc

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.command.Aoc
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.*
import io.github.aj8gh.aoc.command.handler.runtime.Executor
import io.github.aj8gh.aoc.command.handler.runtime.FileHandler
import io.github.aj8gh.aoc.command.handler.runtime.OpenHandler

fun main(args: Array<String>) = Aoc(
  SetHandler(EchoHandler()),
  StatHandler(Terminal(width = 100)),
  TokenHandler(),
  ProfileHandler(),
  NextHandler(EchoHandler()),
  EchoHandler(),
  CreateHandler(InputCreator(), ReadmeCreator(), ExampleCreator(), CodeCreator()),
  FileHandler(Executor(Runtime.getRuntime())),
  OpenHandler(Executor(Runtime.getRuntime())),
  AnswerHandler(),
).main(args)
