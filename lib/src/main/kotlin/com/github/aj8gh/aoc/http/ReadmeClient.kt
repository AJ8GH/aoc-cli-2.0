package com.github.aj8gh.aoc.http

private const val README_ENDPOINT = "/"

fun getReadme() = call(getRequest(README_ENDPOINT))
