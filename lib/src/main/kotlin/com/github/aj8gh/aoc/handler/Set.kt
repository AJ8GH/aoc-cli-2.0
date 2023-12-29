package com.github.aj8gh.aoc.handler

import com.github.aj8gh.aoc.config.getCurrentProperties
import com.github.aj8gh.aoc.config.updateProperties

fun set(year: Int?, day: Int?, level: Int?) {
  val properties = getCurrentProperties()
  year?.let { properties!!.current.year = it }
  day?.let { properties!!.current.day = it }
  level?.let { properties!!.current.level = it }
  updateProperties(properties!!)
  println(getCurrentProperties())
}
