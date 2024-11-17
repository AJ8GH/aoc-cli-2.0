package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.properties.PropertiesManager

class FilesHandler(
  private val props: PropertiesManager,
  private val executor: Executor,
  private val aocHome: String,
) {

  fun handle(flag: Boolean) {
    if (!flag) return
    executor.exec(arrayOf(props.activeProfile().ide, aocHome))
  }
}
