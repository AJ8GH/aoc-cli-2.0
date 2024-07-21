package io.github.aj8gh.aoc.util

import com.github.ajalt.clikt.completion.CompletionCandidates

fun toCandidates(intRange: IntRange) = CompletionCandidates
  .Fixed(intRange.map(Int::toString).toSet())
