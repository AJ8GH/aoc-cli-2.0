package io.github.aj8gh.aoc.command.handler

import io.github.aj8gh.aoc.command.PROFILE_SHORT
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.GO_PROFILE
import io.github.aj8gh.aoc.test.KT_PROFILE
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ProfileHandlerTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("inputSource")
  fun profile(currentProfile: String, newProfile: String) {
    GIVEN
      .activeProfileIs(currentProfile)

    WHEN
      .theAppIsRunWithArgs(listOf(PROFILE_SHORT, newProfile))

    THEN
      .theFollowingProfileIsActive(newProfile)
      .theFollowingMessageIsEchoed("Active profile is now \"$newProfile\"")
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
