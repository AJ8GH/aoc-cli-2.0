package io.github.aj8gh.aoc.io

import com.github.ajalt.mordant.table.Table
import com.github.ajalt.mordant.terminal.Terminal

class Console(
  private val terminal: Terminal
) {

  fun echo(message: String) = terminal.println(message)

  fun echo(table: Table) = terminal.println(table)

  fun printLn(message: String) = println(message)
}
