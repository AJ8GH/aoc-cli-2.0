package io.github.aj8gh.aoc.properties

data class Profile(
  val name: String,
  val language: String,
  val ide: String,
  val files: FileProperties,
  val current: CurrentProperties,
  val writeAnswerInCode: Boolean,
) {

  data class CurrentProperties(
    var year: Int,
    var day: Int,
    var level: Int,
  )

  data class FileProperties(
    val projectHome: String,
    val mainDir: String,
    val testDir: String,
    val mainFilePrefix: String,
    val testFilePrefix: String,
    val testFileSuffix: String,
    val resourcesDir: String?,
    val resourcesSubDir: String,
    val yearPrefix: String,
    val dayPrefix: String,
    val modulePrefix: String?,
  )
}
