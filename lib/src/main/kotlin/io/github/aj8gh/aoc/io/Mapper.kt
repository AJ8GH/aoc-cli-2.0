package io.github.aj8gh.aoc.io

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
