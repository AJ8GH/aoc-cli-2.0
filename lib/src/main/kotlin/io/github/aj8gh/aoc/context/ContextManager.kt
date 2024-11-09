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
import io.github.aj8gh.aoc.http.ReadmeClient

open class ContextManager {

  fun context(
    terminal: Terminal = terminal(),
    runtime: Runtime = runtime(),
  ): ApplicationContext {
    val executor = executor(runtime)

    val answerCacheManager = answerCacheManager()
    val inputCache = inputCache()
    val readmeCache = readmeCache()

    val aocClient = aocClient()
    val answerClient = answerClient(aocClient)
    val inputClient = inputClient(aocClient)
    val readmeClient = readmeClient(aocClient)

    val inputCreator = inputCreator(inputCache, inputClient)
    val readmeCreator = readmeCreator(readmeCache, readmeClient, answerCacheManager)
    val exampleCreator = exampleCreator()
    val codeCreator = codeCreator(answerCacheManager)

    val echoHandler = echoHandler()
    val setHandler = setHandler(echoHandler)
    val statHandler = statHandler(answerCacheManager, terminal)

    val tokenHandler = tokenHandler()
    val profileHandler = profileHandler()
    val nextHandler = nextHandler(echoHandler)
    val createHandler = createHandler(
      inputCreator,
      readmeCreator,
      exampleCreator,
      codeCreator,
    )

    val filesHandler = filesHandler(executor)
    val openHandler = openHandler(executor)
    val answerHandler = answerHandler(answerCacheManager, answerClient, createHandler)

    return ApplicationContext(
      ApplicationContext.Handler(
        set = setHandler,
        answer = answerHandler,
        profile = profileHandler,
        token = tokenHandler,
        create = createHandler,
        echo = echoHandler,
        files = filesHandler,
        next = nextHandler,
        open = openHandler,
        stats = statHandler,
      ),
      ApplicationContext.Exec(
        terminal = terminal,
        runtime = runtime,
        executor = executor,
      ),
      ApplicationContext.Client(
        aoc = aocClient,
        answer = answerClient,
        input = inputClient,
        readme = aocClient,
      ),
      ApplicationContext.Creator(
        input = inputCreator,
        readme = readmeCreator,
        example = exampleCreator,
        code = codeCreator,
      ),
      ApplicationContext.Cache(
        answer = answerCacheManager,
        input = inputCache,
        readme = readmeCache,
      )
    )
  }

  private fun terminal() = Terminal(width = 100)
  private fun runtime() = Runtime.getRuntime()
  private fun executor(runtime: Runtime) = Executor(runtime)

  private fun answerCacheManager() = AnswerCache()
  private fun readmeCache() = ReadmeCache()
  private fun inputCache() = InputCache()

  private fun inputCreator(
    inputCache: InputCache,
    inputClient: InputClient,
  ) = InputCreator(inputCache, inputClient)

  private fun readmeCreator(
    readmeCache: ReadmeCache,
    readmeClient: ReadmeClient,
    answerCache: AnswerCache,
  ) = ReadmeCreator(readmeCache, readmeClient, answerCache)

  private fun exampleCreator() = ExampleCreator()
  private fun codeCreator(answerCache: AnswerCache) = CodeCreator(answerCache)

  private fun aocClient() = AocClient()
  private fun answerClient(aocClient: AocClient) = AnswerClient(aocClient)
  private fun inputClient(aocClient: AocClient) = InputClient(aocClient)
  private fun readmeClient(aocClient: AocClient) = ReadmeClient(aocClient)

  private fun setHandler(echoHandler: EchoHandler) = SetHandler(echoHandler)

  private fun statHandler(
    answerCache: AnswerCache,
    terminal: Terminal,
  ) = StatsHandler(answerCache, terminal)

  private fun tokenHandler() = TokenHandler()
  private fun profileHandler() = ProfileHandler()
  private fun nextHandler(echoHandler: EchoHandler) = NextHandler(echoHandler)
  private fun echoHandler() = EchoHandler()

  private fun createHandler(
    inputCreator: InputCreator,
    readmeCreator: ReadmeCreator,
    exampleCreator: ExampleCreator,
    codeCreator: CodeCreator,
  ) = CreateHandler(inputCreator, readmeCreator, exampleCreator, codeCreator)

  private fun filesHandler(executor: Executor) = FilesHandler(executor)
  private fun openHandler(executor: Executor) = OpenHandler(executor)

  private fun answerHandler(
    answerCache: AnswerCache,
    answerClient: AnswerClient,
    createHandler: CreateHandler,
  ) = AnswerHandler(answerCache, answerClient, createHandler)
}
