package io.github.aj8gh.aoc.properties

import io.github.aj8gh.aoc.io.EXTENSION
import io.github.aj8gh.aoc.io.Reader
import java.io.File

private const val AOC_PROPERTIES_FILE_NAME = "aoc.yaml"

class PropertyFileManager(
  private val reader: Reader,
) {

  private val aocHome = "${System.getProperty("user.home")}/.config/.aoc/"

  var homeOverride: String? = null

  fun aocHomeDir() = homeOverride ?: aocHome

  fun aocPropertiesFile() = File("${aocHomeDir()}$AOC_PROPERTIES_FILE_NAME")

  fun activeProfileFile(): File {
    val aocProps = reader.readYaml(File("${aocHomeDir()}$AOC_PROPERTIES_FILE_NAME"), AocProperties::class.java)
    return File("${aocHomeDir()}${aocProps.active}$EXTENSION")
  }
}
