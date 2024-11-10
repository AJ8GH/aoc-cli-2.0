package io.github.aj8gh.aoc.io

import io.github.aj8gh.aoc.properties.PropertiesManager
import io.github.aj8gh.aoc.properties.PropertyFileManager
import java.io.File

const val EXTENSION = ".yaml"

private const val CACHE_DIR = "cache/"
private const val FILE_CACHE_DIR = "files/"
private const val ANSWER_CACHE_DIR = "answer/"
private const val TEMPLATE_DIR = "template/"
private const val LOG_DIR = "log/"
private const val ANSWER_CACHE_FILE_NAME = "answers.yaml"
private const val EXAMPLE_CACHE_FILE_NAME = "examples.yaml"
private const val INPUT_FILE_NAME = "input.txt"
private const val ERROR_LOG_FILE = "error.log"
private const val EXAMPLE_FILE_NAME = "example.txt"
private const val README_CACHE_FILE_NAME = "README.html"
private const val README_FILE_NAME = "README.md"
private const val MAIN_TEMPLATE_FILE_NAME = "main.txt"
private const val TEST_TEMPLATE_FILE_NAME = "test.txt"

class FileManager(
  private val props: PropertiesManager,
  private val propsFiles: PropertyFileManager,
) {

  // Project Files
  fun readmeFile() = File("${resourcesDir()}$README_FILE_NAME")
  fun inputFile() = File("${resourcesDir()}$INPUT_FILE_NAME")
  fun exampleFile() = File("${resourcesDir()}$EXAMPLE_FILE_NAME")
  fun mainFile() = File("${mainDir()}${mainFileName()}")
  fun testFile() = File("${testDir()}${testFileName()}")

  // Cache & Template Files
  fun readmeCacheFile() = File("${resourcesCacheDir()}$README_CACHE_FILE_NAME")
  fun inputCacheFile() = File("${resourcesCacheDir()}$INPUT_FILE_NAME")
  fun answerCacheFile() = File("${answerCacheDir()}$ANSWER_CACHE_FILE_NAME")
  fun exampleCacheFile() = File("${answerCacheDir()}$EXAMPLE_CACHE_FILE_NAME")
  fun mainTemplateFile() = File("${templateDir()}$MAIN_TEMPLATE_FILE_NAME")
  fun errorLogFile() = File("${logDir()}$ERROR_LOG_FILE")

  fun testTemplateFile() = File("${templateDir()}$TEST_TEMPLATE_FILE_NAME")
  fun createResourcesDirIfNotExists() = createDirsIfNotExists(resourcesDir())
  fun createAnswerCacheDirIfNotExists() = createDirsIfNotExists(answerCacheDir())
  fun createLogDirIfNotExists() = createDirsIfNotExists(logDir())
  fun createResourcesCacheDirIfNotExists() = createDirsIfNotExists(resourcesCacheDir())
  fun createSourceDirsIfNotExists() {
    createDirsIfNotExists(mainDir())
    createDirsIfNotExists(testDir())
  }

  fun resourcesDir() = with(props.files().resourcesSubDir) {
    props.files()
      .resourcesDir
      ?.let { "${sourceDir(it)}${this}/" }
      ?: "${mainDir()}${this}/"
  }

  private fun moduleDir() = props.files().modulePrefix?.let { it + props.year() } ?: ""
  private fun contentRootDir() = "${props.project()}${moduleDir()}/"
  private fun mainDir() = sourceDir("${props.files().mainDir}${yearSourceDir()}/")
  private fun testDir() = sourceDir("${props.files().testDir}${yearSourceDir()}/")
  private fun cacheDir() = "${propsFiles.aocHomeDir()}$CACHE_DIR"
  private fun logDir() = "${propsFiles.aocHomeDir()}$LOG_DIR"
  private fun resourcesCacheDir() = "${cacheDir()}$FILE_CACHE_DIR/y${props.year()}/d${props.day()}/"
  private fun answerCacheDir() = "${cacheDir()}$ANSWER_CACHE_DIR/"
  private fun templateDir() = "${propsFiles.aocHomeDir()}$TEMPLATE_DIR${fileExt()}/"
  private fun sourceDir(type: String) = "${contentRootDir()}${type}${props.files().dayPrefix}${props.day()}/"
  private fun yearSourceDir() = "${props.files().yearPrefix}${props.year()}"
  private fun fileExt() = props.activeProfile().language
  private fun mainFileName() = "${props.files().mainFilePrefix}${props.day()}.${fileExt()}"
  private fun testFileName() = "${props.files().testFilePrefix}${props.day()}${props.files().testFileSuffix}.${fileExt()}"
  private fun createDirsIfNotExists(dir: String) = File(dir).let {
    if (!it.exists()) it.mkdirs()
  }
}
