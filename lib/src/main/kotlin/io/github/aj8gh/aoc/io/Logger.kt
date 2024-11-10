package io.github.aj8gh.aoc.io

import java.io.PrintWriter
import java.io.StringWriter


class Logger(
  private val writer: Writer,
  private val files: FileManager,
) {

  fun error(e: Exception) {
    println(consoleErrorMessage(e.message))
    files.createLogDirIfNotExists()
    writer.write(files.errorLogFile(), stackTrace(e))
  }

  private fun consoleErrorMessage(message: String?) =
    "Error executing command: \"$message\". View stack trace in log file: ${files.errorLogFile()}"

  private fun stackTrace(e: Exception) {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    return e.printStackTrace(pw)
  }
}
