package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.cache.answer.*
import io.github.aj8gh.aoc.io.*
import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.day
import io.github.aj8gh.aoc.properties.year
import java.io.File

private const val YEAR_PLACEHOLDER = "\${YEAR}"
private const val DAY_PLACEHOLDER = "\${DAY}"
private const val TYPE_PLACEHOLDER = "\${TYPE}"
private const val ASSERT_FUNC_TYPE_PLACEHOLDER = "\${ASSERT_FUNC_TYPE}"
private const val ANSWER_1_PLACEHOLDER = "\${ANSWER_1}"
private const val ANSWER_2_PLACEHOLDER = "\${ANSWER_2}"
private const val EXAMPLE_1_PLACEHOLDER = "\${EXAMPLE_1}"
private const val EXAMPLE_2_PLACEHOLDER = "\${EXAMPLE_2}"

fun code() {
  createSourceDirsIfNotExists()
  val answer1 = answer1OrDefault()
  val answer2 = answer2OrDefault()
  val default = default()
  val answers = Pair(answer1, answer2)
  val mainAnswers = answers.takeIf { activeProfile().writeAnswerInCode } ?: Pair(default, default)

  generate(mainFile(), mainTemplateFile(), mainAnswers)
  generate(testFile(), testTemplateFile(), answers)
}

private fun generate(file: File, template: File, answers: Pair<String, String>) {
  if (!file.exists()) write(file, format(template.readText(), answers))
}

private fun format(text: String, answers: Pair<String, String>) = text
  .replace(YEAR_PLACEHOLDER, year().toString())
  .replace(DAY_PLACEHOLDER, day().toString())
  .replace(TYPE_PLACEHOLDER, typeForLanguage())
  .replace(ASSERT_FUNC_TYPE_PLACEHOLDER, assertFuncType())
  .replace(ANSWER_1_PLACEHOLDER, answers.first)
  .replace(ANSWER_2_PLACEHOLDER, answers.second)
  .replace(EXAMPLE_1_PLACEHOLDER, example1())
  .replace(EXAMPLE_2_PLACEHOLDER, example2())
