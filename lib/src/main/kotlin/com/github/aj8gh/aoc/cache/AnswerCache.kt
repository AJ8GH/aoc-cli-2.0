package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.command.handler.*
import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.aocHome
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.level
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.util.L1
import com.github.aj8gh.aoc.util.L2
import java.io.File

private const val ANSWER_CACHE = "cache/answer/answers.yaml"

fun checkAnswer(answer: String) =
  handle(answer, getCachedAnswer())

fun cacheAnswer(answer: String) = cacheAnswer(level(), answer)

fun cacheAnswer(level: Int, answer: String) {
  val answers = getAnswers().toMutableMap()
  answers
    .computeIfAbsent(year().toString()) {
      mutableMapOf(day().toString() to mutableMapOf())
    }.computeIfAbsent(day().toString()) {
      mutableMapOf(level.toString() to answer)
    }[level.toString()] = answer
  writeAnswers(answers)
}

fun dayCompletion(): Int {
  val year = getAnswers()[year().toString()]
  val day = year?.get(day().toString())
  return if (year == null || day == null || day[L1.toString()] == null) 0
  else if (day[L2.toString()] == null) 1
  else 2
}

fun clearCacheForDay() =
  getAnswers()[year().toString()]?.get(day().toString())?.clear()

private fun getCachedAnswer(): String? =
  getAnswers()[year().toString()]
    ?.get(day().toString())
    ?.get(level().toString())

@SuppressWarnings("unchecked")
private fun getAnswers(): MutableMap<String, MutableMap<String, MutableMap<String, String>>> =
  readYaml("${aocHome()}$ANSWER_CACHE", Map::class.java)
      as MutableMap<String, MutableMap<String, MutableMap<String, String>>>

private fun writeAnswers(answers: Map<String, Map<String, Map<String, String>?>?>) =
  write(File("${aocHome()}$ANSWER_CACHE"), answers)

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
