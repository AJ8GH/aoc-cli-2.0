package io.github.aj8gh.aoc

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.*
import io.github.aj8gh.aoc.command.handler.runtime.*

fun whenNextIsCalledFor(flag: Boolean) =
  NextHandler(EchoHandler()).next(flag = flag, verbose = false)

fun whenEchoCurrentIsCalledFor(echo: Boolean, verbose: Boolean) =
  EchoHandler().echoCurrent(echo, verbose)

fun whenAnswerIsCalledWith(answer: String) = AnswerHandler().answer(answer, false)

fun whenSetIsCalledFor(year: Int?, day: Int?, level: Int?) =
  SetHandler(EchoHandler()).set(year = year, day = day, level = level, false)

fun whenCreateInputIsCalled() = InputCreator().create()

fun whenCreateReadmeIsCalled() = ReadmeCreator().create()

fun whenCreateCodeIsCalled() = CodeCreator().create()

fun whenCreateExampleIsCalled() = ExampleCreator().create()

fun whenProfileIsCalledWith(profile: String) = ProfileHandler().profile(profile)

fun whenOpenIsCalled(runtime: Runtime) = OpenHandler(Executor(runtime)).open(true)

fun whenConfigFileIsCalled(runtime: Runtime) = FileHandler(Executor(runtime)).files(true)

fun whenTokenIsCalled(token: String) = TokenHandler().token(token)

fun whenStatsIsCalled(flag: Boolean, terminal: Terminal) =
  StatHandler(terminal).stats(flag)
