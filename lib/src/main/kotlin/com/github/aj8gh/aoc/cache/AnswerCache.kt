package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.command.handler.*
import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.aocHome
import com.github.aj8gh.aoc.properties.current
import java.io.File

private const val ANSWER_CACHE = "cache/answer/answers.yaml"

fun checkAnswer(answer: String) =
    handle(answer, getCachedAnswer())

fun cache(answer: String) {
  val answers = getAnswers().toMutableMap()
  answers
      .computeIfAbsent(current().year.toString()) {
        mutableMapOf(current().day.toString() to mutableMapOf())
      }.computeIfAbsent(current().day.toString()) {
        mutableMapOf(current().level.toString() to answer)
      }[current().level.toString()] = answer
  writeAnswers(answers)
}

private fun getCachedAnswer(): String? =
    getAnswers()[current().year.toString()]
        ?.get(current().day.toString())
        ?.get(current().level.toString())

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
