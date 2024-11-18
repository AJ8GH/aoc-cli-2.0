package io.github.aj8gh.aoc.io

import io.github.aj8gh.aoc.context.ApplicationProperties
import io.github.aj8gh.aoc.properties.PropertiesManager
import java.io.File

class FileManager(
  private val props: PropertiesManager,
  private val files: ApplicationProperties.Files,
) {

  fun aocPropertiesFile() = File(files.aocProperties())
  fun activeProfileFile() = File(files.activeProfile(props.aocProperties().active))
  fun logFile() = File(files.log())

  fun readme() = File("${resourcesDir()}${files.readme}")
  fun inputFile() = File("${resourcesDir()}${files.input}")
  fun exampleFile() = File("${resourcesDir()}${files.example}")
  fun mainFile() = File("${mainDir()}${mainFileName()}")
  fun testFile() = File("${testDir()}${testFileName()}")

  fun readmeCacheFile() = File(files.readmeCache(props.year(), props.day()))
  fun inputCacheFile() = File(files.inputCache(props.year(), props.day()))
  fun answerCacheFile() = File(files.answerCache())
  fun exampleCacheFile() = File(files.exampleCache())
  fun mainTemplateFile() = File(files.mainTemplate(props.activeProfile().name))
  fun testTemplateFile() = File(files.testTemplate(props.activeProfile().name))

  private fun moduleDir() = props.files().modulePrefix?.let { it + props.year() } ?: ""
  private fun contentRootDir() = "${props.project()}${moduleDir()}/"
  private fun mainDir() = sourceDir("${props.files().mainDir}${yearSourceDir()}/")
  private fun testDir() = sourceDir("${props.files().testDir}${yearSourceDir()}/")
  private fun sourceDir(type: String) = "${contentRootDir()}${type}${props.files().dayPrefix}${props.day()}/"
  private fun yearSourceDir() = "${props.files().yearPrefix}${props.year()}"
  private fun fileExt() = props.activeProfile().language
  private fun mainFileName() = "${props.files().mainFilePrefix}${props.day()}.${fileExt()}"
  private fun testFileName() = "${props.files().testFilePrefix}${props.day()}${props.files().testFileSuffix}.${fileExt()}"
  private fun resourcesDir() = with(props.files().resourcesSubDir) {
    props.files()
      .resourcesDir
      ?.let { "${sourceDir(it)}${this}/" }
      ?: "${mainDir()}${this}/"
  }
}
