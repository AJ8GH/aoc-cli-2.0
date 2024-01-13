package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import org.junit.jupiter.api.Test

private const val EXPECTED_CODE_DIR = "${AOC_HOME}code/"

class CodeKtTest : BaseTest() {

  @Test
  fun createCode() {
    givenCodeFilesDoNotExistForToday()

    whenCreateCodeIsCalled()

    thenMainFileIsCreatedAsExpected(expectedMainFile())
    thenTestFileIsCreatedAsExpected(expectedTestFile())
  }

  private fun expectedMainFile() = read("${expectedCodeDir()}main.txt")

  private fun expectedTestFile() = read("${expectedCodeDir()}test.txt")

  private fun expectedCodeDir() = "${EXPECTED_CODE_DIR}y${year()}/d${day()}/"
}
