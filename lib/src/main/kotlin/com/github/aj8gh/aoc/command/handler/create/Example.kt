package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.io.createResourcesDirIfNotExists
import com.github.aj8gh.aoc.io.exampleFile
import com.github.aj8gh.aoc.io.readmeCacheFile
import com.github.aj8gh.aoc.io.write

private const val EXAMPLE_IDENTIFIER = "for example"
private const val LARGER_EXAMPLE_IDENTIFIER = "consider this larger example"
private const val OPENING_TAG = "<pre><code>"
private const val CLOSING_TAG = "</code></pre>"

private var identifier = EXAMPLE_IDENTIFIER

fun example() {
  val html = readmeCacheFile().readText()
  val lines = html.lines()

  val lower = html.lowercase()
  identifier = if (lower.contains(LARGER_EXAMPLE_IDENTIFIER)) {
    LARGER_EXAMPLE_IDENTIFIER
  } else if (lower.contains(EXAMPLE_IDENTIFIER)) {
    EXAMPLE_IDENTIFIER
  } else {
    println("Example not found")
    return
  }

  val exampleBuilder: StringBuilder = StringBuilder()
  var foundExample = false
  var completedExample = false
  for (i in lines.indices) {

    if (foundExample && !completedExample) {
      if (lines[i].contains(OPENING_TAG)) {
        val line = lines[i].substringAfter(OPENING_TAG)
        if (line.contains(CLOSING_TAG)) {
          exampleBuilder.appendLine(line.substringBefore(CLOSING_TAG))
          completedExample = true
        } else {
          exampleBuilder.appendLine(line)
        }
      } else if (lines[i].contains(CLOSING_TAG)) {
        exampleBuilder.append(lines[i].substringBefore(CLOSING_TAG))
        completedExample = true
      } else {
        exampleBuilder.appendLine(lines[i])
      }
    }
    if (lines[i].lowercase().contains(identifier))
      foundExample = true
  }

  val example = exampleBuilder.toString()
    .replace("&lt;", "<")
    .replace("&gt;", ">")
    .replace("<em>", "")
    .replace("</em>", "")

  createResourcesDirIfNotExists()
  write(exampleFile(), example)
}
