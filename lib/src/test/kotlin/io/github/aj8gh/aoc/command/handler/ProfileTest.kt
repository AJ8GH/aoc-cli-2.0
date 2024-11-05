package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ProfileTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputSource")
  fun profile(currentProfile: String, newProfile: String) {
    givenActivePropertiesIsSetTo(currentProfile)

    whenProfileIsCalledWith(newProfile)

    thenTheFollowingProfileIsActive(newProfile)
    andTheFollowingMessageIsEchoed("Active profile is now \"$newProfile\"")
  }

  companion object {
    @JvmStatic
    private fun inputSource(): List<Arguments> {
      return listOf(
        Arguments.of(KT_PROFILE, GO_PROFILE),
        Arguments.of(GO_PROFILE, KT_PROFILE)
      )
    }
  }
}
