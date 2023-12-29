package com.github.aj8gh.aoc.config

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write

private val HOME = System.getProperty("user.home")
private val AOC_PROPERTIES_HOME = "$HOME/.config/.aoc/"
private val AOC_PROPERTIES_FILE = "$AOC_PROPERTIES_HOME/aoc.yaml"

private var aocProperties: AocProperties? = null
private var properties: Properties? = null

var aocPropertiesFile = AOC_PROPERTIES_FILE

fun getCurrentProperties() = properties ?: readAndSetCurrentProperties()

fun getAocProperties() = aocProperties ?: readAndSetAocProperties()

fun getCurrentPropertiesFile() = "$AOC_PROPERTIES_HOME${getAocProperties().current}"

fun updateProperties(newProperties: Properties) {
  properties = newProperties
  write(newProperties)
}

private fun readAndSetAocProperties() = readYaml(
  aocPropertiesFile,
  AocProperties::class.java
)

private fun readAndSetCurrentProperties() = readYaml(
  getCurrentPropertiesFile(),
  Properties::class.java
)
