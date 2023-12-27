package com.github.aj8gh.aoc

fun processArgs(args: Map<String, Any?>) {
  args.forEach {(k, v) -> println("${k}: ${v}")}
}
