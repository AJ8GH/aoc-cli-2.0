package io.github.aj8gh.aoc.context

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
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
import io.github.aj8gh.aoc.io.*
import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager
import java.time.Clock

open class ContextManager {

  fun context(
    terminal: Terminal = Terminal(width = 100),
    runtime: Runtime = Runtime.getRuntime(),
    mapper: ObjectMapper = ObjectMapper(YAMLFactory()).registerKotlinModule(),
    clock: Clock = Clock.systemUTC()
  ): ApplicationContext {

    val console = Console(terminal)
    val executor = Executor(runtime)
    val reader = Reader(mapper)
    val propertyFileManager = PropertyFileManager(reader)
    val writer = Writer(propertyFileManager, mapper)
    val propertiesManager = PropertiesManager(writer, reader, propertyFileManager)
    val fileManager = FileManager(propertiesManager, propertyFileManager)
    val logger = Logger(writer, fileManager)
    val dateManager = DateManager(clock)

    val answerCache = AnswerCache(fileManager, writer, reader, propertiesManager)
    val inputCache = InputCache(fileManager, reader, writer)
    val readmeCache = ReadmeCache(fileManager, writer, reader)

    val aocClient = AocClient(propertiesManager)
    val answerClient = AnswerClient(aocClient, propertiesManager)
    val inputClient = InputClient(aocClient)
    val readmeClient = ReadmeClient(aocClient)

    val inputCreator = InputCreator(inputCache, inputClient, writer, fileManager)
    val readmeCreator = ReadmeCreator(readmeCache, readmeClient, answerCache, reader, writer, fileManager)
    val exampleCreator = ExampleCreator(fileManager, writer, console)
    val codeCreator = CodeCreator(answerCache, fileManager, propertiesManager, writer)

    val echoHandler = EchoHandler(propertiesManager, propertyFileManager, reader, console)
    val setHandler = SetHandler(propertiesManager, echoHandler)
    val statHandler = StatsHandler(answerCache, console, dateManager)

    val tokenHandler = TokenHandler(propertiesManager, propertyFileManager, writer)
    val profileHandler = ProfileHandler(propertiesManager)
    val nextHandler = NextHandler(propertiesManager, echoHandler, dateManager)
    val createHandler = CreateHandler(inputCreator, readmeCreator, exampleCreator, codeCreator)

    val filesHandler = FilesHandler(propertiesManager, propertyFileManager, executor)
    val openHandler = OpenHandler(propertiesManager, executor)
    val answerHandler = AnswerHandler(answerCache, answerClient, createHandler, nextHandler, console)

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
      system = ApplicationContext.System(
        runtime = runtime,
        executor = executor,
        clock = clock,
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
        date = dateManager,
      ),
      io = ApplicationContext.Io(
        reader = reader,
        writer = writer,
        log = logger,
        console = console,
        terminal = terminal,
        mapper = mapper,
      ),
    )
  }
}
