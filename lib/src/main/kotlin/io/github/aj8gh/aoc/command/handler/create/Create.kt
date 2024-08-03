package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.command.handler.create.example.example

fun create(create: Boolean) = if (create) create() else Unit

fun create() {
  input()
  readme()
  example()
  code()
}
