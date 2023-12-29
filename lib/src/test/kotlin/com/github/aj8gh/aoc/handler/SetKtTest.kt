package com.github.aj8gh.aoc.handler

import com.github.aj8gh.aoc.config.Properties
import com.github.aj8gh.aoc.config.aocConfigFile
import com.github.aj8gh.aoc.io.readYaml
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val CURRENT_CONFIG_FILE = "/config/current-test.yaml"

class SetKtTest {

  @BeforeEach
  fun setUp() {
    aocConfigFile = "/aoc-test.yaml"
  }

  @Test
  fun set() {
    // Given
    val year = 16
    val day = 2
    val level = 2

    // When
    set(year = year, day = day, level = level)

    // Then
    val actual = readYaml(CURRENT_CONFIG_FILE, Properties::class.java)
    assertEquals(actual.current.year, year)
    assertEquals(actual.current.day, day)
    assertEquals(actual.current.level, level)
  }
}
