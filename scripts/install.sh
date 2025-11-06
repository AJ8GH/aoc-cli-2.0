#!/bin/sh

/Users/adamjonas/Projects/aoc-2.0/gradlew :lib:jar

ts=$(date +"%s")
mv "$XDG_CONFIG_HOME/.aoc/jars/current.jar" "$XDG_CONFIG_HOME/.aoc/jars/old-$ts.jar"
cp "$HOME/Projects/aoc-2.0/lib/build/libs/lib-0.0.1-SNAPSHOT.jar" "$XDG_CONFIG_HOME/.aoc/jars/current.jar"

if alias aoc >/dev/null 2>&1; then
  echo "alias aoc ='kotlin $XDG_CONFIG_HOME/.aoc/jars/current.jar'"
fi
