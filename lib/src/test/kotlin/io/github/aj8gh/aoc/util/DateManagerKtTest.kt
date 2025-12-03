package io.github.aj8gh.aoc.util

import io.github.aj8gh.aoc.context.DateManager
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import kotlin.test.assertEquals

class DateManagerKtTest {

  @ParameterizedTest
  @MethodSource("latestYearSource")
  fun latestYear(date: String, expected: Int) {
    val clock = Clock.fixed(Instant.parse(date), ZoneId.systemDefault())
    val subject = DateManager(clock)
    val actual = subject.latestYear()
    assertEquals(expected, actual)
  }

  @ParameterizedTest
  @MethodSource("isPuzzleAvailableSource")
  fun isPuzzleAvailable(date: String, year: Int, day: Int, expected: Boolean) {
    val clock = Clock.fixed(Instant.parse(date), ZoneId.systemDefault())
    val subject = DateManager(clock)
    val actual = subject.isPuzzleAvailable(year, day)
    assertEquals(expected, actual)
  }

  companion object {
    @JvmStatic
    private fun latestYearSource() = listOf(
      Arguments.of("2086-12-01T00:00:00Z", 86),
      Arguments.of("2086-11-30T23:59:59Z", 85),
      Arguments.of("2023-07-30T23:59:59Z", 22),
      Arguments.of("2023-12-26T23:59:59Z", 23),
      Arguments.of("2024-01-01T00:00:00Z", 23),
    )

    @JvmStatic
    private fun isPuzzleAvailableSource() = listOf(
      Arguments.of("2086-12-01T00:00:00Z", 86, 1, true),
      Arguments.of("2086-11-30T23:59:59Z", 85, 12, true),
      Arguments.of("2086-11-30T23:59:59Z", 85, 13, false),
      Arguments.of("2023-07-30T23:59:59Z", 22, 25, true),
      Arguments.of("2023-12-10T23:59:59Z", 23, 11, false),
      Arguments.of("2023-12-26T23:59:59Z", 23, 25, true),
      Arguments.of("2024-01-01T00:00:00Z", 23, 25, true),
      Arguments.of("2025-12-03T08:00:00Z", 25, 3, true),
      Arguments.of("2025-12-03T08:00:00Z", 25, 4, false),
      Arguments.of("2025-12-13T08:00:00Z", 25, 12, true),
      Arguments.of("2026-01-13T08:00:00Z", 25, 12, true),
    )
  }
}
