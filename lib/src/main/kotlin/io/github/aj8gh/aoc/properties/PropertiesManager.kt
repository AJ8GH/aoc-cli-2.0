package io.github.aj8gh.aoc.properties

import io.github.aj8gh.aoc.io.activePropertiesFile
import io.github.aj8gh.aoc.io.aocPropertiesFile
import io.github.aj8gh.aoc.io.readYaml
import io.github.aj8gh.aoc.io.write

private var aocProperties: AocProperties? = null
private var properties: Properties? = null

fun aocProperties() = aocProperties ?: readAndSetAocProperties()
fun forceLoadAocProperties() = readAndSetAocProperties()
fun activeProperties() = properties ?: readAndSetActiveProperties()
fun current() = activeProperties().current
fun files() = activeProperties().files
fun project() = files().project
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
