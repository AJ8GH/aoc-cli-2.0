package io.github.aj8gh.aoc.io

import java.io.File

class DirCreator(private val log: Logger? = null) {

  fun mkdirs(file: File) = file.parentFile.let {
    if (!it.exists()) {
      log?.warn("Creating directory ${it.absolutePath}")
      it.mkdirs()
    }
  }
}
