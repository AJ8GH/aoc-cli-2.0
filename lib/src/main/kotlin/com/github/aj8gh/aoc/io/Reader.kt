package com.github.aj8gh.aoc.io

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.aj8gh.aoc.config.AocProperties
import com.github.aj8gh.aoc.config.Properties

class Reader {

  companion object {

    private val aocConfig = "aoc.yaml"
    private val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

    fun readConfig(): Properties? {
      val aocProps = readYaml(aocConfig, AocProperties::class.java)
      val props = readYaml(
        aocProps.current,
        Properties::class.java
      )
      props.aocProperties = aocProps
      return props
    }

    private fun <T> readYaml(name: String, type: Class<T>) = mapper.readValue(
      read(name),
      type
    )

    private fun read(name: String) = this::class
      .java
      .classLoader
      .getResourceAsStream(name)
  }
}
