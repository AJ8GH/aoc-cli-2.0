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
import io.github.aj8gh.aoc.http.ReadmeClient

private const val TEST_PROFILE = "TEST"
private const val DEV_PROFILE = "DEV"
private const val PROD_PROFILE = "PROD"

open class ContextManager {

  fun context(profile: String? = DEV_PROFILE): ApplicationContext {
    val terminal = terminal()
    val runtime = runtime()
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
        setHandler = setHandler,
        answerHandler = answerHandler,
        profileHandler = profileHandler,
        tokenHandler = tokenHandler,
        createHandler = createHandler,
        echoHandler = echoHandler,
        filesHandler = filesHandler,
        nextHandler = nextHandler,
        openHandler = openHandler,
        statHandler = statHandler,
      ),
      ApplicationContext.Exec(
        terminal = terminal,
        runtime = runtime,
        executor = executor,
      ),
      ApplicationContext.Client(
        aocClient = aocClient,
        answerClient = answerClient,
        inputClient = inputClient,
        readmeClient = aocClient,
      ),
      ApplicationContext.Creator(
        inputCreator = inputCreator,
        readmeCreator = readmeCreator,
        exampleCreator = exampleCreator,
        codeCreator = codeCreator,
      ),
      ApplicationContext.Cache(
        answerCacheManager = answerCacheManager,
        inputCache = inputCache,
        readmeCache = readmeCache,
      )
    )
  }

  private fun terminal() = Terminal(width = 100)
  private fun runtime() = Runtime.getRuntime()
  private fun executor(runtime: Runtime) = Executor(runtime)

  private fun answerCacheManager() = AnswerCacheManager()
  private fun readmeCache() = ReadmeCache()
  private fun inputCache() = InputCache()

  private fun inputCreator(
    inputCache: InputCache,
    inputClient: InputClient,
  ) = InputCreator(inputCache, inputClient)

  private fun readmeCreator(
    readmeCache: ReadmeCache,
    readmeClient: ReadmeClient,
    answerCacheManager: AnswerCacheManager,
  ) = ReadmeCreator(readmeCache, readmeClient, answerCacheManager)

  private fun exampleCreator() = ExampleCreator()
  private fun codeCreator(answerCacheManager: AnswerCacheManager) = CodeCreator(answerCacheManager)

  private fun aocClient() = AocClient()
  private fun answerClient(aocClient: AocClient) = AnswerClient(aocClient)
  private fun inputClient(aocClient: AocClient) = InputClient(aocClient)
  private fun readmeClient(aocClient: AocClient) = ReadmeClient(aocClient)

  private fun setHandler(echoHandler: EchoHandler) = SetHandler(echoHandler)

  private fun statHandler(
    answerCacheManager: AnswerCacheManager,
    terminal: Terminal,
  ) = StatHandler(answerCacheManager, terminal)

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
    answerCacheManager: AnswerCacheManager,
    answerClient: AnswerClient,
    createHandler: CreateHandler,
  ) = AnswerHandler(answerCacheManager, answerClient, createHandler)
}
