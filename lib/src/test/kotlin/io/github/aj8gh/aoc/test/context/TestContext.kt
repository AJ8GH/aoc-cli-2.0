package io.github.aj8gh.aoc.test.context

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.context.ContextManager
import io.mockk.mockk

val CONTEXT = ContextManager().context(mockk<Terminal>(), mockk<Runtime>())
