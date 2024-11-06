package io.github.aj8gh.aoc.cache.answer

import io.github.aj8gh.aoc.cache.answer.AnswerType.*
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.io.answerCacheFile
import io.github.aj8gh.aoc.io.readYaml
import io.github.aj8gh.aoc.io.write
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.day
import io.github.aj8gh.aoc.properties.level
import io.github.aj8gh.aoc.properties.year

private const val COMPLETION_LEVEL_2 = 2
private const val COMPLETION_LEVEL_1 = 1
private const val COMPLETION_LEVEL_0 = 0

private const val NUMERIC_DEFAULT = "0"
private const val LONG_DEFAULT = "0L"
private const val STRING_DEFAULT = "\"\""

private const val JAVA = "java"
private const val KOTLIN = "kt"
private const val GO = "go"

private val types = mapOf(
  Pair(
    INT,
    mapOf(
      Pair(JAVA, "int"),
      Pair(KOTLIN, "Int"),
      Pair(GO, "int"),
    )
  ),

  Pair(
    STRING,
    mapOf(
      Pair(JAVA, "String"),
      Pair(KOTLIN, "String"),
      Pair(GO, "string"),
    )
  ),

  Pair(
    LONG,
    mapOf(
      Pair(JAVA, "long"),
      Pair(KOTLIN, "Long"),
      Pair(GO, "int64"),
    )
  ),
)

fun answerCache(): AnswerCache {
  val file = answerCacheFile()
  if (!file.exists()) write(answerCacheFile(), AnswerCache())
  return readYaml(file, AnswerCache::class.java)
}

fun checkAnswer(answer: String) =
  handle(answer, getAnswer())

fun cacheAnswer(answer: String) = cacheAnswer(level(), answer)

fun cacheAnswer(level: Int, answer: String) {
  val answers = answerCache()
  answers.save(year(), day(), level, answer)
  writeAnswers(answers)
}

fun dayCompletion(): Int {
  val day = answerCache().get(year(), day())
  return if (day.level(L2) != null) COMPLETION_LEVEL_2
  else if (day.level(L1) != null) COMPLETION_LEVEL_1
  else COMPLETION_LEVEL_0
}

fun clearCacheForDay() = answerCache().clear(year(), day())

fun type() = when {
  isIntOrNull(answer1()) && isIntOrNull(answer2()) -> INT
  isLong(answer1()) || isLong(answer2()) -> LONG
  else -> STRING
}

fun typeForLanguage() = types[type()]!![activeProfile().language]!!

fun assertFuncType() = when (type()) {
  STRING -> ""
  else -> "Int"
}

fun answer1OrDefault() = answer1()?.let { wrapIfString(handleLong(it)) } ?: default()

fun answer2OrDefault() = answer2()?.let { wrapIfString(handleLong(it)) } ?: default()

fun example1() = default()

fun example2() = default()

fun default(): String {
  val lang = activeProfile().language
  return when (type()) {
    INT -> NUMERIC_DEFAULT
    LONG -> LONG_DEFAULT.takeIf { lang == JAVA || lang == KOTLIN } ?: NUMERIC_DEFAULT
    else -> STRING_DEFAULT
  }
}

private fun wrapIfString(answer: String): String = answer
  .takeIf { type() != STRING }
  ?: "\"${answer}\""

private fun answer1() = answerCache().get(year(), day(), L1)

private fun answer2() = answerCache().get(year(), day(), L2)

private fun handleLong(answer: String): String {
  val lang = activeProfile().language
  if (type() == LONG && (lang == JAVA || lang == KOTLIN)) {
    return answer + "L"
  }
  return answer
}

private fun getAnswer() = answerCache().get(year(), day(), level())

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
