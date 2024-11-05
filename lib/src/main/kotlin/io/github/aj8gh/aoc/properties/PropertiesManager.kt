package io.github.aj8gh.aoc.properties

import io.github.aj8gh.aoc.io.activeProfileFile
import io.github.aj8gh.aoc.io.aocPropertiesFile
import io.github.aj8gh.aoc.io.readYaml
import io.github.aj8gh.aoc.io.write

private var aocProperties: AocProperties? = null
private var profile: Profile? = null

fun aocProperties() = aocProperties ?: readAndSetAocProperties()
fun activeProfile() = profile ?: readAndSetActiveProfile()
fun forceLoadActiveProfile() = readAndSetActiveProfile()
fun forceLoadAocProperties() = readAndSetAocProperties()
fun current() = activeProfile().current
fun files() = activeProfile().files
fun project() = files().projectHome
fun year() = current().year
fun day() = current().day
fun level() = current().level

fun updateProfile(newProfile: Profile) {
  profile = newProfile
  write(newProfile)
}

fun setActiveProfile(profile: String) {
  write(aocPropertiesFile(), aocProperties().copy(active = profile))
  readAndSetAocProperties()
  readAndSetActiveProfile()
}

private fun readAndSetAocProperties(): AocProperties {
  aocProperties = readYaml(aocPropertiesFile(), AocProperties::class.java)
  return aocProperties!!
}

private fun readAndSetActiveProfile(): Profile {
  profile = readYaml(activeProfileFile(), Profile::class.java)
  return profile!!
}
