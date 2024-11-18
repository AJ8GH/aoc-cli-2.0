package io.github.aj8gh.aoc.context

import io.github.aj8gh.aoc.io.Reader

class PropertiesLoader(
  private val reader: Reader,
) {

  fun load(file: String) = reader.loadProperties(file, ApplicationProperties::class.java)
}
