package io.github.aj8gh.aoc.util

import com.github.ajalt.clikt.completion.CompletionCandidates

fun rangeOf(start: Int, end: Int) = CompletionCandidates.Fixed(
  (start..end)
    .map(Int::toString)
    .toSet()
)
