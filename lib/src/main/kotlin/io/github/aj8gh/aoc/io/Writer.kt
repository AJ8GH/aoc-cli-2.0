package io.github.aj8gh.aoc.io

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.aj8gh.aoc.properties.Profile
import io.github.aj8gh.aoc.properties.PropertyFileManager
import java.io.File

class Writer(
  private val propsFiles: PropertyFileManager,
  private val mapper: ObjectMapper,
) {

  fun write(profile: Profile) = mapper.writeValue(propsFiles.activeProfileFile(), profile)

  fun <T> write(file: File, t: T) = mapper.writeValue(file, t)

  fun write(file: File, content: String) = file.writeText(content)
}
