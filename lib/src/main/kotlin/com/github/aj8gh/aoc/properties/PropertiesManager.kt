package com.github.aj8gh.aoc.properties

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write

private const val AOC_PROPERTIES_FILE = "aoc.yaml"

private val aocHome = "${System.getProperty("user.home")}/.config/.aoc/"
private var aocProperties: AocProperties? = null
private var properties: Properties? = null

var aocOverride: String? = null
var homeOverride: String? = null

fun getActiveProperties() = properties ?: readAndSetActiveProperties()
fun getAocProperties() = aocProperties ?: readAndSetAocProperties()
fun getAocPropertiesFile() = aocOverride ?: "${aocHome}${AOC_PROPERTIES_FILE}"
fun getActivePropertiesFile() = "${homeOverride ?: aocHome}${getAocProperties().active}"

fun updateProperties(newProperties: Properties) {
  properties = newProperties
  write(newProperties)
}

private fun readAndSetAocProperties() = readYaml(
  getAocPropertiesFile(),
  AocProperties::class.java
)

private fun readAndSetActiveProperties() = readYaml(
  getActivePropertiesFile(),
  Properties::class.java
)
