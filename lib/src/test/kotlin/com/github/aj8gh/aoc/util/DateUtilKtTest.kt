package com.github.aj8gh.aoc.util

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import kotlin.test.assertEquals

class DateUtilKtTest {

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun test(date: String, expected: Int) {
    val clock = Clock.fixed(Instant.parse(date), ZoneId.systemDefault())
    val actual = latestYear(clock)
    assertEquals(expected, actual)
  }


  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
        Arguments.of("2086-12-01T00:00:00Z", 86),
        Arguments.of("2086-11-30T23:59:59Z", 85),
        Arguments.of("2023-07-30T23:59:59Z", 22),
        Arguments.of("2023-12-26T23:59:59Z", 23),
        Arguments.of("2024-01-01T00:00:00Z", 23),
    )
  }
}
