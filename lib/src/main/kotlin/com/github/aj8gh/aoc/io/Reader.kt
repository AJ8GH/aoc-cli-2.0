package com.github.aj8gh.aoc.io

import java.io.File

fun <T> readYaml(name: File, type: Class<T>): T =
  mapper.readValue(read(name), type)

fun read(file: File) = file.readText(Charsets.UTF_8)

fun read(name: String) = File(name).readText(Charsets.UTF_8)
