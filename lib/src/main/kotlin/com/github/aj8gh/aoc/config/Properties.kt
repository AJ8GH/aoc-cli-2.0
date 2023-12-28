package com.github.aj8gh.aoc.config

data class Properties(
  var aocProperties: AocProperties?,
  val language: String,
  val ide: String,
  val files: FileProperties,
  val current: CurrentProperties,
  val session: String?,
)
