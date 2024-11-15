package io.github.aj8gh.aoc.test.context

import io.github.aj8gh.aoc.Runner
import io.github.aj8gh.aoc.context.ApplicationContext
import io.github.aj8gh.aoc.context.ContextManager
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager
import io.mockk.mockk
import java.time.Clock
import java.time.Instant
import java.time.Instant.parse
import java.time.ZoneId
import java.time.ZoneOffset.UTC

fun buildContext() {
  FIXED_INSTANT = parse("2024-01-01T00:00:00Z")
  FIXED_ZONE_ID = UTC!!
  CONTEXT = ContextManager().context(
    runtime = mockk<Runtime>(),
    clock = Clock.fixed(FIXED_INSTANT, FIXED_ZONE_ID)
  )
  RUNNER = Runner(context = CONTEXT)
  FILES = CONTEXT.manager.file
  PROPS_FILES = CONTEXT.manager.propsFiles
  PROPS = CONTEXT.manager.props
  READER = CONTEXT.io.reader
  WRITER = CONTEXT.io.writer
}

lateinit var FIXED_INSTANT: Instant
lateinit var FIXED_ZONE_ID: ZoneId
lateinit var CONTEXT: ApplicationContext
lateinit var RUNNER: Runner
lateinit var FILES: FileManager
lateinit var PROPS_FILES: PropertyFileManager
lateinit var PROPS: PropertiesManager
lateinit var READER: Reader
lateinit var WRITER: Writer
