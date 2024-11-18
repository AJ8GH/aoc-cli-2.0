package io.github.aj8gh.aoc.context

import java.time.Clock

private const val PROPERTIES = "application.yaml"

class ContextManager(
  private val builder: ContextBuilder,
) {

  fun context(
    properties: String = PROPERTIES,
    runtime: Runtime = Runtime.getRuntime(),
    clock: Clock = Clock.systemUTC(),
  ): ApplicationContext {

    return builder.context(
      properties = properties,
      runtime = runtime,
      clock = clock
    )
  }
}
