package com.github.aj8gh.aoc.properties

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write

private const val AOC_PROPERTIES_FILE = "aoc.yaml"
private val AOC_HOME = "${System.getProperty("user.home")}/.config/.aoc/"

private var aocProperties: AocProperties? = null
private var properties: Properties? = null

var homeOverride: String? = null

fun getActiveProperties() = properties ?: readAndSetActiveProperties()
fun getCurrent() = getActiveProperties().current
fun getAocProperties() = aocProperties ?: readAndSetAocProperties()
fun getAocPropertiesFile() = "${getAocHome()}${AOC_PROPERTIES_FILE}"
fun getActivePropertiesFile() = "${getAocHome()}${getAocProperties().active}"
fun getAocHome() = homeOverride ?: AOC_HOME

fun updateProperties(newProperties: Properties) {
  properties = newProperties
  write(newProperties)
}

private fun readAndSetAocProperties(): AocProperties {
  aocProperties = readYaml(getAocPropertiesFile(), AocProperties::class.java)
  return aocProperties!!
}

private fun readAndSetActiveProperties(): Properties {
  properties = readYaml(getActivePropertiesFile(), Properties::class.java)
  return properties!!
}
