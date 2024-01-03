package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.properties.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

const val DEFAULT_YEAR = 15
const val DEFAULT_DAY = 1
const val DEFAULT_LEVEL = 1
const val HTTP_PORT = 80
const val SESSION = "session"
const val AOC_HOME = "src/test/resources/"
const val ACTIVE_CONFIG_FILE = "${AOC_HOME}current-test.yaml"
const val TEMPLATE_CONFIG_FILE = "${AOC_HOME}templates/current-test-template.yaml"
const val AOC_CONFIG_FILE = "aoc-test.yaml"

private val outContent = ByteArrayOutputStream()
private val originalOut = System.out

open class BaseTest {
  @BeforeTest
  fun setUp() {
    aocOverride = "${AOC_HOME}${AOC_CONFIG_FILE}"
    homeOverride = AOC_HOME
    getAocProperties()
    System.setOut(PrintStream(outContent))
  }

  @AfterTest
  fun tearDown() {
    updateProperties(readYaml(TEMPLATE_CONFIG_FILE, Properties::class.java))
    System.setOut(originalOut)
  }

  protected fun assertMessage(expected: String) =
    assertEquals(expected, outContent.toString().trim())

  protected fun activeProperties() =
    readYaml(ACTIVE_CONFIG_FILE, Properties::class.java)
}
