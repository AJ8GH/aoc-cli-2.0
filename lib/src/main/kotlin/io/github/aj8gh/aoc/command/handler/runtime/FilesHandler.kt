package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.io.aocHomeDir
import io.github.aj8gh.aoc.properties.activeProfile

class FilesHandler(private val executor: Executor) {
  fun files(flag: Boolean) {
    if (!flag) return
    executor.exec(arrayOf(activeProfile().ide, aocHomeDir()))
  }
}
