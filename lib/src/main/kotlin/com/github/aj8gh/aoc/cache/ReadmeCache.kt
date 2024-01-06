package com.github.aj8gh.aoc.cache

import com.github.aj8gh.aoc.command.handler.create.README_FILE_NAME
import com.github.aj8gh.aoc.properties.aocHome
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year

private const val README_CACHE = "cache/files/"

fun readmeCacheFile() = "${readmeCacheDir()}$README_FILE_NAME"

private fun readmeCacheDir() = "${aocHome()}${README_CACHE}y${year()}/d${day()}/"
