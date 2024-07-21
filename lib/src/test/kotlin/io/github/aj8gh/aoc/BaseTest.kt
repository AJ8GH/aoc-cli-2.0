package io.github.aj8gh.aoc

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.http.Body
import io.github.aj8gh.aoc.http.SESSION_KEY
import io.github.aj8gh.aoc.io.homeOverride
import io.github.aj8gh.aoc.io.read
import io.github.aj8gh.aoc.io.readYaml
import io.github.aj8gh.aoc.properties.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

const val ANSWER = "123"
const val HTTP_PORT = 80
const val EMPTY_MESSAGE = ""
const val SESSION = "session"
const val TEST_RESOURCES_ROOT = "src/test/resources/"
const val AOC_HOME = "${TEST_RESOURCES_ROOT}home/"
const val TEMPLATE_HOME = "${TEST_RESOURCES_ROOT}template/home/"
const val TEMPLATE_PROPERTIES_FILE = "${TEMPLATE_HOME}active.yaml"
const val HTML_DIR = "${TEST_RESOURCES_ROOT}html/"
const val EXAMPLE_DIR = "${TEST_RESOURCES_ROOT}example/"
const val MARKDOWN_DIR = "${TEST_RESOURCES_ROOT}markdown/"
const val EXPECTED_CODE_DIR = "${TEST_RESOURCES_ROOT}expected-code/"
const val MAIN_FILE = "main.txt"
const val TEST_FILE = "test.txt"

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

fun stubOutStream() {
  outContent = ByteArrayOutputStream()
  System.setOut(PrintStream(outContent))
}

fun outContent() = outContent.toString().trim()

fun testInput() = INPUT.trimIndent().trim()

fun html() = read("${HTML_DIR}y${year()}/d${day()}.html")

fun readmeRequestMapping(response: String) = WireMock.get(readmeUrl())
  .withCookie(SESSION_KEY, WireMock.matching(SESSION))
  .willReturn(
    ResponseDefinitionBuilder.responseDefinition()
      .withResponseBody(Body(response))
  )

fun readmeUrl() = "/20${year()}/day/${day()}"

fun inputUrl() = "/20${year()}/day/${day()}/input"

fun getInputMapping(): MappingBuilder =
  WireMock.get(inputUrl())
    .withCookie(SESSION_KEY, WireMock.matching(SESSION))
    .willReturn(
      ResponseDefinitionBuilder.responseDefinition()
        .withStatus(200)
        .withResponseBody(Body(testInput()))
    )

fun markdown() = read("${MARKDOWN_DIR}y${year()}/d${day()}.md")

fun expectedMainFile() = read("${EXPECTED_CODE_DIR}y${year()}/d${day()}/$MAIN_FILE")

fun expectedTestFile() = read("${EXPECTED_CODE_DIR}y${year()}/d${day()}/$TEST_FILE")

private fun resetProperties() =
  updateProperties(readYaml(File(TEMPLATE_PROPERTIES_FILE), Properties::class.java))

private fun resetFiles() {
  File(AOC_HOME).deleteRecursively()
  File(TEMPLATE_HOME).copyRecursively(File(AOC_HOME), true)
}

private fun resetOut() = System.setOut(originalOut)

private fun overrideAndLoadProperties() {
  homeOverride = AOC_HOME
  aocProperties()
}
