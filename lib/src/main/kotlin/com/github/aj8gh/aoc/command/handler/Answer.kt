package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.http.answer
import com.github.aj8gh.aoc.properties.getActiveProperties
import com.github.aj8gh.aoc.properties.getAocProperties

private const val TOO_HIGH = "That's not the right answer; your answer is too high."
private const val TOO_LOW = "That's not the right answer; your answer is too low."
private const val WRONG_LEVEL = "You don't seem to be solving the right level.  Did you already complete it?"
private const val CORRECT = "Congratulations, that's the correct answer!"

fun answer(answer: String?) {
  if (answer == null) return

  val year = getActiveProperties().current.year
  val day = getActiveProperties().current.day
  val level = getActiveProperties().current.level

  // checkCache()

  val url = "${getAocProperties().url}/20$year/day/$day/answer"
  val session = getAocProperties().session

  val response = answer(answer, level.toString(), url, session)
  handle(response)
}

private fun handle(response: String) {
  with(response) {
    when {
      contains(CORRECT) -> handleCorrect()
      contains(WRONG_LEVEL) -> println(WRONG_LEVEL)
      contains(TOO_HIGH) -> println(TOO_HIGH)
      contains(TOO_LOW) -> println(TOO_LOW)
    }
  }
}

private fun handleCorrect() {
  // cache()
  // next()
  println(CORRECT)
}
