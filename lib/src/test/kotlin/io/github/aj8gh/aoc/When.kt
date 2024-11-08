package io.github.aj8gh.aoc

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.cache.InputCache
import io.github.aj8gh.aoc.cache.ReadmeCache
import io.github.aj8gh.aoc.cache.answer.AnswerCacheManager
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.*
import io.github.aj8gh.aoc.command.handler.runtime.Executor
import io.github.aj8gh.aoc.command.handler.runtime.FileHandler
import io.github.aj8gh.aoc.command.handler.runtime.OpenHandler
import io.github.aj8gh.aoc.http.AnswerClient
import io.github.aj8gh.aoc.http.AocClient
import io.github.aj8gh.aoc.http.InputClient
import io.github.aj8gh.aoc.http.ReadmeClient

fun whenNextIsCalledFor(flag: Boolean) =
  NextHandler(EchoHandler()).next(flag = flag, verbose = false)

fun whenEchoCurrentIsCalledFor(echo: Boolean, verbose: Boolean) =
  EchoHandler().echoCurrent(echo, verbose)

fun whenAnswerIsCalledWith(answer: String) = AnswerHandler(
  AnswerCacheManager(),
  AnswerClient(AocClient()),
  CreateHandler(
    InputCreator(InputCache(), InputClient(AocClient())),
    ReadmeCreator(ReadmeCache(), ReadmeClient(AocClient()), AnswerCacheManager()),
    ExampleCreator(),
    CodeCreator(AnswerCacheManager())
  )
).answer(answer, false)

fun whenSetIsCalledFor(year: Int?, day: Int?, level: Int?) =
  SetHandler(EchoHandler()).set(year = year, day = day, level = level, false)

fun whenCreateInputIsCalled() = InputCreator(InputCache(), InputClient(AocClient())).create()

fun whenCreateReadmeIsCalled() = ReadmeCreator(
  ReadmeCache(),
  ReadmeClient(AocClient()),
  AnswerCacheManager()
).create()

fun whenCreateCodeIsCalled() = CodeCreator(AnswerCacheManager()).create()

fun whenCreateExampleIsCalled() = ExampleCreator().create()

fun whenProfileIsCalledWith(profile: String) = ProfileHandler().profile(profile)

fun whenOpenIsCalled(runtime: Runtime) = OpenHandler(Executor(runtime)).open(true)

fun whenConfigFileIsCalled(runtime: Runtime) = FileHandler(Executor(runtime)).files(true)

fun whenTokenIsCalled(token: String) = TokenHandler().token(token)

fun whenStatsIsCalled(flag: Boolean, terminal: Terminal) =
  StatHandler(AnswerCacheManager(), terminal).stats(flag)
