package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.cache.answer.AnswerCache
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.handler.create.CreateHandler
import io.github.aj8gh.aoc.command.handler.create.ReadmeCreator
import io.github.aj8gh.aoc.http.AnswerClient
import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.properties.PropertiesManager

const val TOO_HIGH = "That's not the right answer; your answer is too high."
const val TOO_LOW = "That's not the right answer; your answer is too low."
const val WRONG_LEVEL = "You don't seem to be solving the right level.  Did you already complete it?"
const val INCORRECT = "That's not the right answer."
const val NOT_CACHED = "Answer not found in cache."
const val CORRECT = "Congratulations, that's the correct answer!"
const val CORRECT_ALT = "That's the right answer!"

class AnswerHandler(
  private val cache: AnswerCache,
  private val client: AnswerClient,
  private val createHandler: CreateHandler,
  private val nextHandler: NextHandler,
  private val console: Console,
  private val props: PropertiesManager,
  private val readmeCreator: ReadmeCreator,
) {

  fun handle(answer: String?, verbose: Boolean) =
    answer?.let { checkCache(it, verbose) }

  private fun checkCache(answer: String, verbose: Boolean) =
    handle(cache.checkAnswer(answer), answer, verbose)

  private fun submitAnswer(answer: String, verbose: Boolean) {
    val response = client.postAnswer(answer)
    handle(response, answer, verbose)
  }

  private fun handle(response: String, answer: String, verbose: Boolean) =
    with(response) {
      when {
        contains(CORRECT) || contains(CORRECT_ALT) -> handleCorrect(answer, verbose)
        contains(INCORRECT) -> console.echo(INCORRECT)
        contains(WRONG_LEVEL) -> console.echo(WRONG_LEVEL)
        contains(TOO_HIGH) -> console.echo(TOO_HIGH)
        contains(TOO_LOW) -> console.echo(TOO_LOW)
        contains(NOT_CACHED) -> submitAnswer(answer, verbose)
        else -> console.echo(response)
      }
    }

  private fun handleCorrect(answer: String, verbose: Boolean) {
    console.echo(CORRECT)
    cache.cacheAnswer(answer)
    readmeCreator.create()
    nextHandler.handle(verbose)
    if (props.level() == L1) createHandler.handle()
  }
}
