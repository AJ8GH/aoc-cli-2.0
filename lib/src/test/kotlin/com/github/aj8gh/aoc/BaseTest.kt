package com.github.aj8gh.aoc

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.properties.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

const val HTTP_PORT = 80
const val EMPTY_MESSAGE = ""
const val SESSION = "session"
const val TEST_RESOURCES_ROOT = "src/test/resources/"
const val AOC_HOME = "${TEST_RESOURCES_ROOT}home/"
const val TEMPLATE_HOME = "${TEST_RESOURCES_ROOT}template/home/"
const val PROJECT_ROOT = "${AOC_HOME}project/"
const val ACTIVE_PROPERTIES_FILE = "${AOC_HOME}active.yaml"
const val TEMPLATE_PROPERTIES_FILE = "${TEMPLATE_HOME}active.yaml"
const val INPUT = """
      1 2 3
      4 5 6
      7 8 9
    """

private val originalOut = System.out
private lateinit var outContent: ByteArrayOutputStream

open class BaseTest {

  @BeforeTest
  fun setUp() {
    overrideAndLoadProperties()
    stubOutStream()
  }

  @AfterTest
  fun tearDown() {
    resetProperties()
    resetFiles()
    resetOut()
  }
}

fun getEchoMessage(year: Int, day: Int, level: Int) =
  "You are on year $year day $day level $level"

fun activeProperties() =
  readYaml(ACTIVE_PROPERTIES_FILE, Properties::class.java)

fun stubOutStream() {
  outContent = ByteArrayOutputStream()
  System.setOut(PrintStream(outContent))
}

fun outContent() = outContent.toString().trim()

fun testInput() = INPUT.trimIndent().trim()

private fun resetProperties() =
  updateProperties(readYaml(TEMPLATE_PROPERTIES_FILE, Properties::class.java))

private fun resetFiles() {
  File(AOC_HOME).deleteRecursively()
  File(TEMPLATE_HOME).copyRecursively(File(AOC_HOME), true)
}

private fun resetOut() = System.setOut(originalOut)

private fun overrideAndLoadProperties() {
  homeOverride = AOC_HOME
  projectOverride = PROJECT_ROOT
  aocProperties()
}
