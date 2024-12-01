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
import io.github.aj8gh.aoc.command.handler.runtime.WebHandler
import io.github.aj8gh.aoc.http.AnswerClient
import io.github.aj8gh.aoc.http.AocClient
import io.github.aj8gh.aoc.http.InputClient
import io.github.aj8gh.aoc.http.ReadmeClient
import io.github.aj8gh.aoc.io.*
import io.github.aj8gh.aoc.properties.PropertiesManager
import java.io.File
import java.time.Clock

class ContextBuilder {

  fun context(
    properties: String,
    runtime: Runtime,
    clock: Clock,
  ): ApplicationContext {

    val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
    val reader = Reader(mapper)
    val writer = Writer(mapper)
    val propertiesLoader = PropertiesLoader(reader)
    val props = propertiesLoader.load(properties)

    val terminal = Terminal(width = props.console.width)
    val console = Console(terminal)
    val executor = Executor(runtime)

    DirCreator().mkdirs(File(props.files.log()))
    val logger = Logger(writer, File(props.files.log()), console, clock, fileLevel = props.log.level.file, consoleLevel = props.log.level.console)
    val propertiesManager = PropertiesManager(writer, reader, props.files, logger.of(PropertiesManager::class.simpleName))
    val fileManager = FileManager(propertiesManager, props.files)
    val dateManager = DateManager(clock)

    val dirCreator = DirCreator(logger.of(DirCreator::class.simpleName))
    val answerCache = AnswerCache(fileManager, writer, reader, propertiesManager, dirCreator)
    val inputCache = InputCache(fileManager, dirCreator, reader, writer)
    val readmeCache = ReadmeCache(fileManager, dirCreator, writer, reader)

    val aocClient = AocClient(propertiesManager, logger.of(AocClient::class.simpleName))
    val answerClient = AnswerClient(aocClient, propertiesManager, props.http.endpoint.answer)
    val inputClient = InputClient(aocClient, props.http.endpoint.input)
    val readmeClient = ReadmeClient(aocClient, props.http.endpoint.readme)

    val inputCreator = InputCreator(inputCache, inputClient, writer, fileManager, dirCreator, logger.of(InputCreator::class.simpleName), console)
    val readmeCreator = ReadmeCreator(readmeCache, readmeClient, answerCache, reader, writer, fileManager, dirCreator, logger.of(ReadmeCreator::class.simpleName), console)
    val exampleCreator = ExampleCreator(fileManager, dirCreator, writer, console, logger.of(ExampleCreator::class.simpleName))
    val codeCreator = CodeCreator(answerCache, fileManager, dirCreator, propertiesManager, writer, logger.of(CodeCreator::class.simpleName), console)

    val echoHandler = EchoHandler(propertiesManager, reader, console, fileManager)
    val setHandler = SetHandler(propertiesManager, echoHandler)
    val statHandler = StatsHandler(answerCache, console, dateManager)

    val tokenHandler = TokenHandler(propertiesManager, writer, fileManager, logger.of(TokenHandler::class.simpleName), console)
    val profileHandler = ProfileHandler(propertiesManager, console)
    val nextHandler = NextHandler(propertiesManager, echoHandler, dateManager, console)
    val createHandler = CreateHandler(inputCreator, readmeCreator, exampleCreator, codeCreator)

    val filesHandler = FilesHandler(propertiesManager, executor, props.files.dirs.home(), console)
    val openHandler = OpenHandler(propertiesManager, executor, console)
    val answerHandler = AnswerHandler(answerCache, answerClient, createHandler, nextHandler, console, propertiesManager, readmeCreator)
    val webHandler = WebHandler(propertiesManager, executor, console)

    val context = ApplicationContext(
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
        web = webHandler,
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
        date = dateManager,
      ),
      io = ApplicationContext.Io(
        reader = reader,
        writer = writer,
        log = logger,
        console = console,
        terminal = terminal,
        mapper = mapper,
        dirCreator = dirCreator,
      ),
      app = ApplicationContext.Application(
        properties = props,
        propertiesLoader = propertiesLoader,
      ),
    )

    return context
  }
}
