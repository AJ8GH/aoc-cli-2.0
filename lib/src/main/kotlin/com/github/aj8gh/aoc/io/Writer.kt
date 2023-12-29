package com.github.aj8gh.aoc.io

import com.github.aj8gh.aoc.config.Properties
import com.github.aj8gh.aoc.config.getAocProperties
import com.github.aj8gh.aoc.config.getCurrentProperties
import java.io.File

fun write(properties: Properties) = mapper.writeValue(getFile(), properties)

private fun currentConfigFileName() = getAocProperties()!!.current

private fun getFile() = File(object {}.javaClass
  .getResource(currentConfigFileName())!!
  .toURI())
