package com.github.aj8gh.aoc.command.handler

fun create(create: Boolean) = create().takeIf { create }

fun create() {
  createInput()
  createReadme()
  createExamples()
  createCode()
}

private fun createInput() = Unit

private fun createReadme() = Unit
private fun createExamples() = Unit
private fun createCode() = Unit
