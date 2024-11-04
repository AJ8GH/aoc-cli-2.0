package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.files

fun open(runtime: Runtime) = runtime.exec(arrayOf(activeProfile().ide, files().projectHome))
