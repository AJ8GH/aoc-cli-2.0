package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Writer
import io.github.aj8gh.aoc.properties.PropertiesManager

class TokenHandler(
  private val props: PropertiesManager,
  private val writer: Writer,
  private val files: FileManager,
  private val log: Logger,
) {

  fun handle(token: String?) {
    token?.let {
      val newProps = props.aocProperties().copy(session = it)
      writer.write(files.aocPropertiesFile(), newProps)
      props.forceLoadAocProperties()
      log.info("AoC properties file ${files.aocPropertiesFile().absolutePath} updated with new token")
    }
  }
}
