package io.github.aj8gh.aoc.properties

import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer

class PropertiesManager(
  private val writer: Writer,
  private val reader: Reader,
  private val propsFiles: PropertyFileManager,
) {

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
    writer.write(newProfile)
  }

  fun setActiveProfile(profile: String) {
    writer.write(propsFiles.aocPropertiesFile(), aocProperties().copy(active = profile))
    readAndSetAocProperties()
    readAndSetActiveProfile()
  }

  private fun readAndSetAocProperties(): AocProperties {
    aocProperties = reader.readYaml(propsFiles.aocPropertiesFile(), AocProperties::class.java)
    return aocProperties!!
  }

  private fun readAndSetActiveProfile(): Profile {
    profile = reader.readYaml(propsFiles.activeProfileFile(), Profile::class.java)
    return profile!!
  }
}
