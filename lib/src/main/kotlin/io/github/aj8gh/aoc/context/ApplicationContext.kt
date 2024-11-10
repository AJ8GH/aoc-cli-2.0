package io.github.aj8gh.aoc.context

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.cache.InputCache
import io.github.aj8gh.aoc.cache.ReadmeCache
import io.github.aj8gh.aoc.cache.answer.AnswerCache
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.*
import io.github.aj8gh.aoc.command.handler.runtime.Executor
import io.github.aj8gh.aoc.command.handler.runtime.FilesHandler
import io.github.aj8gh.aoc.command.handler.runtime.OpenHandler
import io.github.aj8gh.aoc.http.AnswerClient
import io.github.aj8gh.aoc.http.AocClient
import io.github.aj8gh.aoc.http.InputClient
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager

data class ApplicationContext(
  val handler: Handler,
  val exec: Exec,
  val client: Client,
  val creator: Creator,
  val cache: Cache,
  val manager: Manager,
  val io: Io,
) {

  data class Handler(
    val set: SetHandler,
    val stats: StatsHandler,
    val token: TokenHandler,
    val profile: ProfileHandler,
    val next: NextHandler,
    val echo: EchoHandler,
    val create: CreateHandler,
    val files: FilesHandler,
    val open: OpenHandler,
    val answer: AnswerHandler,
  )

  data class Exec(
    val terminal: Terminal,
    val runtime: Runtime,
    val executor: Executor,
  )

  data class Client(
    val aoc: AocClient,
    val answer: AnswerClient,
    val input: InputClient,
    val readme: AocClient,
  )

  data class Creator(
    val input: InputCreator,
    val readme: ReadmeCreator,
    val example: ExampleCreator,
    val code: CodeCreator,
  )

  data class Cache(
    val answer: AnswerCache,
    val input: InputCache,
    val readme: ReadmeCache,
  )

  data class Manager(
    val file: FileManager,
    val props: PropertiesManager,
    val propsFiles: PropertyFileManager,
  )

  data class Io(
    val reader: Reader,
    val writer: Writer,
    val log: Logger,
  )
}
