package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.files

class OpenHandler(private val executor: Executor) {
  fun handle(flag: Boolean) {
    if (!flag) return
    executor.exec(arrayOf(activeProfile().ide, files().projectHome))
  }
}
