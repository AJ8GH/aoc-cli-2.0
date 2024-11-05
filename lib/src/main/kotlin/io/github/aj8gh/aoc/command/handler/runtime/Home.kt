package io.github.aj8gh.aoc.command.handler.runtime

import io.github.aj8gh.aoc.io.aocHomeDir
import io.github.aj8gh.aoc.properties.activeProfile

fun home(runtime: Runtime) = exec(runtime, arrayOf(activeProfile().ide, aocHomeDir()))
