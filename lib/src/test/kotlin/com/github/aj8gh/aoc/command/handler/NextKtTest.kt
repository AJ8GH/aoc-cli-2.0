package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.properties.*
import com.github.aj8gh.aoc.util.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

private const val AOC_HOME = "src/test/resources/"
private const val ACTIVE_CONFIG_FILE = "${AOC_HOME}current-test.yaml"
private const val AOC_CONFIG_FILE = "aoc-test.yaml"
private const val TEMPLATE_CONFIG_FILE = "${AOC_HOME}templates/current-test-template.yaml"
private const val SECOND_YEAR = FIRST_YEAR + 1
private const val SECOND_DAY = FIRST_DAY + 1

private val CLOCK = Clock.fixed(
  Instant.parse("2023-12-26T00:00:00Z"),
  ZoneId.systemDefault()
)

private val LATEST_YEAR = latestYear(CLOCK)

class NextKtTest {

  @BeforeTest
  fun setUp() {
    aocOverride = "${AOC_HOME}${AOC_CONFIG_FILE}"
    homeOverride = AOC_HOME
    getAocProperties()
  }

  @AfterTest
  fun tearDown() {
    updateProperties(readYaml(TEMPLATE_CONFIG_FILE, Properties::class.java))
  }

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun nextTest(
    next: Boolean,
    year: Int,
    day: Int,
    level: Int,
    expectedYear: Int,
    expectedDay: Int,
    expectedLevel: Int,
  ) {

    // Given
    set(year = year, day = day, level = level)

    // When
    next(next)

    // Then
    val actual = readYaml(ACTIVE_CONFIG_FILE, Properties::class.java)
    assertEquals(expectedYear, actual.current.year)
    assertEquals(expectedDay, actual.current.day)
    assertEquals(expectedLevel, actual.current.level)
  }

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(
        true,
        FIRST_YEAR, FIRST_DAY, LEVEL_1,
        FIRST_YEAR, FIRST_DAY, LEVEL_2
      ),
      Arguments.of(
        false,
        FIRST_YEAR, FIRST_DAY, LEVEL_1,
        FIRST_YEAR, FIRST_DAY, LEVEL_1
      ),
      Arguments.of(
        true,
        FIRST_YEAR, FIRST_DAY, LEVEL_2,
        FIRST_YEAR, SECOND_DAY, LEVEL_1
      ),
      Arguments.of(
        true,
        FIRST_YEAR, LAST_DAY, LEVEL_2,
        SECOND_YEAR, FIRST_DAY, LEVEL_1
      ),
      Arguments.of(
        true,
        LATEST_YEAR, LAST_DAY, LEVEL_2,
        LATEST_YEAR, LAST_DAY, LEVEL_2
      ),
    )
  }
}
