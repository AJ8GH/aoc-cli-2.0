package io.github.aj8gh.aoc.context

import com.github.ajalt.mordant.terminal.Terminal
import io.github.aj8gh.aoc.cache.InputCache
import io.github.aj8gh.aoc.cache.ReadmeCache
import io.github.aj8gh.aoc.cache.answer.AnswerCacheManager
import io.github.aj8gh.aoc.command.handler.*
import io.github.aj8gh.aoc.command.handler.create.*
import io.github.aj8gh.aoc.command.handler.runtime.Executor
import io.github.aj8gh.aoc.command.handler.runtime.FilesHandler
import io.github.aj8gh.aoc.command.handler.runtime.OpenHandler
import io.github.aj8gh.aoc.http.AnswerClient
import io.github.aj8gh.aoc.http.AocClient
import io.github.aj8gh.aoc.http.InputClient

data class ApplicationContext(
  val handler: Handler,
  val exec: Exec,
  val client: Client,
  val creator: Creator,
  val cache: Cache,
) {

  data class Handler(
    val setHandler: SetHandler,
    val statHandler: StatHandler,
    val tokenHandler: TokenHandler,
    val profileHandler: ProfileHandler,
    val nextHandler: NextHandler,
    val echoHandler: EchoHandler,
    val createHandler: CreateHandler,
    val filesHandler: FilesHandler,
    val openHandler: OpenHandler,
    val answerHandler: AnswerHandler,
  )

  data class Exec(
    val terminal: Terminal,
    val runtime: Runtime,
    val executor: Executor,
  )

  data class Client(
    val aocClient: AocClient,
    val answerClient: AnswerClient,
    val inputClient: InputClient,
    val readmeClient: AocClient,
  )

  data class Creator(
    val inputCreator: InputCreator,
    val readmeCreator: ReadmeCreator,
    val exampleCreator: ExampleCreator,
    val codeCreator: CodeCreator,
  )

  data class Cache(
    val answerCacheManager: AnswerCacheManager,
    val inputCache: InputCache,
    val readmeCache: ReadmeCache,
  )
}
