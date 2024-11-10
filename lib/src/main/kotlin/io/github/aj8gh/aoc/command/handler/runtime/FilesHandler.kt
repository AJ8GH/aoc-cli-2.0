package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager

class FilesHandler(
  private val props: PropertiesManager,
  private val propsFiles: PropertyFileManager,
  private val executor: Executor,
) {

  fun handle(flag: Boolean) {
    if (!flag) return
    executor.exec(arrayOf(props.activeProfile().ide, propsFiles.aocHomeDir()))
  }
}
