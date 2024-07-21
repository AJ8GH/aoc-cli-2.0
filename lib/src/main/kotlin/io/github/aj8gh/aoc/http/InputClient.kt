package io.github.aj8gh.aoc.http

private const val INPUT_ENDPOINT = "/input"

fun getInput() = call(getRequest(INPUT_ENDPOINT))
