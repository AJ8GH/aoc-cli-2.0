package io.github.aj8gh.aoc

import io.github.aj8gh.aoc.command.Aoc
import io.github.aj8gh.aoc.context.ContextManager

fun run(args: Array<String>) = Aoc(ContextManager().context()).main(args)
