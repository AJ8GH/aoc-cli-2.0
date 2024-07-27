package io.github.aj8gh.aoc.io

import io.github.aj8gh.aoc.properties.*
import java.io.File

private const val CACHE_DIR = "cache/"
private const val FILE_CACHE_DIR = "files/"
private const val ANSWER_CACHE_DIR = "answer/"
private const val TEMPLATE_DIR = "template/"
private const val AOC_PROPERTIES_FILE_NAME = "aoc.yaml"
private const val ANSWER_CACHE_FILE_NAME = "answers.yaml"
private const val EXAMPLE_CACHE_FILE_NAME = "examples.yaml"
private const val INPUT_FILE_NAME = "input.txt"
private const val EXAMPLE_FILE_NAME = "example.txt"
private const val EXAMPLE_N_FILE_NAME = "example-%s.txt"
private const val EXAMPLE_PART_2_FILE_NAME = "example-part-2.txt"
private const val EXAMPLE_PART_2_N_FILE_NAME = "example-part-2-%s.txt"
private const val README_CACHE_FILE_NAME = "README.html"
private const val README_FILE_NAME = "README.md"
private const val MAIN_TEMPLATE_FILE_NAME = "main.txt"
private const val TEST_TEMPLATE_FILE_NAME = "test.txt"

private val AOC_HOME = "${System.getProperty("user.home")}/.config/.aoc/"

var homeOverride: String? = null

fun aocPropertiesFile() = File("${aocHomeDir()}${AOC_PROPERTIES_FILE_NAME}")
fun activePropertiesFile() = File("${aocHomeDir()}${aocProperties().active}")

// Project Files
fun readmeFile() = File("${resourcesDir()}$README_FILE_NAME")
fun inputFile() = File("${resourcesDir()}$INPUT_FILE_NAME")
fun exampleFile() = File("${resourcesDir()}$EXAMPLE_FILE_NAME")
fun exampleFile(n: Int) = File("${resourcesDir()}${EXAMPLE_N_FILE_NAME.format(n)}")
fun examplePart2File() = File("${resourcesDir()}$EXAMPLE_PART_2_FILE_NAME")
fun examplePart2File(n: Int) = File("${resourcesDir()}${EXAMPLE_PART_2_N_FILE_NAME.format(n)}")
fun mainFile() = File("${mainDir()}${mainFileName()}")
fun testFile() = File("${testDir()}${testFileName()}")

// Cache & Template Files
fun readmeCacheFile() = File("${resourcesCacheDir()}$README_CACHE_FILE_NAME")
fun inputCacheFile() = File("${resourcesCacheDir()}$INPUT_FILE_NAME")
fun answerCacheFile() = File("${answerCacheDir()}$ANSWER_CACHE_FILE_NAME")
fun exampleCacheFile() = File("${answerCacheDir()}$EXAMPLE_CACHE_FILE_NAME")
fun mainTemplateFile() = File("${templateDir()}$MAIN_TEMPLATE_FILE_NAME")
fun testTemplateFile() = File("${templateDir()}$TEST_TEMPLATE_FILE_NAME")

fun createResourcesDirIfNotExists() = createDirsIfNotExists(resourcesDir())
fun createAnswerCacheDirIfNotExists() = createDirsIfNotExists(answerCacheDir())
fun createResourcesCacheDirIfNotExists() = createDirsIfNotExists(resourcesCacheDir())
fun createSourceDirsIfNotExists() {
  createDirsIfNotExists(mainDir())
  createDirsIfNotExists(testDir())
}

fun resourcesDir() = "${sourceDir(files().resources)}/"

private fun aocHomeDir() = homeOverride ?: AOC_HOME
private fun contentRootDir() = "${project()}${files().modulePrefix}${year()}/"
private fun mainDir() = sourceDir("${files().main}${yearSourceDir()}/")
private fun testDir() = sourceDir("${files().test}${yearSourceDir()}/")
private fun cacheDir() = "${aocHomeDir()}$CACHE_DIR"
private fun resourcesCacheDir() = "${cacheDir()}$FILE_CACHE_DIR/y${year()}/d${day()}/"
private fun answerCacheDir() = "${cacheDir()}$ANSWER_CACHE_DIR/"
private fun templateDir() = "${aocHomeDir()}$TEMPLATE_DIR${fileExt()}/"
private fun sourceDir(type: String) = "${contentRootDir()}${type}${files().dayPrefix}${day()}/"
private fun yearSourceDir() = "${files().yearPrefix}${year()}"
private fun fileExt() = activeProperties().language
private fun mainFileName() = "Day${day()}.${fileExt()}"
private fun testFileName() = "Day${day()}Test.${fileExt()}"
private fun createDirsIfNotExists(dir: String) = File(dir).let {
  if (!it.exists()) it.mkdirs()
}
