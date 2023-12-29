package com.github.aj8gh.aoc.config

data class Properties(
  val name: String,
  val language: String,
  val ide: String,
  val files: FileProperties,
  val current: CurrentProperties,
)
