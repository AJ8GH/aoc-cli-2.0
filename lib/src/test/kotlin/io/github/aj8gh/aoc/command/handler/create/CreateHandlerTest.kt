package io.github.aj8gh.aoc.command.handler.create

import io.github.aj8gh.aoc.context.DateManager
import io.github.aj8gh.aoc.io.Console
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CreateHandlerTest {

  private val inputCreator = mockk<InputCreator>()
  private val readmeCreator = mockk<ReadmeCreator>()
  private val exampleCreator = mockk<ExampleCreator>()
  private val codeCreator = mockk<CodeCreator>()
  private val logger = mockk<Logger>()
  private val propertiesManager = mockk<PropertiesManager>()
  private val dateManager = mockk<DateManager>()
  private val console = mockk<Console>()

  private val subject = CreateHandler(
    inputCreator,
    readmeCreator,
    exampleCreator,
    codeCreator,
    logger,
    propertiesManager,
    dateManager,
    console,
  )

  @ParameterizedTest
  @MethodSource("source")
  fun handle(
    year: Int,
    day: Int,
    available: Boolean,
  ) {
    every { propertiesManager.year() } returns year
    every { propertiesManager.day() } returns day
    every { dateManager.isPuzzleAvailable(year, day) } returns available
    every { readmeCreator.create() } returns Unit
    every { inputCreator.create() } returns Unit
    every { exampleCreator.create() } returns Unit
    every { codeCreator.create() } returns Unit
    every { logger.info(any()) } returns Unit
    every { console.echo(any<String>()) } returns Unit

    subject.handle()

    verify(exactly = if (available) 1 else 0) {
      readmeCreator.create()
      inputCreator.create()
      exampleCreator.create()
    }
    verify { codeCreator.create() }
  }

  companion object {
    @JvmStatic
    private fun source() = listOf(
      Arguments.of(24, 25, true),
      Arguments.of(24, 23, false),
    )
  }
}
