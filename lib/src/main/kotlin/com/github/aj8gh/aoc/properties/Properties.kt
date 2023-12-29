package com.github.aj8gh.aoc.properties

data class Properties(
  val name: String,
  val language: String,
  val ide: String,
  val files: FileProperties,
  val current: CurrentProperties,
) {

  data class CurrentProperties(
    var year: Int,
    var day: Int,
    var level: Int,
  )

  data class FileProperties(
    val project: String,
    val main: String,
    val test: String,
    val resources: String,
    val modulePrefix: String,
    val yearPrefix: String,
    val dayPrefix: String,
  )
}
