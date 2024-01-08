# Notes

## Env [x]
* [x] Set year, day, level
* [x] Next level/day/year

## Submit
* [x] submit answer for current level
* [x] Flow:
  * [x] check if answer is cached already
  * [x] if not cached:
    * [x] submit
    * [x] if answer correct:
      * [x] cache answer
      * [x] advance to next level
      * [ ] create / update files for next level
      * [x] cache readme

## Create

### Input [x]
* [x] check if input is cached, use cached input if it is
* [x] if not cached:
  * [x] GET input and create
  * [x] cache input

### Readme [x]
* [x] check if readme is cached
* [x] if not cached:
  * [x] GET readme and create
  * [x] cache readme
* [x] if readme is cached
  * [x] GET readme if not up to date
  * [x] Use cached readme if up to date

### Example
* [ ] example input:
  * [ ] take all example inputs, save as example files in repo
  * [ ] cache example inputs

### Code
* [ ] create src file and test files for current / given day
* [ ] use cache and / or readme to update test code with example answer 1, example answer 2, answer 1 and answer 2

## Util
* [x] print current y/d/l
* [ ] print current config / properties
* [ ] update config / properties
* [ ] open project for default / given ide
* [ ] install/uninstall aoc cli tool
* [ ] create new projects with new properties
* [ ] override `$AOC_HOME` location
