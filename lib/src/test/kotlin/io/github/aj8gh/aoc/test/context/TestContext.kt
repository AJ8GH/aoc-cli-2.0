package io.github.aj8gh.aoc.test.context

import io.github.aj8gh.aoc.Runner
import io.github.aj8gh.aoc.context.ContextManager
import io.mockk.mockk
import java.time.Clock
import java.time.Instant
import java.time.Instant.parse
import java.time.ZoneOffset.UTC

val FIXED_INSTANT: Instant = parse("2024-01-01T00:00:00Z")
val FIXED_ZONE_ID = UTC!!

val CONTEXT = ContextManager().context(
  runtime = mockk<Runtime>(),
  clock = Clock.fixed(FIXED_INSTANT, FIXED_ZONE_ID)
)

val RUNNER = Runner(context = CONTEXT)

val FILES = CONTEXT.manager.file
val PROPS_FILES = CONTEXT.manager.propsFiles
val PROPS = CONTEXT.manager.props

val READER = CONTEXT.io.reader
val WRITER = CONTEXT.io.writer
