package io.github.aj8gh.aoc

import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.code
import io.github.aj8gh.aoc.command.handler.create.example
import io.github.aj8gh.aoc.command.handler.create.input
import io.github.aj8gh.aoc.command.handler.create.readme
import io.github.aj8gh.aoc.command.handler.runtime.home
import io.github.aj8gh.aoc.command.handler.runtime.open

fun whenNextIsCalledFor(next: Boolean) = next(next)

fun whenEchoCurrentIsCalledFor(echo: Boolean) = echoCurrent(echo)

fun whenAnswerIsCalledWith(answer: String) = answer(answer)

fun whenSetIsCalledFor(year: Int?, day: Int?, level: Int?) =
  set(year = year, day = day, level = level)

fun whenCreateInputIsCalled() = input()

fun whenCreateReadmeIsCalled() = readme()

fun whenCreateCodeIsCalled() = code()

fun whenCreateExampleIsCalled() = example()

fun whenProfileIsCalledWith(profile: String) = profile(profile)

fun whenOpenIsCalled(runtime: Runtime) = open(runtime)

fun whenConfigFileIsCalled(runtime: Runtime) = home(runtime)

fun whenTokenIsCalled(token: String) = token(token)
