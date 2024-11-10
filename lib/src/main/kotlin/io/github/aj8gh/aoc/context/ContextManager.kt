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
import io.github.aj8gh.aoc.io.FileManager
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager

open class ContextManager {

  fun context(
    terminal: Terminal = terminal(),
    runtime: Runtime = runtime(),
  ): ApplicationContext {

    val executor = executor(runtime)
    val reader = reader()
    val propertyFileManager = propertyFileManager(reader)
    val writer = writer(propertyFileManager)
    val propertiesManager = propertiesManager(writer, reader, propertyFileManager)
    val fileManager = fileManager(propertiesManager, propertyFileManager)

    val answerCache = answerCacheManager(fileManager, propertiesManager, reader, writer)
    val inputCache = inputCache(fileManager, reader, writer)
    val readmeCache = readmeCache(fileManager, reader, writer)

    val aocClient = aocClient(propertiesManager)
    val answerClient = answerClient(aocClient, propertiesManager)
    val inputClient = inputClient(aocClient)
    val readmeClient = readmeClient(aocClient)

    val inputCreator = inputCreator(inputCache, inputClient, fileManager, writer)
    val readmeCreator = readmeCreator(readmeCache, readmeClient, answerCache, fileManager, reader, writer)
    val exampleCreator = exampleCreator(fileManager, writer)
    val codeCreator = codeCreator(answerCache, fileManager, propertiesManager, writer)

    val echoHandler = echoHandler(propertiesManager, propertyFileManager, reader)
    val setHandler = setHandler(propertiesManager, echoHandler)
    val statHandler = statHandler(answerCache, terminal)

    val tokenHandler = tokenHandler(propertiesManager, propertyFileManager, writer)
    val profileHandler = profileHandler(propertiesManager)
    val nextHandler = nextHandler(propertiesManager, echoHandler)
    val createHandler = createHandler(inputCreator, readmeCreator, exampleCreator, codeCreator)

    val filesHandler = filesHandler(propertiesManager, propertyFileManager, executor)
    val openHandler = openHandler(propertiesManager, executor)
    val answerHandler = answerHandler(answerCache, answerClient, createHandler, nextHandler)

    return ApplicationContext(
      handler = ApplicationContext.Handler(
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
      exec = ApplicationContext.Exec(
        terminal = terminal,
        runtime = runtime,
        executor = executor,
      ),
      client = ApplicationContext.Client(
        aoc = aocClient,
        answer = answerClient,
        input = inputClient,
        readme = aocClient,
      ),
      creator = ApplicationContext.Creator(
        input = inputCreator,
        readme = readmeCreator,
        example = exampleCreator,
        code = codeCreator,
      ),
      cache = ApplicationContext.Cache(
        answer = answerCache,
        input = inputCache,
        readme = readmeCache,
      ),
      manager = ApplicationContext.Manager(
        file = fileManager,
        props = propertiesManager,
        propsFiles = propertyFileManager,
      ),
      io = ApplicationContext.Io(
        reader = reader,
        writer = writer,
      ),
    )
  }

  private fun propertiesManager(
    writer: Writer,
    reader: Reader,
    propertyFileManager: PropertyFileManager,
  ) = PropertiesManager(writer, reader, propertyFileManager)

  private fun propertyFileManager(reader: Reader) = PropertyFileManager(reader)

  private fun fileManager(
    propertiesManager: PropertiesManager,
    propertyFileManager: PropertyFileManager,
  ) = FileManager(propertiesManager, propertyFileManager)

  private fun reader() = Reader()
  private fun writer(propertyFileManager: PropertyFileManager) = Writer(propertyFileManager)

  private fun terminal() = Terminal(width = 100)
  private fun runtime() = Runtime.getRuntime()
  private fun executor(runtime: Runtime) = Executor(runtime)

  private fun answerCacheManager(
    fileManager: FileManager,
    propertiesManager: PropertiesManager,
    reader: Reader,
    writer: Writer,
  ) = AnswerCache(fileManager, writer, reader, propertiesManager)

  private fun readmeCache(
    fileManager: FileManager,
    reader: Reader,
    writer: Writer,
  ) = ReadmeCache(fileManager, writer, reader)

  private fun inputCache(
    fileManager: FileManager,
    reader: Reader,
    writer: Writer,
  ) = InputCache(fileManager, reader, writer)

  private fun inputCreator(
    inputCache: InputCache,
    inputClient: InputClient,
    fileManager: FileManager,
    writer: Writer,
  ) = InputCreator(inputCache, inputClient, writer, fileManager)

  private fun readmeCreator(
    readmeCache: ReadmeCache,
    readmeClient: ReadmeClient,
    answerCache: AnswerCache,
    fileManager: FileManager,
    reader: Reader,
    writer: Writer,
  ) = ReadmeCreator(readmeCache, readmeClient, answerCache, reader, writer, fileManager)

  private fun exampleCreator(
    fileManager: FileManager,
    writer: Writer,
  ) = ExampleCreator(fileManager, writer)

  private fun codeCreator(
    answerCache: AnswerCache,
    fileManager: FileManager,
    propertiesManager: PropertiesManager,
    writer: Writer,
  ) = CodeCreator(answerCache, fileManager, propertiesManager, writer)

  private fun aocClient(propertiesManager: PropertiesManager) = AocClient(propertiesManager)
  private fun answerClient(
    aocClient: AocClient,
    propertiesManager: PropertiesManager
  ) = AnswerClient(aocClient, propertiesManager)

  private fun inputClient(aocClient: AocClient) = InputClient(aocClient)
  private fun readmeClient(aocClient: AocClient) = ReadmeClient(aocClient)

  private fun setHandler(
    propertiesManager: PropertiesManager,
    echoHandler: EchoHandler
  ) = SetHandler(propertiesManager, echoHandler)

  private fun statHandler(
    answerCache: AnswerCache,
    terminal: Terminal,
  ) = StatsHandler(answerCache, terminal)

  private fun tokenHandler(
    propertiesManager: PropertiesManager,
    propertyFileManager: PropertyFileManager,
    writer: Writer
  ) = TokenHandler(propertiesManager, propertyFileManager, writer)

  private fun profileHandler(
    propertiesManager: PropertiesManager
  ) = ProfileHandler(propertiesManager)

  private fun nextHandler(
    propertiesManager: PropertiesManager,
    echoHandler: EchoHandler,
  ) = NextHandler(propertiesManager, echoHandler)

  private fun echoHandler(
    propertiesManager: PropertiesManager,
    propertyFileManager: PropertyFileManager,
    reader: Reader,
  ) = EchoHandler(propertiesManager, propertyFileManager, reader)

  private fun createHandler(
    inputCreator: InputCreator,
    readmeCreator: ReadmeCreator,
    exampleCreator: ExampleCreator,
    codeCreator: CodeCreator,
  ) = CreateHandler(inputCreator, readmeCreator, exampleCreator, codeCreator)

  private fun filesHandler(
    propertiesManager: PropertiesManager,
    propertyFileManager: PropertyFileManager,
    executor: Executor,
  ) = FilesHandler(propertiesManager, propertyFileManager, executor)

  private fun openHandler(
    propertiesManager: PropertiesManager,
    executor: Executor,
  ) = OpenHandler(propertiesManager, executor)

  private fun answerHandler(
    answerCache: AnswerCache,
    answerClient: AnswerClient,
    createHandler: CreateHandler,
    nextHandler: NextHandler,
  ) = AnswerHandler(answerCache, answerClient, createHandler, nextHandler)
}
