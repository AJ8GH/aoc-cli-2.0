package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.cache.answer.cacheAnswer
import io.github.aj8gh.aoc.cache.answer.checkAnswer
import io.github.aj8gh.aoc.command.handler.create.create
import io.github.aj8gh.aoc.http.postAnswer

const val TOO_HIGH = "That's not the right answer; your answer is too high."
const val TOO_LOW = "That's not the right answer; your answer is too low."
const val WRONG_LEVEL = "You don't seem to be solving the right level.  Did you already complete it?"
const val INCORRECT = "That's not the right answer."
const val NOT_CACHED = "Answer not found in cache."
const val CORRECT = "Congratulations, that's the correct answer!"

fun answer(answer: String?, verbose: Boolean) =
  answer?.let { checkCache(it, verbose) }

private fun checkCache(answer: String, verbose: Boolean) =
  handle(checkAnswer(answer), answer, verbose)

private fun submitAnswer(answer: String, verbose: Boolean) {
  val response = postAnswer(answer)
  handle(response, answer, verbose)
}

private fun handle(response: String, answer: String, verbose: Boolean) =
  with(response) {
    when {
      contains(CORRECT) -> handleCorrect(answer, verbose)
      contains(INCORRECT) -> println(INCORRECT)
      contains(WRONG_LEVEL) -> println(WRONG_LEVEL)
      contains(TOO_HIGH) -> println(TOO_HIGH)
      contains(TOO_LOW) -> println(TOO_LOW)
      contains(NOT_CACHED) -> submitAnswer(answer, verbose)
      else -> println(response)
    }
  }

private fun handleCorrect(answer: String, verbose: Boolean) {
  println(CORRECT)
  cacheAnswer(answer)
  next(verbose)
  create()
}
