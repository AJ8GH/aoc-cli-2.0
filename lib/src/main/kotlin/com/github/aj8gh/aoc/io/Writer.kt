package com.github.aj8gh.aoc.io

import com.github.aj8gh.aoc.config.Properties
import com.github.aj8gh.aoc.config.getCurrentPropertiesFile
import java.io.File

fun write(properties: Properties) = mapper.writeValue(getFile(), properties)

private fun currentConfigFileName() = getCurrentPropertiesFile()

private fun getFile() = File(currentConfigFileName())
