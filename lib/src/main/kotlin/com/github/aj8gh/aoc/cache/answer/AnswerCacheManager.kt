package com.github.aj8gh.aoc.cache.answer

import com.github.aj8gh.aoc.command.handler.*
import com.github.aj8gh.aoc.io.answerCacheFile
import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.level
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.util.L1
import com.github.aj8gh.aoc.util.L2

private const val COMPLETION_LEVEL_2 = 2
private const val COMPLETION_LEVEL_1 = 1
private const val COMPLETION_LEVEL_0 = 0

fun checkAnswer(answer: String) =
  handle(answer, getAnswer())

fun cacheAnswer(answer: String) = cacheAnswer(level(), answer)

fun cacheAnswer(level: Int, answer: String) {
  val answers = getAnswers()
  answers.save(year(), day(), level, answer)
  writeAnswers(answers)
}

fun dayCompletion(): Int {
  val day = getAnswers().get(year(), day())
  return if (day.level(L2) != null) COMPLETION_LEVEL_2
  else if (day.level(L1) != null) COMPLETION_LEVEL_1
  else COMPLETION_LEVEL_0
}

fun clearCacheForDay() = getAnswers().clear(year(), day())

private fun getAnswer() = getAnswers().get(year(), day(), level())
private fun getAnswers(): AnswerCache = readYaml(answerCacheFile(), AnswerCache::class.java)
private fun writeAnswers(answers: AnswerCache) = write(answerCacheFile(), answers)

private fun handle(answer: String, cachedAnswer: String?) =
  with(cachedAnswer) {
    when {
      isNullOrEmpty() -> NOT_CACHED
      equals(answer) -> CORRECT
      else -> handleWrongAnswer(answer, cachedAnswer!!)
    }
  }

private fun handleWrongAnswer(answer: String, cachedAnswer: String) =
  when {
    isTooLow(answer, cachedAnswer) -> TOO_LOW
    isTooHigh(answer, cachedAnswer) -> TOO_HIGH
    else -> INCORRECT
  }

private fun isInt(s: String) = s.toIntOrNull() != null

private fun isTooLow(answer: String, cachedAnswer: String) =
  isInt(answer) && isInt(cachedAnswer) && answer.toInt() < cachedAnswer.toInt()

private fun isTooHigh(answer: String, cachedAnswer: String) =
  isInt(answer) && isInt(cachedAnswer) && answer.toInt() > cachedAnswer.toInt()
