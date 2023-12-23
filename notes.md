# Notes

## Env
- Set year, day, level
- Next level/day/year

## Submit
- submit answer for current level
- submit answer for given level
- Flow:
  - check if answer is cached already
  - if not cached:
    - submit
    - if answer correct:
      - cache answer
      - advance to next level
      - create / update files for next level
      - cache readme

## Create

### Readme
- check if readme is cached, use cached file if it is
- if not cached:
  - GET readme and create
  - cache readme once level complete
- example input:
  - take all example inputs, save as example files in repo
  - cache example inputs

### Code
- create src file and test files for current / given day
- use cache and / or readme to update test code with example answer 1, example answer 2, answer 1 and answer 2

## Util
- print current config
- print current y/d/l
- open project for default / given ide
- install/uninstall aoc cli tool
