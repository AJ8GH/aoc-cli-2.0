package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.http.getInput
import com.github.aj8gh.aoc.io.write
import com.github.aj8gh.aoc.properties.current
import com.github.aj8gh.aoc.properties.files
import com.github.aj8gh.aoc.properties.project
import java.io.File

private const val INPUT_FILE_NAME = "input.txt"

fun input() = write(getAndCreateFile(), getInput())

fun getInputDir() = File(buildString {
  append(project())
  append(files().modulePrefix)
  append("${current().year}/")
  append(files().resources)
  append(files().yearPrefix)
  append("${current().year}/")
  append(files().dayPrefix)
  append("${current().day}/")
})

fun getInputFile() = File("${getInputDir()}/$INPUT_FILE_NAME")

private fun getAndCreateFile(): File {
  val dir = getInputDir()
  val file = getInputFile()
  if (!dir.exists()) dir.mkdirs()
  if (!file.exists()) file.createNewFile()
  return file
}
