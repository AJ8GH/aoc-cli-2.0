package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.io.createResourcesDirIfNotExists
import com.github.aj8gh.aoc.io.exampleFile
import com.github.aj8gh.aoc.io.readmeCacheFile
import com.github.aj8gh.aoc.io.write

private const val EXAMPLE_IDENTIFIER = "for example"
private const val OPENING_TAG = "<pre><code>"
private const val CLOSING_TAG = "</code></pre>"

fun example() {
  val html = readmeCacheFile().readText()
  val lines = html.lines()

  val example: StringBuilder = StringBuilder()
  var foundExample = false
  var completedExample = false
  for (i in lines.indices) {

    if (foundExample && ! completedExample) {
      if (lines[i].contains(OPENING_TAG)) {
        example.appendLine(lines[i].substringAfter(OPENING_TAG))
      } else if (lines[i].contains(CLOSING_TAG)) {
        example.append(lines[i].substringBefore(CLOSING_TAG))
        completedExample = true
      } else {
        example.appendLine(lines[i])
      }
    }
    if (lines[i].lowercase().contains(EXAMPLE_IDENTIFIER)) foundExample = true
  }

  createResourcesDirIfNotExists()
  write(exampleFile(), example.toString())
}
