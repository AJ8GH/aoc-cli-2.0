package io.github.aj8gh.aoc.io

import io.github.aj8gh.aoc.properties.Profile
import java.io.File

fun write(profile: Profile) = mapper.writeValue(activeProfileFile(), profile)

fun <T> write(file: File, t: T) = mapper.writeValue(file, t)

fun write(file: File, content: String) = file.writeText(content)
