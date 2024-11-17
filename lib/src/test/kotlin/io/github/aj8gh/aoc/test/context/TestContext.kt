package io.github.aj8gh.aoc.test.context

import io.github.aj8gh.aoc.Runner
import io.github.aj8gh.aoc.context.ApplicationContext
import io.github.aj8gh.aoc.context.ApplicationProperties
import io.github.aj8gh.aoc.context.ContextBuilder
import io.github.aj8gh.aoc.context.ContextManager
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.mockk.mockk
import java.time.Clock
import java.time.Instant
import java.time.Instant.parse
import java.time.ZoneId
import java.time.ZoneOffset.UTC

private const val TEST_PROPERTIES = "application-test.yaml"
private val contextManager = ContextManager(ContextBuilder())

fun buildContext() {
  fixedInstant = parse("2024-01-01T00:00:00Z")
  fixedZoneId = UTC!!
  context = contextManager.context(
    runtime = mockk<Runtime>(),
    clock = Clock.fixed(fixedInstant, fixedZoneId),
    properties = TEST_PROPERTIES,
  )
  runner = Runner(context = context)
  files = context.manager.file
  props = context.manager.props
  reader = context.io.reader
  writer = context.io.writer
  appProps = context.app.properties
}

lateinit var fixedInstant: Instant
lateinit var fixedZoneId: ZoneId
lateinit var context: ApplicationContext
lateinit var runner: Runner
lateinit var files: FileManager
lateinit var props: PropertiesManager
lateinit var reader: Reader
lateinit var writer: Writer
lateinit var appProps: ApplicationProperties
