package com.github.aj8gh.aoc.command.handler

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.properties.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

const val AOC_HOME = "src/test/resources/"
const val TEMPLATE_CONFIG_FILE = "${AOC_HOME}templates/current-test-template.yaml"
const val AOC_CONFIG_FILE = "aoc-test.yaml"

open class BaseTest {
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
}
