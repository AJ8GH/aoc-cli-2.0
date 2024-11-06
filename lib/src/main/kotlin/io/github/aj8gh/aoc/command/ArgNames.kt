package io.github.aj8gh.aoc.command

const val YEAR_SHORT = "-y"
const val YEAR_LONG = "--year"
const val YEAR_DESCRIPTION = "New year value to set, must be between 15 and current year"

const val DAY_SHORT = "-d"
const val DAY_LONG = "-day"
const val DAY_DESCRIPTION = "New day value to set, must be between 1 and 25."

const val LEVEL_SHORT = "-l"
const val LEVEL_LONG = "--level"
const val LEVEL_DESCRIPTION = "New level value to set, must be 1 or 2."

const val NEXT_SHORT = "-n"
const val NEXT_LONG = "--next"
const val NEXT_DESCRIPTION = "Advance to the next level."

const val ECHO_SHORT = "-e"
const val ECHO_LONG = "--echo"
const val ECHO_DESCRIPTION = "Echo the current year, day and level."

const val CREATE_SHORT = "-c"
const val CREATE_LONG = "--create"
const val CREATE_DESCRIPTION = "Create resources for current level."

const val ANSWER_SHORT = "-a"
const val ANSWER_LONG = "--answer"
const val ANSWER_DESCRIPTION = "Submit answer for current level."

const val PROFILE_SHORT = "-p"
const val PROFILE_LONG = "--profile"
const val PROFILE_DESCRIPTION = "Switch to the given profile."

const val OPEN_SHORT = "-o"
const val OPEN_LONG = "--open"
const val OPEN_DESCRIPTION = "Open project with the configured IDE."

const val FILES_SHORT = "-f"
const val FILES_LONG = "--files"
const val FILES_DESCRIPTION = "Open the aoc home directory with the configured IDE."

const val TOKEN_SHORT = "-t"
const val TOKEN_LONG = "--token"
const val TOKEN_DESCRIPTION = "Update advent of code session token."

const val VERBOSE_SHORT = "-v"
const val VERBOSE_LONG = "--verbose"
const val VERBOSE_DESCRIPTION = "Make output more verbose."
