package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.Properties
import com.github.aj8gh.aoc.properties.getAocProperties
import com.github.aj8gh.aoc.properties.homeOverride
import com.github.aj8gh.aoc.properties.updateProperties
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

const val HTTP_PORT = 80
const val EMPTY_MESSAGE = ""
const val SESSION = "session"
const val AOC_HOME = "src/test/resources/"
const val ACTIVE_PROPERTIES_FILE = "${AOC_HOME}current-test.yaml"
const val TEMPLATE_PROPERTIES_FILE = "${AOC_HOME}templates/current-test-template.yaml"
const val TEMPLATE_ANSWERS_FILE = "${AOC_HOME}templates/test-answer-cache-template.yaml"

private val originalOut = System.out
private lateinit var outContent: ByteArrayOutputStream

open class BaseTest {
  @BeforeTest
  fun setUp() {
    homeOverride = AOC_HOME
    getAocProperties()
    resetOutStream()
  }

  @AfterTest
  fun tearDown() {
    updateProperties(readYaml(TEMPLATE_PROPERTIES_FILE, Properties::class.java))
    write(File("${AOC_HOME}/cache/answer/real.yaml"), readYaml(TEMPLATE_ANSWERS_FILE, Map::class.java))
    System.setOut(originalOut)
  }

  protected fun givenCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) {
    set(year = year, day = day, level = level)
    resetOutStream()
  }

  protected fun givenTheFollowingRequestStub(stub: MappingBuilder): StubMapping =
      stubFor(stub)

  protected fun whenNextIsCalledFor(next: Boolean) =
      next(next)

  protected fun whenSetIsCalledFor(year: Int?, day: Int?, level: Int?) =
      set(year = year, day = day, level = level)

  protected fun whenEchoCurrentIsCalledFor(echo: Boolean) =
      echoCurrent(echo)

  protected fun whenAnswerIsCalledWith(answer: String) =
      answer(answer)

  protected fun thenCurrentYearDayAndLevelAre(year: Int, day: Int, level: Int) {
    val actual = activeProperties()
    assertEquals(year, actual.current.year)
    assertEquals(day, actual.current.day)
    assertEquals(level, actual.current.level)
  }

  protected fun thenTheFollowingMessageIsEchoed(expected: String) =
      assertEquals(expected, outContent.toString().trim())

  protected fun thenTheFollowingMessagesAreEchoed(vararg expected: String) {
    val actual = outContent.toString().trim().lines()
    assertEquals(expected.size, actual.size, "Found mismatched number of expected and actual messages")
    for (i in expected.indices) {
      assertEquals(expected[i], actual[i].trim())
    }
  }

  protected fun thenTheFollowingRequestWasMade(expected: RequestPatternBuilder) =
      verify(expected)

  protected fun thenNoRequestsWereMadeForUrl(expectedUrl: String) =
      verify(exactly(0), anyRequestedFor(urlPathEqualTo(expectedUrl)))

  private fun activeProperties() =
      readYaml(ACTIVE_PROPERTIES_FILE, Properties::class.java)

  private fun resetOutStream() {
    outContent = ByteArrayOutputStream()
    System.setOut(PrintStream(outContent))
  }
}

fun getEchoMessage(year: Int, day: Int, level: Int) =
    "You are on year $year day $day level $level"
