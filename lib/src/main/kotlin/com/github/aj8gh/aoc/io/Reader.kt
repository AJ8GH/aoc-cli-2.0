package com.github.aj8gh.aoc.io

import io.github.oshai.kotlinlogging.KotlinLogging

private const val AOC_SESSION = "/session.txt"

private val logger = KotlinLogging.logger {}

fun <T> readYaml(name: String, type: Class<T>): T {
  logger.info { "reading $name for type $type" }
  return mapper.readValue(read(name), type)
}

fun readSession() = read(AOC_SESSION)!!.readText()

private fun read(name: String) = object {}.javaClass.getResource(name)
