package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.properties.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

private const val AOC_HOME = "src/test/resources/"
private const val ACTIVE_CONFIG_FILE = "${AOC_HOME}current-test.yaml"
private const val AOC_CONFIG_FILE = "aoc-test.yaml"
private const val TEMPLATE_CONFIG_FILE = "${AOC_HOME}templates/current-test-template.yaml"
private const val DEFAULT_YEAR = 15
private const val DEFAULT_DAY = 1
private const val DEFAULT_LEVEL = 1

class SetKtTest {

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
  fun setTest(year: Int?, day: Int?, level: Int?) {
    // Given
    val expectedYear = year ?: DEFAULT_YEAR
    val expectedDay = day ?: DEFAULT_DAY
    val expectedLevel = level ?: DEFAULT_LEVEL

    // When
    set(year = year, day = day, level = level)

    // Then
    val actual = readYaml(ACTIVE_CONFIG_FILE, Properties::class.java)
    assertEquals(expectedYear, actual.current.year)
    assertEquals(expectedDay, actual.current.day)
    assertEquals(expectedLevel, actual.current.level)
  }

  companion object {
    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of(16, 2, 2),
      Arguments.of(null, null, null),
      Arguments.of(null, 2, 2),
      Arguments.of(16, null, 2),
      Arguments.of(16, 2, null),
      Arguments.of(16, null, null),
      Arguments.of(null, null, 2),
      Arguments.of(null, 2, null),
    )
  }
}
