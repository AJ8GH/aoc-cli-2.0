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
const val ROOT = "src/test/resources/"
const val AOC_HOME = "${ROOT}home/"
const val TEMPLATE_HOME = "${ROOT}template/home/"
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
    homeOverride = AOC_HOME
    projectOverride = PROJECT_ROOT
    aocProperties()
    resetOutStream()
  }

  @AfterTest
  fun tearDown() {
    updateProperties(readYaml(TEMPLATE_PROPERTIES_FILE, Properties::class.java))
    System.setOut(originalOut)
    File(AOC_HOME).deleteRecursively()
    File(TEMPLATE_HOME).copyRecursively(File(AOC_HOME), true)
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

fun testInput() = INPUT.trimIndent().trim()
