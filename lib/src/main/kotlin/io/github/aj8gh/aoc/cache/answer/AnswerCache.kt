package io.github.aj8gh.aoc.cache.answer

import io.github.aj8gh.aoc.cache.answer.AnswerType.*
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.io.DirCreator
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
import io.github.aj8gh.aoc.properties.PropertiesManager

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

class AnswerCache(
  private val files: FileManager,
  private val writer: Writer,
  private val reader: Reader,
  private val props: PropertiesManager,
  private val dirCreator: DirCreator,
) {

  fun cache(): Answers {
    if (!files.answerCacheFile().exists()) {
      dirCreator.mkdirs(files.answerCacheFile())
      writer.write(files.answerCacheFile(), Answers())
    }
    return reader.readYaml(files.answerCacheFile(), Answers::class.java)
  }

  fun checkAnswer(answer: String) = handle(answer, getAnswer())

  fun cacheAnswer(answer: String) = cacheAnswer(props.level(), answer)

  fun dayCompletion(): Int {
    val day = cache().get(props.year(), props.day())
    return if (day.level(L2) != null) COMPLETION_LEVEL_2
    else if (day.level(L1) != null) COMPLETION_LEVEL_1
    else COMPLETION_LEVEL_0
  }

  fun cacheAnswer(level: Int, answer: String) {
    val answers = cache()
    answers.save(props.year(), props.day(), level, answer)
    writeAnswers(answers)
  }

  fun clearCacheForDay() = cache().clear(props.year(), props.day())

  fun type() = when {
    isIntOrNull(answer1()) && isIntOrNull(answer2()) -> INT
    isLong(answer1()) || isLong(answer2()) -> LONG
    else -> STRING
  }

  fun typeForLanguage() = types[type()]!![props.activeProfile().language]!!

  fun answer1OrDefault() = answer1()?.let { wrapIfString(handleLong(it)) } ?: default()

  fun answer2OrDefault() = answer2()?.let { wrapIfString(handleLong(it)) } ?: default()

  fun example1() = default()

  fun example2() = default()

  fun default(): String {
    val lang = props.activeProfile().language
    return when (type()) {
      INT -> NUMERIC_DEFAULT
      LONG -> LONG_DEFAULT.takeIf { lang == JAVA || lang == KOTLIN } ?: NUMERIC_DEFAULT
      else -> STRING_DEFAULT
    }
  }

  private fun wrapIfString(answer: String): String = answer
    .takeIf { type() != STRING }
    ?: "\"${answer}\""

  private fun answer1() = cache().get(props.year(), props.day(), L1)

  private fun answer2() = cache().get(props.year(), props.day(), L2)

  private fun handleLong(answer: String): String {
    val lang = props.activeProfile().language
    if (type() == LONG && (lang == JAVA || lang == KOTLIN)) {
      return answer + "L"
    }
    return answer
  }

  private fun getAnswer() = cache().get(props.year(), props.day(), props.level())

  private fun writeAnswers(answers: Answers) = writer.write(files.answerCacheFile(), answers)

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
}
