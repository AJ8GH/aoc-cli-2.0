package com.github.aj8gh.aoc.config

import com.github.aj8gh.aoc.io.readYaml
import com.github.aj8gh.aoc.io.write

private const val AOC_CONFIG_FILE = "/Users/adamjonas/.config/.aoc/aoc.yaml"

private var aocProperties: AocProperties? = null
private var properties: Properties? = null

var aocConfigFile = AOC_CONFIG_FILE

fun getCurrentProperties() = properties ?: readAndSetCurrentProperties()
fun getAocProperties() = aocProperties ?: readAndSetAocProperties()

fun updateProperties(newProperties: Properties) {
  properties = newProperties
  write(newProperties)
}

private fun readAndSetAocProperties(): AocProperties? {
  aocProperties = readYaml(
    aocConfigFile,
    AocProperties::class.java
  )
  return aocProperties
}

private fun readAndSetCurrentProperties(): Properties? {
  val name = getAocProperties()!!.current
  properties = readYaml(
    name,
    Properties::class.java
  )
  return properties
}
