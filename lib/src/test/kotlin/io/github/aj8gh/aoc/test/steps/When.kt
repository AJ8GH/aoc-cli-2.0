package io.github.aj8gh.aoc.test.steps

import io.github.aj8gh.aoc.test.context.context
import io.github.aj8gh.aoc.test.context.runner

class When {

  fun createInputIsCalled() = context.creator.input.create()
  fun createReadmeIsCalled() = context.creator.readme.create()
  fun createExampleIsCalled() = context.creator.example.create()
  fun createCodeIsCalled() = context.creator.code.create()
  fun theAnswerIsCached(answer: String) = context.cache.answer.cacheAnswer(answer)
  fun theAppIsRunWithArg(arg: String) = theAppIsRunWithArgs(listOf(arg))
  fun theAppIsRunWithArgs(args: List<String>) = runner.run(args)
}
