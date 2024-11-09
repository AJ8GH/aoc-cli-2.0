package io.github.aj8gh.aoc.cache.answer

import io.github.aj8gh.aoc.command.Y15

data class Answers(
  val cache: MutableMap<Int, Year> = mutableMapOf(Y15 to Year())
) {

  fun year(year: Int) = cache.getOrDefault(year, Year())

  fun clear(year: Int, day: Int) = year(year).day(day).clear()

  fun get(year: Int, day: Int, level: Int) = year(year).day(day).level(level)

  fun get(year: Int, day: Int) = year(year).day(day)

  fun save(year: Int, day: Int, level: Int, answer: String) = cache
    .computeIfAbsent(year) { Year() }
    .save(day, level, answer)

  fun completion() = cache.values
    .map { it.completion() }
    .fold(0) { a, b -> a + b }
}
