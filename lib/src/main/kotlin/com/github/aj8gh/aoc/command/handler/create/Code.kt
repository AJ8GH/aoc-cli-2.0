package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.io.*
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import java.io.File

fun code() {
  createSourceDirsIfNotExists()
  generate(mainFile(), mainTemplateFile())
  generate(testFile(), testTemplateFile())
}

private fun generate(file: File, template: File) {
  if (!file.exists()) write(
    file, template.readText()
      .replace("\${YEAR}", "${year()}")
      .replace("\${DAY}", "${day()}")
  )
}
