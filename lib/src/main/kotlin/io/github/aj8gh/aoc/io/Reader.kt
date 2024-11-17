package io.github.aj8gh.aoc.io

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

class Reader(private val mapper: ObjectMapper) {

  fun <T> readYaml(name: File, type: Class<T>): T = mapper.readValue(read(name), type)
  fun read(file: File) = file.readText(Charsets.UTF_8)
  fun read(name: String) = File(name).readText(Charsets.UTF_8)
  fun <T> loadProperties(name: String, type: Class<T>): T = mapper.readValue(
    this::class.java.classLoader.getResource(name),
    type,
  )
}
