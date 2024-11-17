package io.github.aj8gh.aoc.io

import java.io.File

class DirCreator {

  fun mkdirs(file: File) = file.parentFile.let {
    if (!it.exists()) {
//      log?.warn(this, "Creating directory ${it.absolutePath}")
      it.mkdirs()
    }
  }
}
