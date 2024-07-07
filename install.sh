#!/bin/sh

/Users/adamjonas/Projects/aoc-2.0/gradlew :lib:jar

ts=$(date +"%s")
mv "/Users/adamjonas/.config/.aoc/jars/current.jar" "/Users/adamjonas/.config/.aoc/jars/old-$ts.jar"
cp "/Users/adamjonas/Projects/aoc-2.0/lib/build/libs/lib.jar" "/Users/adamjonas/.config/.aoc/jars/current.jar"
