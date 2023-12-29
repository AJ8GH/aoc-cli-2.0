package com.github.aj8gh.aoc.io

import com.github.aj8gh.aoc.properties.Properties
import com.github.aj8gh.aoc.properties.getActivePropertiesFile
import java.io.File

fun write(properties: Properties) = mapper.writeValue(getFile(), properties)

private fun currentConfigFileName() = getActivePropertiesFile()

private fun getFile() = File(currentConfigFileName())
