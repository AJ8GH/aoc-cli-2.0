package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.io.*
import okhttp3.internal.toImmutableList
import java.util.regex.Pattern
import kotlin.math.sign

private const val EXAMPLE_IDENTIFIER = "for example"
private const val LARGER_EXAMPLE_IDENTIFIER = "consider this larger example"
private const val HERE_IS_EXAMPLE_IDENTIFIER = "here is an example"
private const val OPENING_TAG = "<pre><code>"
private const val CLOSING_TAG = "</code></pre>"
private const val OPENING_LIST = "<ul>"
private const val CLOSING_LIST = "</ul>"
private const val PART_2 = "--- Part Two ---"
private const val ENCODED_OPENING_BRACKET = "&lt;"
private const val ENCODED_CLOSING_BRACKET = "&gt;"
private const val OPENING_BRACKET = "<"
private const val CLOSING_BRACKET = ">"
private const val OPENING_EMPHASIS_TAG = "<em>"
private const val CLOSING_EMPHASIS_TAG = "</em>"
private const val EMPTY_STRING = ""
private const val LIST_EXAMPLE_PATTERN = "(.*<code>)(.*)(</code>.*)"

private var identifier = EXAMPLE_IDENTIFIER

fun example() {
  if (!readmeCacheFile().exists()) return
  val html = readmeCacheFile().readText()
  if (!findExampleIdentifier(html.lowercase())) return
  val allExamples = findExamples(html.lines(), false)
  var exampleNumber = 0

  for (examples in allExamples.first) {
    for (example in examples.second) {
      val total = allExamples.first.map { it.second }.flatten().size
      val exampleFile = if (total == 1) exampleFile() else exampleFile(exampleNumber)
      val sanitised = sanitiseExample(example)
      createResourcesDirIfNotExists()
      write(exampleFile, sanitised)
      exampleNumber++
    }
  }

  if (allExamples.second == allExamples.first) return

  exampleNumber = 0
  for (examples in allExamples.second) {
    for (example in examples.second) {
      val mapped = allExamples.second.map { it.second }
      val flattened = mapped.flatten()
      val total = flattened.size
      val exampleFile =
        if (total == 1) examplePart2File()
        else examplePart2File(exampleNumber)
      val sanitised = sanitiseExample(example)
      write(exampleFile, sanitised)
      exampleNumber++
    }
  }
}

private fun findExamples(lines: List<String>, part2: Boolean):
    Pair<List<Pair<String, List<String>>>, List<Pair<String, List<String>>>> {
  val exampleBuilder: StringBuilder = StringBuilder()
  var foundExample = false
  var checkedForListExample = false
  for (i in lines.indices) {
    val line = lines[i]
    if (foundExample) {

      if (!checkedForListExample && line.contains(OPENING_LIST)) {
        val examples = listExample(lines.subList(i, lines.size))
        if (examples.isNotEmpty()) {
          val remaining = lines.subList(i, lines.size)
          return if (!part2 && remaining.joinToString(" ").contains(PART_2))
            Pair(examples, findExamples(remaining, true).first)
          else
            Pair(examples, listOf())
        }
        checkedForListExample = true
      }
      if (line.contains(OPENING_TAG)) {
        val openingLine = line.substringAfter(OPENING_TAG)
        if (openingLine.contains(CLOSING_TAG)) {
          exampleBuilder.appendLine(openingLine.substringBefore(CLOSING_TAG))
          break
        } else {
          exampleBuilder.appendLine(openingLine)
        }
      } else if (line.contains(CLOSING_TAG)) {
        exampleBuilder.append(line.substringBefore(CLOSING_TAG))
        break
      } else {
        exampleBuilder.appendLine(line)
      }
    }
    if (line.lowercase().contains(identifier))
      foundExample = true
  }
  val examples = listOf(Pair("TODO", listOf(exampleBuilder.toString())))
  val i = lines.indexOfFirst { it.contains(PART_2) }
  return if (!part2 && i > 0)
    Pair(examples, findExamples(lines.subList(i, lines.size), true).first)
  else
    Pair(examples, listOf())
}

private fun findExampleIdentifier(html: String) = when {
  html.contains(LARGER_EXAMPLE_IDENTIFIER) -> {
    identifier = LARGER_EXAMPLE_IDENTIFIER
    true
  }

  html.contains(EXAMPLE_IDENTIFIER) -> {
    identifier = EXAMPLE_IDENTIFIER
    true
  }

  html.contains(HERE_IS_EXAMPLE_IDENTIFIER) -> {
    identifier = HERE_IS_EXAMPLE_IDENTIFIER
    true
  }

  else -> {
    println("Example not found")
    false
  }
}

private fun sanitiseExample(example: String) = example
  .replace(ENCODED_OPENING_BRACKET, OPENING_BRACKET)
  .replace(ENCODED_CLOSING_BRACKET, CLOSING_BRACKET)
  .replace(OPENING_EMPHASIS_TAG, EMPTY_STRING)
  .replace(CLOSING_EMPHASIS_TAG, EMPTY_STRING)

private fun listExample(lines: List<String>): List<Pair<String, List<String>>> {
  val allExamples = mutableListOf<Pair<String, List<String>>>()
  for (i in lines.indices) {
    val line = lines[i]
    if (line.contains(CLOSING_LIST)) break

    val words = line.split(" ").toMutableList()
    val examples = mutableListOf<String>()
    for (word in words) {
      val pattern = Pattern.compile(LIST_EXAMPLE_PATTERN)
      val matcher = pattern.matcher(word)
      if (matcher.matches()) {
        examples.addLast(matcher.group(2))
      }
    }
    if (examples.isNotEmpty()) {
      allExamples.addLast(Pair(examples.removeLast(), examples.toImmutableList()))
    }
  }

  return allExamples.toImmutableList()
}
