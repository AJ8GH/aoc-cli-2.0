package io.github.aj8gh.aoc.test.steps

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

class When {

  fun createInputIsCalled() = InputCreator(InputCache(), InputClient(AocClient())).create()
  fun createCodeIsCalled() = CodeCreator(AnswerCacheManager()).create()
  fun createExampleIsCalled() = ExampleCreator().create()
  fun profileIsCalledWith(profile: String) = ProfileHandler().profile(profile)
  fun openIsCalled(runtime: Runtime) = OpenHandler(Executor(runtime)).open(true)
  fun configFileIsCalled(runtime: Runtime) = FilesHandler(Executor(runtime)).files(true)
  fun tokenIsCalled(token: String) = TokenHandler().token(token)
  fun theAnswerIsCached(answerCacheManager: AnswerCacheManager, answer: String) =
    answerCacheManager.cacheAnswer(answer)

  fun statsIsCalled(flag: Boolean, terminal: Terminal) =
    StatHandler(AnswerCacheManager(), terminal).stats(flag)

  fun nextIsCalledFor(flag: Boolean) =
    NextHandler(EchoHandler()).next(flag = flag, verbose = false)

  fun echoCurrentIsCalledFor(echo: Boolean, verbose: Boolean) =
    EchoHandler().echoCurrent(echo, verbose)

  fun answerIsCalledWith(answer: String) = AnswerHandler(
    AnswerCacheManager(),
    AnswerClient(AocClient()),
    CreateHandler(
      InputCreator(InputCache(), InputClient(AocClient())),
      ReadmeCreator(ReadmeCache(), ReadmeClient(AocClient()), AnswerCacheManager()),
      ExampleCreator(),
      CodeCreator(AnswerCacheManager())
    )
  ).answer(answer, false)

  fun setIsCalledFor(year: Int?, day: Int?, level: Int?) =
    SetHandler(EchoHandler()).set(year = year, day = day, level = level, false)

  fun createReadmeIsCalled() = ReadmeCreator(
    ReadmeCache(),
    ReadmeClient(AocClient()),
    AnswerCacheManager()
  ).create()
}
