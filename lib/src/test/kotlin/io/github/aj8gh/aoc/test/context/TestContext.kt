package io.github.aj8gh.aoc.test.context

import io.github.aj8gh.aoc.Runner
import io.github.aj8gh.aoc.context.ContextBuilder
import io.github.aj8gh.aoc.context.ContextManager
import io.github.aj8gh.aoc.test.HTTP_PORT
import io.mockk.mockk
import java.time.Clock

private const val TEST_PROPERTIES = "application-test.yaml"

private val contextManager = ContextManager(ContextBuilder())

val context = contextManager.context(
  runtime = mockk<Runtime>(),
  clock = mockk<Clock>(),
  properties = TEST_PROPERTIES,
  port = HTTP_PORT,
)

val runner = Runner(context = context)
val files = context.manager.file
val props = context.manager.props
val reader = context.io.reader
val writer = context.io.writer
val appProps = context.app.properties
