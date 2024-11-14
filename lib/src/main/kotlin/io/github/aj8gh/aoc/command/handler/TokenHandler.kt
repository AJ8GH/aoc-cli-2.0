package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Writer
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager

class TokenHandler(
  private val props: PropertiesManager,
  private val propsFiles: PropertyFileManager,
  private val writer: Writer,
  private val log: Logger,
) {
  fun handle(token: String?) {
    token?.let {
      val newProps = props.aocProperties().copy(session = it)
      writer.write(propsFiles.aocPropertiesFile(), newProps)
      props.forceLoadAocProperties()
      log.info("AoC properties file ${propsFiles.aocPropertiesFile()} updated with new token")
    }
  }
}
