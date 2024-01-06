package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.command.handler.create.getInputFile
import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

const val HTTP_PORT = 80
const val EMPTY_MESSAGE = ""
const val SESSION = "session"
const val AOC_HOME = "src/test/resources/"
const val PROJECT_ROOT = "src/test/resources/project/"
const val ACTIVE_PROPERTIES_FILE = "${AOC_HOME}current-test.yaml"
const val TEMPLATE_PROPERTIES_FILE = "${AOC_HOME}templates/current-test-template.yaml"
const val TEMPLATE_ANSWERS_FILE = "${AOC_HOME}templates/test-answer-cache-template.yaml"

private val originalOut = System.out
private lateinit var outContent: ByteArrayOutputStream

open class BaseTest {

  @BeforeTest
  fun setUp() {
    homeOverride = AOC_HOME
    projectOverride = PROJECT_ROOT
    aocProperties()
    resetOutStream()
  }

  @AfterTest
  fun tearDown() {
    updateProperties(readYaml(TEMPLATE_PROPERTIES_FILE, Properties::class.java))
    write(
      File("$AOC_HOME/cache/answer/answers.yaml"),
      readYaml(TEMPLATE_ANSWERS_FILE, Map::class.java)
    )
    System.setOut(originalOut)
    getInputFile().delete()
  }
}

fun getEchoMessage(year: Int, day: Int, level: Int) =
  "You are on year $year day $day level $level"

fun activeProperties() =
  readYaml(ACTIVE_PROPERTIES_FILE, Properties::class.java)

fun resetOutStream() {
  outContent = ByteArrayOutputStream()
  System.setOut(PrintStream(outContent))
}

fun outContent() = outContent.toString().trim()
