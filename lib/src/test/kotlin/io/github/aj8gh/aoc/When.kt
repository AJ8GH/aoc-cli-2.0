package io.github.aj8gh.aoc

import io.github.aj8gh.aoc.command.handler.answer
import io.github.aj8gh.aoc.command.handler.create.code
import io.github.aj8gh.aoc.command.handler.create.example.example
import io.github.aj8gh.aoc.command.handler.create.input
import io.github.aj8gh.aoc.command.handler.create.readme
import io.github.aj8gh.aoc.command.handler.echoCurrent
import io.github.aj8gh.aoc.command.handler.next
import io.github.aj8gh.aoc.command.handler.set

fun whenNextIsCalledFor(next: Boolean) = next(next)

fun whenEchoCurrentIsCalledFor(echo: Boolean) = echoCurrent(echo)

fun whenAnswerIsCalledWith(answer: String) = answer(answer)

fun whenSetIsCalledFor(year: Int?, day: Int?, level: Int?) =
  set(year = year, day = day, level = level)

fun whenCreateInputIsCalled() = input()

fun whenCreateReadmeIsCalled() = readme()

fun whenCreateCodeIsCalled() = code()

fun whenCreateExampleIsCalled() = example()
