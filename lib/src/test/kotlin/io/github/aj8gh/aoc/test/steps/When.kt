package io.github.aj8gh.aoc.test.steps

import io.github.aj8gh.aoc.command.OPEN_SHORT
import io.github.aj8gh.aoc.test.context.CONTEXT
import io.github.aj8gh.aoc.test.context.RUNNER

class When {

  fun createInputIsCalled() = CONTEXT.creator.input.create()
  fun createReadmeIsCalled() = CONTEXT.creator.readme.create()
  fun createExampleIsCalled() = CONTEXT.creator.example.create()
  fun createCodeIsCalled() = CONTEXT.creator.code.create()
  fun answerIsCalledWith(answer: String) = CONTEXT.handler.answer.handle(answer, false)
  fun theAnswerIsCached(answer: String) = CONTEXT.cache.answer.cacheAnswer(answer)
  fun profileIsCalledWith(profile: String) = CONTEXT.handler.profile.handle(profile)
  fun openIsCalled() = RUNNER.run(arrayOf(OPEN_SHORT))
  fun configFileIsCalled() = CONTEXT.handler.files.handle(true)
  fun tokenIsCalled(token: String) = CONTEXT.handler.token.handle(token)
  fun statsIsCalled(flag: Boolean) = CONTEXT.handler.stats.handle(flag)
  fun nextIsCalledFor(flag: Boolean) = CONTEXT.handler.next.handle(flag = flag, verbose = false)
  fun echoIsCalledFor(echo: Boolean, verbose: Boolean) = CONTEXT.handler.echo.handle(echo, verbose)
  fun setIsCalledFor(year: Int?, day: Int?, level: Int?) = CONTEXT.handler.set
    .handle(year = year, day = day, level = level)
}
