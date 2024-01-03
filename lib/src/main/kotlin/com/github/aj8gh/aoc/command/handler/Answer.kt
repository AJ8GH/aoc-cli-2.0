package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.cache.cache
import com.github.aj8gh.aoc.cache.checkAnswer
import com.github.aj8gh.aoc.http.submitAnswer
import com.github.aj8gh.aoc.properties.getAocProperties
import com.github.aj8gh.aoc.properties.getCurrent

private const val TOO_HIGH = "That's not the right answer; your answer is too high."
private const val TOO_LOW = "That's not the right answer; your answer is too low."
private const val WRONG_LEVEL = "You don't seem to be solving the right level.  Did you already complete it?"
private const val CORRECT = "Congratulations, that's the correct answer!"

fun answer(answer: String?) =
  answer?.let { checkCache(it) }

private fun checkCache(answer: String) =
  handle(checkAnswer(answer, getCurrent()), answer)

private fun submitAnswer(answer: String) {
  val year = getCurrent().year
  val day = getCurrent().day
  val level = getCurrent().level.toString()
  val url = "${getAocProperties().url}/20$year/day/$day/answer"
  val session = getAocProperties().session
  val response = submitAnswer(answer, level, url, session)
  handle(response, answer)
}

private fun handle(response: String, answer: String) {
  with(response) {
    when {
      contains(CORRECT) -> handleCorrect(answer)
      contains(WRONG_LEVEL) -> println(WRONG_LEVEL)
      contains(TOO_HIGH) -> println(TOO_HIGH)
      contains(TOO_LOW) -> println(TOO_LOW)
      else -> submitAnswer(answer)
    }
  }
}

private fun handleCorrect(answer: String) {
  println(CORRECT)
  cache(answer)
  next()
}
