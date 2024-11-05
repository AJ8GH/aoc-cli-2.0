package io.github.aj8gh.aoc

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.http.Body
import io.github.aj8gh.aoc.command.handler.EXTENSION
import io.github.aj8gh.aoc.http.SESSION_KEY
import io.github.aj8gh.aoc.io.homeOverride
import io.github.aj8gh.aoc.io.read
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
const val HTML_DIR = "${TEST_RESOURCES_ROOT}html/"
const val EXAMPLE_DIR = "${TEST_RESOURCES_ROOT}example/"
const val MARKDOWN_DIR = "${TEST_RESOURCES_ROOT}markdown/"
const val EXPECTED_CODE_DIR = "${TEST_RESOURCES_ROOT}expected-code/"
const val MAIN_FILE = "main.txt"
const val TEST_FILE = "test.txt"
const val GO_PROFILE = "go"
const val KT_PROFILE = "kt"

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
    resetFiles()
    forceLoadAocProperties()
    forceLoadActiveProfile()
    resetOut()
  }
}

fun getEchoMessage(year: Int, day: Int, level: Int, profile: String) =
  "You are on year $year day $day level $level. Active profile: $profile"

fun stubOutStream() {
  outContent = ByteArrayOutputStream()
  System.setOut(PrintStream(outContent))
}

fun outContent() = outContent.toString().trim()

fun testInput() = INPUT.trimIndent().trim()

fun html() = html(year(), day())
fun html(year: Int, day: Int) = read("${HTML_DIR}y$year/d$day.html")

fun readmeRequestMapping(response: String) = readmeRequestMapping(response, year(), day())

fun readmeRequestMapping(response: String, year: Int, day: Int) = WireMock.get(readmeUrl(year, day))
  .withCookie(SESSION_KEY, WireMock.matching(SESSION))
  .willReturn(
    ResponseDefinitionBuilder.responseDefinition()
      .withResponseBody(Body(response))
  )

fun readmeUrl() = readmeUrl(year(), day())

fun readmeUrl(year: Int, day: Int) = "/20$year/day/$day"

fun inputUrl() = "/20${year()}/day/${day()}/input"

fun inputUrl(year: Int, day: Int) = "/20$year/day/$day/input"

fun getInputMapping(): MappingBuilder = getInputMapping(year(), day())

fun getInputMapping(year: Int, day: Int): MappingBuilder =
  WireMock.get(inputUrl(year, day))
    .withCookie(SESSION_KEY, WireMock.matching(SESSION))
    .willReturn(
      ResponseDefinitionBuilder.responseDefinition()
        .withStatus(200)
        .withResponseBody(Body(testInput()))
    )

fun markdown() = read("${MARKDOWN_DIR}y${year()}/d${day()}.md")

fun expectedCodeDir() = "$EXPECTED_CODE_DIR/${activeProfile().language}"

fun expectedMainFile() = read("${expectedCodeDir()}/y${year()}/d${day()}/$MAIN_FILE")

fun expectedTestFile() = read("${expectedCodeDir()}/y${year()}/d${day()}/$TEST_FILE")

private fun templateActiveProfileFile() = "${TEMPLATE_HOME}${aocProperties().active}$EXTENSION"

//private fun resetProperties() =
//  updateProfile(readYaml(File(templateActiveProfileFile()), Profile::class.java))

private fun resetFiles() {
  File(AOC_HOME).deleteRecursively()
  File(TEMPLATE_HOME).copyRecursively(File(AOC_HOME), true)
}

private fun resetOut() = System.setOut(originalOut)

private fun overrideAndLoadProperties() {
  homeOverride = AOC_HOME
  aocProperties()
}
