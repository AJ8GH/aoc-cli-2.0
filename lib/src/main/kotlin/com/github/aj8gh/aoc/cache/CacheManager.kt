package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.command.handler.*
import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.getAocHome
import com.github.aj8gh.aoc.properties.getCurrent
import java.io.File

private const val CACHE_DIR = "cache/"
private const val ANSWER_CACHE = "${CACHE_DIR}answer/real.yaml"

fun checkAnswer(answer: String) =
    handle(answer, getCachedAnswer())

fun cache(answer: String) {
  val answers = getAnswers().toMutableMap()
  answers
      .computeIfAbsent(getCurrent().year.toString()) {
        mutableMapOf(getCurrent().day.toString() to mutableMapOf())
      }.computeIfAbsent(getCurrent().day.toString()) {
        mutableMapOf(getCurrent().level.toString() to answer)
      }[getCurrent().level.toString()] = answer
  writeAnswers(answers)
}

private fun getCachedAnswer(): String? =
    getAnswers()[getCurrent().year.toString()]
        ?.get(getCurrent().day.toString())
        ?.get(getCurrent().level.toString())

@SuppressWarnings("unchecked")
private fun getAnswers(): MutableMap<String, MutableMap<String, MutableMap<String, String>>> =
    readYaml("${getAocHome()}$ANSWER_CACHE", Map::class.java)
        as MutableMap<String, MutableMap<String, MutableMap<String, String>>>

private fun writeAnswers(answers: Map<String, Map<String, Map<String, String>?>?>) =
    write(File("${getAocHome()}$ANSWER_CACHE"), answers)

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
