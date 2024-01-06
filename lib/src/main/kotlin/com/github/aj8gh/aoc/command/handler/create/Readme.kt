package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.http.getReadme
import java.io.File

const val README_FILE_NAME = "README.md"

fun readme() = getReadme()

fun readmeFile() = File("${getResourcesDir()}/${README_FILE_NAME}")
