package io.github.aj8gh.aoc.context

import io.github.aj8gh.aoc.io.Logger

data class ApplicationProperties(
  val files: Files,
  val console: Console,
  val log: Log,
  val http: Http,
) {

  data class Files(
    val dirs: Directories,
    val aocProperties: String,
    val log: String,
    val answerCache: String,
    val exampleCache: String,
    val input: String,
    val example: String,
    val readmeCache: String,
    val readme: String,
    val mainTemplate: String,
    val testTemplate: String,
  ) {
    fun aocProperties() = "${dirs.home()}$aocProperties"
    fun activeProfile(profile: String) = "${dirs.home()}$profile.yaml"
    fun log() = "${dirs.log()}${log}"
    fun answerCache() = "${dirs.answerCache()}$answerCache"
    fun exampleCache() = "${dirs.exampleCache()}$exampleCache"
    fun readmeCache(year: Int, day: Int) = "${dirs.fileCache(year = year, day = day)}$readmeCache"
    fun inputCache(year: Int, day: Int) = "${dirs.fileCache(year = year, day = day)}$input"
    fun mainTemplate(profile: String) = "${dirs.template(profile)}$mainTemplate"
    fun testTemplate(profile: String) = "${dirs.template(profile)}$testTemplate"
  }

  data class Directories(
    private val home: String,
    val log: String,
    val cache: String,
    val fileCache: String,
    val answerCache: String,
    val exampleCache: String,
    val template: String,
  ) {
    fun home() = home.replace("{HOME}", System.getProperty("user.home"))
    fun log() = "${home()}$log"
    fun cache() = "${home()}$cache"
    fun fileCache(year: Int, day: Int) = "${home()}$cache${fileCache}y$year/d$day/"
    fun answerCache() = "${home()}$cache$answerCache"
    fun exampleCache() = "${home()}$cache$exampleCache"
    fun template(profile: String) = "${home()}$template$profile/"
  }

  data class Console(
    val width: Int,
  )

  data class Log(val level: Level) {
    data class Level(
      val console: Logger.Level,
      val file: Logger.Level,
    )
  }

  data class Http(
    val url: String,
    val endpoint: Endpoint,
  ) {
    data class Endpoint(
      val answer: String,
      val input: String,
      val readme: String,
    )
  }
}
