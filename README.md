# AOC 2.0

2nd iteration of AOC CLI tool. Initial implementation was a collection of shell
and python scripts: https://github.com/AJ8GH/aoc-cli. This project adds more
features, is fully tested, and implements caching to reduce unnecessary HTTP
requests to the aoc website.

Utility application to manage advent of code projects.

## Tech

- Kotlin 2.0.0
- Java 21
- J-Unit
- Clikt (Kotlin CLI library)
- Http4k

## Getting started

- Build and install by running the install script:
  _Note: the script assumes that the install location should be
  `$XDG_CONFIG_HOME/.aoc` and that the location of this project once cloned will
  be `$HOME/Projects/aoc-2.0`_

```sh
sh install.sh
```

- Create config folder

```sh
mkdir $XDG_CONFIG_HOME/.aoc/
```

- This repo can be used as a guide for the contents:
  https://github.com/AJ8GH/aoc-config-backup/tree/main/template.
  No need to copy the cache folders, they will be created by the application
  when needed.
- Add an addtional file `aoc.yaml` at the root of that folder, with:

```yaml
---
url: "https://adventofcode.com"
active: "go"
session: <your-session-token>
```

- Get the session token from dev tools in the advent of code website when
  authenticated.

## Usage

Documentation can be viewed by using the `-h` or `--help` command:

```sh
aoc -h
```

Example output:

```
Usage: aoc [<options>]

Options:
-y, --year=(15|16|17|18|19|20|21|22|23|24)
New year value to set, must be between 15 and current
year
-d, -day=(1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25)
New day value to set, must be between 1 and 25.
-l, --level=(1|2) New level value to set, must be 1 or 2.
-t, --token=<text> Update advent of code session token.
-a, --answer=<text> Submit answer for current level.
-p, --profile=<text> Switch to the given profile.
-n, --next Advance to the next level.
-e, --echo Echo the current year, day and level.
-o, --open Open project with the configured IDE.
-f, --files Open the aoc home directory with the configured IDE.
-s, --stats Show AoC completion stats.
-c, --create Create resources for current level.
-v, --verbose Make output more verbose.
-w, --web Open the AoC web url.
-h, --help Show this message and exit
```
