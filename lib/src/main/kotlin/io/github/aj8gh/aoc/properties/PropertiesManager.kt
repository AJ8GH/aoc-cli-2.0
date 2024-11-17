package io.github.aj8gh.aoc.properties

import io.github.aj8gh.aoc.context.ApplicationProperties
import io.github.aj8gh.aoc.io.Logger
import io.github.aj8gh.aoc.io.Reader
import io.github.aj8gh.aoc.io.Writer
import java.io.File

class PropertiesManager(
  private val writer: Writer,
  private val reader: Reader,
  private val files: ApplicationProperties.Files,
  private val log: Logger,
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
    writer.write(File(files.activeProfile(newProfile.name)), newProfile)
  }

  fun setActiveProfile(profile: String) {
    writer.write(File(files.aocProperties()), aocProperties().copy(active = profile))
    readAndSetAocProperties()
    readAndSetActiveProfile()
  }

  private fun readAndSetAocProperties(): AocProperties {
    aocProperties = reader.readYaml(aocPropertiesFile(), AocProperties::class.java)
    log.info("AoC properties set from ${aocPropertiesFile().absolutePath}")
    return aocProperties!!
  }

  private fun readAndSetActiveProfile(): Profile {
    profile = reader.readYaml(activeProfileFile(), Profile::class.java)
    log.info("Active profile set from ${activeProfileFile().absolutePath}")
    return profile!!
  }

  private fun aocPropertiesFile() = File(files.aocProperties())
  private fun activeProfileFile() = File(files.activeProfile(aocProperties().active))
}
