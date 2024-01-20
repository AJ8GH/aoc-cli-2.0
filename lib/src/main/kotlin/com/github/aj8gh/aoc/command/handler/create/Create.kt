package com.github.aj8gh.aoc.command.handler.create

fun create(create: Boolean) = if (create) create() else Unit

fun create() {
  input()
  readme()
  example()
  code()
}
