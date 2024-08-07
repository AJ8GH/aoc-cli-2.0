package io.github.aj8gh.aoc.command

const val YEAR_SHORT = "-y"
const val YEAR_LONG = "--year"
const val YEAR_DESCRIPTION = "New year value to set, must be between 15 and current year"

const val DAY_SHORT = "-d"
const val DAY_LONG = "-day"
const val DAY_DESCRIPTION = "New day value to set, must be between 1 and 25"

const val LEVEL_SHORT = "-s"
const val LEVEL_LONG = "--short"
const val LEVEL_DESCRIPTION = "New level value to set, must be 1 or 2"

const val NEXT_SHORT = "-n"
const val NEXT_LONG = "--next"
const val NEXT_DESCRIPTION = "Advance to the next level"


const val ECHO_SHORT = "-e"
const val ECHO_LONG = "--echo"
const val ECHO_DESCRIPTION = "Echo the current year, day and level"


const val CREATE_SHORT = "-c"
const val CREATE_LONG = "--create"
const val CREATE_DESCRIPTION = "Create resources for current level"


const val ANSWER_SHORT = "-a"
const val ANSWER_LONG = "--answer"
const val ANSWER_DESCRIPTION = "Submit answer for current level"
