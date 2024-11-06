package io.github.aj8gh.aoc.cache.answer

data class Year(val days: MutableMap<Int, Day> = mutableMapOf()) {

  fun day(day: Int) = days.getOrDefault(day, Day())

  fun save(day: Int, level: Int, answer: String) = days
    .computeIfAbsent(day) { Day() }
    .save(level, answer)

  fun completion() = days.values
    .map { it.completion() }
    .fold(0) { a, b -> a + b }
}
