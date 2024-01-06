package com.github.aj8gh.aoc.properties

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write

private const val AOC_PROPERTIES_FILE = "aoc.yaml"
private val AOC_HOME = "${System.getProperty("user.home")}/.config/.aoc/"

private var aocProperties: AocProperties? = null
private var properties: Properties? = null

var homeOverride: String? = null
var projectOverride: String? = null

fun activeProperties() = properties ?: readAndSetActiveProperties()
fun current() = activeProperties().current
fun files() = activeProperties().files
fun aocProperties() = aocProperties ?: readAndSetAocProperties()
fun aocPropertiesFile() = "${aocHome()}${AOC_PROPERTIES_FILE}"
fun activePropertiesFile() = "${aocHome()}${aocProperties().active}"
fun aocHome() = homeOverride ?: AOC_HOME
fun project() = projectOverride ?: files().project
fun year() = current().year
fun day() = current().day
fun level() = current().level

fun updateProperties(newProperties: Properties) {
  properties = newProperties
  write(newProperties)
}

private fun readAndSetAocProperties(): AocProperties {
  aocProperties = readYaml(aocPropertiesFile(), AocProperties::class.java)
  return aocProperties!!
}

private fun readAndSetActiveProperties(): Properties {
  properties = readYaml(activePropertiesFile(), Properties::class.java)
  return properties!!
}
