package io.github.aj8gh.aoc.test

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.http.Body
import io.github.aj8gh.aoc.http.SESSION_KEY
import io.github.aj8gh.aoc.test.context.PROPS
import io.github.aj8gh.aoc.test.context.PROPS_FILES
import io.github.aj8gh.aoc.test.context.READER
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
    PROPS.forceLoadAocProperties()
    PROPS.forceLoadActiveProfile()
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

fun html() = html(PROPS.year(), PROPS.day())
fun html(year: Int, day: Int) = READER.read("${HTML_DIR}y$year/d$day.html")

fun readmeRequestMapping(response: String) = readmeRequestMapping(response, PROPS.year(), PROPS.day())

fun readmeRequestMapping(response: String, year: Int, day: Int) = get(readmeUrl(year, day))
  .withCookie(SESSION_KEY, matching(SESSION))
  .willReturn(
    ResponseDefinitionBuilder.responseDefinition()
      .withResponseBody(Body(response))
  )

fun readmeUrl() = readmeUrl(PROPS.year(), PROPS.day())

fun readmeUrl(year: Int, day: Int) = "/20$year/day/$day"

fun inputUrl() = "/20${PROPS.year()}/day/${PROPS.day()}/input"

fun inputUrl(year: Int, day: Int) = "/20$year/day/$day/input"

fun getInputMapping(): MappingBuilder = getInputMapping(PROPS.year(), PROPS.day())

fun getInputMapping(year: Int, day: Int): MappingBuilder =
  get(inputUrl(year, day))
    .withCookie(SESSION_KEY, matching(SESSION))
    .willReturn(
      ResponseDefinitionBuilder.responseDefinition()
        .withStatus(200)
        .withResponseBody(Body(testInput()))
    )

fun markdown() = READER.read("${MARKDOWN_DIR}y${PROPS.year()}/d${PROPS.day()}.md")

fun expectedCodeDir() = "$EXPECTED_CODE_DIR/${PROPS.activeProfile().language}"

fun expectedMainFile() = READER.read("${expectedCodeDir()}/y${PROPS.year()}/d${PROPS.day()}/$MAIN_FILE")

fun expectedTestFile() = READER.read("${expectedCodeDir()}/y${PROPS.year()}/d${PROPS.day()}/$TEST_FILE")

private fun resetFiles() {
  File(AOC_HOME).deleteRecursively()
  File(TEMPLATE_HOME).copyRecursively(File(AOC_HOME), true)
}

private fun resetOut() = System.setOut(originalOut)

private fun overrideAndLoadProperties() {
  PROPS_FILES.homeOverride = AOC_HOME
  PROPS.aocProperties()
}
