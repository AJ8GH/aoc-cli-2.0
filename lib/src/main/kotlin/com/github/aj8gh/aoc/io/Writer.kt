package com.github.aj8gh.aoc.io

import com.github.aj8gh.aoc.properties.Properties
import java.io.File

fun write(properties: Properties) = mapper.writeValue(activePropertiesFile(), properties)

fun <T> write(file: File, t: T) = mapper.writeValue(file, t)

fun write(file: File, content: String) = file.writeText(content)
