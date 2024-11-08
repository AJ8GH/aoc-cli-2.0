package io.github.aj8gh.aoc

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.cache.InputCache
import io.github.aj8gh.aoc.cache.ReadmeCache
import io.github.aj8gh.aoc.cache.answer.AnswerCacheManager
import io.github.aj8gh.aoc.command.Aoc
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.*
import io.github.aj8gh.aoc.command.handler.runtime.Executor
import io.github.aj8gh.aoc.command.handler.runtime.FileHandler
import io.github.aj8gh.aoc.command.handler.runtime.OpenHandler
import io.github.aj8gh.aoc.http.AnswerClient
import io.github.aj8gh.aoc.http.AocClient
import io.github.aj8gh.aoc.http.InputClient
import io.github.aj8gh.aoc.http.ReadmeClient

fun main(args: Array<String>) {
  val createHandler = CreateHandler(
    InputCreator(InputCache(), InputClient(AocClient())),
    ReadmeCreator(ReadmeCache(), ReadmeClient(AocClient()), AnswerCacheManager()),
    ExampleCreator(),
    CodeCreator(AnswerCacheManager()),
  )
  Aoc(
    SetHandler(EchoHandler()),
    StatHandler(AnswerCacheManager(), Terminal(width = 100)),
    TokenHandler(),
    ProfileHandler(),
    NextHandler(EchoHandler()),
    EchoHandler(),
    createHandler,
    FileHandler(Executor(Runtime.getRuntime())),
    OpenHandler(Executor(Runtime.getRuntime())),
    AnswerHandler(AnswerCacheManager(), AnswerClient(AocClient()), createHandler),
  ).main(args)
}
