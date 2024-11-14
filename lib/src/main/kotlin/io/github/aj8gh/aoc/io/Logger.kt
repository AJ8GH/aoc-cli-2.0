package io.github.aj8gh.aoc.io

import io.github.aj8gh.aoc.io.Logger.Level.*
import java.io.PrintWriter
import java.io.StringWriter
import java.time.Clock
import java.time.Instant

class Logger(
  private val writer: Writer,
  private val files: FileManager,
  private val console: Console,
  private val clock: Clock,
  private val consoleLevel: Level = ERROR,
  private val fileLevel: Level = DEBUG,
  private val className: String? = Logger::class.simpleName,
) {

  fun trace(message: String) = logToConsoleAndFile(TRACE, message)
  fun debug(message: String) = logToConsoleAndFile(DEBUG, message)
  fun info(message: String) = logToConsoleAndFile(INFO, message)
  fun warn(message: String) = logToConsoleAndFile(WARN, message)

  fun error(e: Exception, name: String? = className) {
    val timestamp = clock.instant()
    logToConsole(ERROR, format(ERROR, timestamp, consoleErrorMessage(e.message), name))
    logToFile(ERROR, format(ERROR, timestamp, stackTrace(e), name))
  }

  private fun isLevelEnabled(level: Level, messageLevel: Level) = level.value <= messageLevel.value

  private fun consoleErrorMessage(message: String?) =
    "Error executing command: \"$message\". View stack trace in log file: ${files.logFile()}"

  private fun logToConsoleAndFile(level: Level, message: String) {
    val timestamp = clock.instant()
    logToConsole(level, format(level, timestamp, message))
    logToFile(level, format(level, timestamp, message))
  }

  private fun logToConsole(level: Level, message: String) {
    if (!isLevelEnabled(level = consoleLevel, messageLevel = level)) return
    console.echo(message)
  }

  private fun logToFile(level: Level, message: String) {
    if (!isLevelEnabled(level = fileLevel, messageLevel = level)) return
    files.createLogDirIfNotExists()
    writer.append(files.logFile(), message)
  }

  private fun format(level: Level, timestamp: Instant, message: String, name: String? = className) =
    "$level - $timestamp - $name - $message\n"

  private fun stackTrace(e: Exception): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    e.printStackTrace(pw)
    return sw.toString()
  }

  enum class Level(val value: Int) {
    TRACE(0),
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4),
    NONE(5),
  }

  fun of(className: String?) = Logger(
    writer = writer,
    files = files,
    console = console,
    clock = clock,
    consoleLevel = consoleLevel,
    fileLevel = fileLevel,
    className = className,
  )
}
