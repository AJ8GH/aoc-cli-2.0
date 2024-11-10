package io.github.aj8gh.aoc.test.steps

import io.github.aj8gh.aoc.test.context.CONTEXT
import io.github.aj8gh.aoc.test.context.RUNNER

class When {

  fun createInputIsCalled() = CONTEXT.creator.input.create()
  fun createReadmeIsCalled() = CONTEXT.creator.readme.create()
  fun createExampleIsCalled() = CONTEXT.creator.example.create()
  fun createCodeIsCalled() = CONTEXT.creator.code.create()
  fun theAnswerIsCached(answer: String) = CONTEXT.cache.answer.cacheAnswer(answer)
  fun theAppIsRunWithArg(arg: String) = theAppIsRunWithArgs(listOf(arg))
  fun theAppIsRunWithArgs(args: List<String>) = RUNNER.run(args)
}
