package io.github.aj8gh.aoc.test

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.http.Body
import io.github.aj8gh.aoc.http.SESSION_KEY
import io.github.aj8gh.aoc.test.context.appProps
import io.github.aj8gh.aoc.test.context.props
import io.github.aj8gh.aoc.test.context.reader
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
const val TEMPLATE_HOME = "${TEST_RESOURCES_ROOT}template/home/"
const val HTML_DIR = "${TEST_RESOURCES_ROOT}html/"
const val EXAMPLE_DIR = "${TEST_RESOURCES_ROOT}example/"
const val MARKDOWN_DIR = "${TEST_RESOURCES_ROOT}markdown/"
const val EXPECTED_CODE_DIR = "${TEST_RESOURCES_ROOT}expected-code/"
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
    resetFiles()
    resetOut()
    stubOutStream()
  }

  @AfterTest
  fun tearDown() {
    resetFiles()
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
fun html(year: Int = props.year(), day: Int = props.day()) = reader.read("${HTML_DIR}y$year/d$day.html")
fun readmeUrl(year: Int = props.year(), day: Int = props.day()) = "/20$year/day/$day"
fun inputUrl(year: Int = props.year(), day: Int = props.day()) = "/20$year/day/$day/input"
fun markdown(year: Int = props.year(), day: Int = props.day()) = reader.read("${MARKDOWN_DIR}y$year/d$day.md")
fun expectedCodeDir() = "$EXPECTED_CODE_DIR/${props.activeProfile().language}"
fun expectedMainFile(year: Int = props.year(), day: Int = props.day()) = reader.read("${expectedCodeDir()}/y$year/d$day/${appProps.files.mainTemplate}")
fun expectedTestFile(year: Int = props.year(), day: Int = props.day()) = reader.read("${expectedCodeDir()}/y$year/d$day/${appProps.files.testTemplate}")

fun readmeRequestMapping(
  response: String,
  year: Int = props.year(),
  day: Int = props.day()
) = get(readmeUrl(year, day))
  .withCookie(SESSION_KEY, matching(SESSION))
  .willReturn(
    ResponseDefinitionBuilder.responseDefinition()
      .withResponseBody(Body(response))
  )

fun getInputMapping(year: Int = props.year(), day: Int = props.day()): MappingBuilder =
  get(inputUrl(year, day))
    .withCookie(SESSION_KEY, matching(SESSION))
    .willReturn(
      ResponseDefinitionBuilder.responseDefinition()
        .withStatus(200)
        .withResponseBody(Body(testInput()))
    )

private fun resetFiles() {
  File(appProps.files.dirs.home()).deleteRecursively()
  File(TEMPLATE_HOME).copyRecursively(File(appProps.files.dirs.home()), true)
  props.forceLoadAocProperties()
  props.forceLoadActiveProfile()
}

private fun resetOut() = System.setOut(originalOut)
