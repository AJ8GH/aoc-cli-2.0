package io.github.aj8gh.aoc.test.context

import io.github.aj8gh.aoc.Runner
import io.github.aj8gh.aoc.context.ContextBuilder
import io.github.aj8gh.aoc.context.ContextManager
import io.mockk.mockk
import java.time.Clock
import java.time.Instant.parse
import java.time.ZoneOffset.UTC

private const val TEST_PROPERTIES = "application-test.yaml"

private val contextManager = ContextManager(ContextBuilder())
private val fixedInstant = parse("2024-01-01T00:00:00Z")
private val fixedZoneId = UTC!!

val context = contextManager.context(
  runtime = mockk<Runtime>(),
  clock = Clock.fixed(fixedInstant, fixedZoneId),
  properties = TEST_PROPERTIES,
)

val runner = Runner(context = context)
val files = context.manager.file
val props = context.manager.props
val reader = context.io.reader
val writer = context.io.writer
val appProps = context.app.properties
