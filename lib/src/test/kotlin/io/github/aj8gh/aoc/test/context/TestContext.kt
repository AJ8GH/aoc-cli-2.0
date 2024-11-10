package io.github.aj8gh.aoc.test.context

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.Runner
import io.github.aj8gh.aoc.context.ContextManager
import io.mockk.mockk

val CONTEXT = ContextManager().context(mockk<Terminal>(), mockk<Runtime>())
val RUNNER = Runner(context = CONTEXT)

val FILES = CONTEXT.manager.file
val PROPS_FILES = CONTEXT.manager.propsFiles
val PROPS = CONTEXT.manager.props

val READER = CONTEXT.io.reader
val WRITER = CONTEXT.io.writer
