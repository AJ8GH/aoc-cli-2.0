package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.properties.activeProfile
import io.github.aj8gh.aoc.properties.files

fun open(runtime: Runtime) = exec(runtime, arrayOf(activeProfile().ide, files().projectHome))
