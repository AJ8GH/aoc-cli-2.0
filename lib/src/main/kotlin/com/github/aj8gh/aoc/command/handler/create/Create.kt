package com.github.aj8gh.aoc.command.handler.create

fun create(create: Boolean) = create().takeIf { create }

fun create() {
  input()
  readme()
  example()
  code()
}
