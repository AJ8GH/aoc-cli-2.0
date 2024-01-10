package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.givenCodeFilesDoNotExistForToday
import com.github.aj8gh.aoc.thenCodeFilesExistForToday
import com.github.aj8gh.aoc.whenCreateCodeIsCalled
import org.junit.jupiter.api.Test

class CodeKtTest {

  @Test
  fun createCode() {
    givenCodeFilesDoNotExistForToday()

    whenCreateCodeIsCalled()

    thenCodeFilesExistForToday()
  }
}
