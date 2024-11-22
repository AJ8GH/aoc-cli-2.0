package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.io.*

private const val EXAMPLE_IDENTIFIER = "for example"
private const val LARGER_EXAMPLE_IDENTIFIER = "consider this larger example"
private const val HERE_IS_EXAMPLE_IDENTIFIER = "here is an example"
private const val OPENING_TAG = "<pre><code>"
private const val CLOSING_TAG = "</code></pre>"
private const val ENCODED_OPENING_BRACKET = "&lt;"
private const val ENCODED_CLOSING_BRACKET = "&gt;"
private const val OPENING_BRACKET = "<"
private const val CLOSING_BRACKET = ">"
private const val OPENING_EMPHASIS_TAG = "<em>"
private const val CLOSING_EMPHASIS_TAG = "</em>"
private const val EMPTY_STRING = ""

private var identifier = EXAMPLE_IDENTIFIER

class ExampleCreator(
  private val files: FileManager,
  private val dirCreator: DirCreator,
  private val writer: Writer,
  private val console: Console,
  private val log: Logger,
) {

  fun create() {
    if (!files.readmeCacheFile().exists()) return
    val html = files.readmeCacheFile().readText()
    if (!findExampleIdentifier(html.lowercase())) return
    val example = sanitiseExample(buildExample(html.lines()))
    dirCreator.mkdirs(files.exampleFile())
    log.info("Writing to example file ${files.exampleFile().absolutePath}")
    writer.write(files.exampleFile(), example)
  }

  private fun buildExample(lines: List<String>): String {
    val exampleBuilder: StringBuilder = StringBuilder()
    var foundExample = false
    for (line in lines) {

      if (foundExample) {
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
    return exampleBuilder.toString()
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
      console.echo("Example not found")
      false
    }
  }

  private fun sanitiseExample(example: String) = example
    .replace(ENCODED_OPENING_BRACKET, OPENING_BRACKET)
    .replace(ENCODED_CLOSING_BRACKET, CLOSING_BRACKET)
    .replace(OPENING_EMPHASIS_TAG, EMPTY_STRING)
    .replace(CLOSING_EMPHASIS_TAG, EMPTY_STRING)
}
