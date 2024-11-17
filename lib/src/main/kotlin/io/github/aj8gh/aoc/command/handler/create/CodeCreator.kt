package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.cache.answer.AnswerCache
import io.github.aj8gh.aoc.io.DirCreator
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Writer
import io.github.aj8gh.aoc.properties.PropertiesManager
import java.io.File

private const val YEAR_PLACEHOLDER = "\${YEAR}"
private const val DAY_PLACEHOLDER = "\${DAY}"
private const val TYPE_PLACEHOLDER = "\${TYPE}"
private const val ANSWER_1_PLACEHOLDER = "\${ANSWER_1}"
private const val ANSWER_2_PLACEHOLDER = "\${ANSWER_2}"
private const val EXAMPLE_1_PLACEHOLDER = "\${EXAMPLE_1}"
private const val EXAMPLE_2_PLACEHOLDER = "\${EXAMPLE_2}"

class CodeCreator(
  private val answerCache: AnswerCache,
  private val files: FileManager,
  private val dirCreator: DirCreator,
  private val props: PropertiesManager,
  private val writer: Writer,
  private val log: Logger,
) {

  fun create() {
    dirCreator.mkdirs(files.mainTemplateFile())
    dirCreator.mkdirs(files.testTemplateFile())
    val answer1 = answerCache.answer1OrDefault()
    val answer2 = answerCache.answer2OrDefault()
    val default = answerCache.default()
    val answers = Pair(answer1, answer2)
    val mainAnswers = answers
      .takeIf { props.activeProfile().writeAnswerInCode }
      ?: Pair(default, default)

    generate(files.mainFile(), files.mainTemplateFile(), mainAnswers)
    generate(files.testFile(), files.testTemplateFile(), answers)
  }

  private fun generate(file: File, template: File, answers: Pair<String, String>) {
    if (file.exists()) {
      log.debug("File ${file.absolutePath} already exists, skipping code generation")
      return
    }
    dirCreator.mkdirs(file)
    writer.write(file, format(template.readText(), answers))
  }

  private fun format(text: String, answers: Pair<String, String>) = text
    .replace(YEAR_PLACEHOLDER, props.year().toString())
    .replace(DAY_PLACEHOLDER, props.day().toString())
    .replace(TYPE_PLACEHOLDER, answerCache.typeForLanguage())
    .replace(ANSWER_1_PLACEHOLDER, answers.first)
    .replace(ANSWER_2_PLACEHOLDER, answers.second)
    .replace(EXAMPLE_1_PLACEHOLDER, answerCache.example1())
    .replace(EXAMPLE_2_PLACEHOLDER, answerCache.example2())
}
