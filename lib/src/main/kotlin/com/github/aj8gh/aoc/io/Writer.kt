package com.github.aj8gh.aoc.io

import com.github.aj8gh.aoc.config.Properties
import com.github.aj8gh.aoc.config.getAocProperties
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

fun write(properties: Properties) = mapper.writeValue(getFile(), properties)

private fun currentConfigFileName() = getAocProperties()!!.current

private fun getFile() = File(currentConfigFileName())
