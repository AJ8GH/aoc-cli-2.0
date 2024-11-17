package io.github.aj8gh.aoc.io

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

class Writer(private val mapper: ObjectMapper) {

  fun <T> write(file: File, t: T) = mapper.writeValue(file, t)
  fun write(file: File, content: String) = file.writeText(content)
  fun append(file: File, content: String) = file.appendText(content)
}
