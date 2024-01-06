package com.github.aj8gh.aoc.io

import java.io.File

fun <T> readYaml(name: String, type: Class<T>): T =
  mapper.readValue(read(name), type)

fun read(name: String) = File(name).readText(Charsets.UTF_8)
