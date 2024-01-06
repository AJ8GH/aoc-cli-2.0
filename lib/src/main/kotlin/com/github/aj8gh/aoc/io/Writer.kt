package com.github.aj8gh.aoc.io

import com.github.aj8gh.aoc.properties.Properties
import com.github.aj8gh.aoc.properties.activePropertiesFile
import java.io.File

fun write(properties: Properties) = mapper.writeValue(getFile(), properties)

fun <T> write(file: File, t: T) = mapper.writeValue(file, t)

fun write(file: File, content: String) = file.writeText(content)

private fun getFile() = File(activePropertiesFile())
