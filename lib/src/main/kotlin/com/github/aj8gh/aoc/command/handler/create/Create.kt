package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.properties.current
import com.github.aj8gh.aoc.properties.files
import com.github.aj8gh.aoc.properties.project
import java.io.File

fun create(create: Boolean) = create().takeIf { create }

fun create() {
  input()
  readme()
  example()
  code()
}

fun resourcesDir() = File(buildString {
  append(project())
  append(files().modulePrefix)
  append("${current().year}/")
  append(files().resources)
  append(files().dayPrefix)
  append("${current().day}/")
})
