package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.command.handler.answer
import com.github.aj8gh.aoc.command.handler.create.code
import com.github.aj8gh.aoc.command.handler.create.example
import com.github.aj8gh.aoc.command.handler.create.input
import com.github.aj8gh.aoc.command.handler.create.readme
import com.github.aj8gh.aoc.command.handler.echoCurrent
import com.github.aj8gh.aoc.command.handler.next
import com.github.aj8gh.aoc.command.handler.set

fun whenNextIsCalledFor(next: Boolean) = next(next)

fun whenEchoCurrentIsCalledFor(echo: Boolean) = echoCurrent(echo)

fun whenAnswerIsCalledWith(answer: String) = answer(answer)

fun whenSetIsCalledFor(year: Int?, day: Int?, level: Int?) =
  set(year = year, day = day, level = level)

fun whenCreateInputIsCalled() = input()

fun whenCreateReadmeIsCalled() = readme()

fun whenCreateCodeIsCalled() = code()

fun whenCreateExampleIsCalled() = example()
