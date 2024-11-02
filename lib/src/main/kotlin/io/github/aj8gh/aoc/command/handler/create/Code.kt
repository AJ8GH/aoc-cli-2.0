package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.cache.answer.*
import io.github.aj8gh.aoc.io.*
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
  generate(mainFile(), mainTemplateFile())
  generate(testFile(), testTemplateFile())
}

private fun generate(file: File, template: File) {
  if (!file.exists()) write(file, format(template.readText()))
}

private fun format(text: String) = text
  .replace(YEAR_PLACEHOLDER, year().toString())
  .replace(DAY_PLACEHOLDER, day().toString())
  .replace(TYPE_PLACEHOLDER, typeForLanguage())
  .replace(ASSERT_FUNC_TYPE_PLACEHOLDER, assertFuncType())
  .replace(ANSWER_1_PLACEHOLDER, answer1OrDefault())
  .replace(ANSWER_2_PLACEHOLDER, answer2OrDefault())
  .replace(EXAMPLE_1_PLACEHOLDER, example1())
  .replace(EXAMPLE_2_PLACEHOLDER, example2())
