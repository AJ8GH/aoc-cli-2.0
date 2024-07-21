package io.github.aj8gh.aoc.cache.answer

import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.io.answerCacheFile
import io.github.aj8gh.aoc.io.readYaml
import io.github.aj8gh.aoc.io.write
import io.github.aj8gh.aoc.properties.day
import io.github.aj8gh.aoc.properties.level
import io.github.aj8gh.aoc.properties.year
import io.github.aj8gh.aoc.util.L1
import io.github.aj8gh.aoc.util.L2

private const val COMPLETION_LEVEL_2 = 2
private const val COMPLETION_LEVEL_1 = 1
private const val COMPLETION_LEVEL_0 = 0

private const val INT_TYPE = "Int"
private const val LONG_TYPE = "Long"
private const val STRING_TYPE = "String"

private const val INT_DEFAULT = "0"
private const val LONG_DEFAULT = "0L"
private const val STRING_DEFAULT = ""

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

fun type() = when {
  isIntOrNull(answer1()) && isIntOrNull(answer2()) -> INT_TYPE
  isLong(answer1()) || isLong(answer2()) -> LONG_TYPE
  else -> STRING_TYPE
}

fun answer1OrDefault() = wrapIfString(answer1() ?: default())

fun answer2OrDefault() = wrapIfString(answer2() ?: default())

fun example1() = wrapIfString(default())

fun example2() = wrapIfString(default())

private fun wrapIfString(answer: String): String = answer
  .takeIf { type() != STRING_TYPE }
  ?: "\"${answer}\""

private fun answer1() = getAnswers().get(year(), day(), L1)

private fun answer2() = getAnswers().get(year(), day(), L2)

private fun default() = when (type()) {
  INT_TYPE -> INT_DEFAULT
  LONG_TYPE -> LONG_DEFAULT
  else -> STRING_DEFAULT
}

private fun getAnswer() = getAnswers().get(year(), day(), level())

private fun getAnswers(): AnswerCache {
  val file = answerCacheFile()
  if (!file.exists()) write(answerCacheFile(), AnswerCache())
  return readYaml(file, AnswerCache::class.java)
}

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

private fun isInt(s: String?) = s?.toIntOrNull() != null

private fun isIntOrNull(s: String?) = s == null || s.toIntOrNull() != null

private fun isLong(s: String?) = s?.toLongOrNull() != null

private fun isTooLow(answer: String, cachedAnswer: String) =
  isInt(answer) && isInt(cachedAnswer) && answer.toInt() < cachedAnswer.toInt()

private fun isTooHigh(answer: String, cachedAnswer: String) =
  isInt(answer) && isInt(cachedAnswer) && answer.toInt() > cachedAnswer.toInt()
