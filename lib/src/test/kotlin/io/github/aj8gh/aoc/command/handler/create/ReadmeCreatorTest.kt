package io.github.aj8gh.aoc.command.handler.create

import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D5
import io.github.aj8gh.aoc.command.D6
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.Y15
import io.github.aj8gh.aoc.command.Y22
import io.github.aj8gh.aoc.command.Y23
import io.github.aj8gh.aoc.http.SESSION_KEY
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.HTML_DIR
import io.github.aj8gh.aoc.test.HTTP_PORT
import io.github.aj8gh.aoc.test.MARKDOWN_DIR
import io.github.aj8gh.aoc.test.SESSION
import io.github.aj8gh.aoc.test.context.props
import io.github.aj8gh.aoc.test.context.reader
import io.github.aj8gh.aoc.test.html
import io.github.aj8gh.aoc.test.markdown
import io.github.aj8gh.aoc.test.readmeRequestMapping
import io.github.aj8gh.aoc.test.readmeUrl
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@WireMockTest(httpPort = HTTP_PORT)
class ReadmeCreatorTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("readmeNotExistsNotCachedSource")
  fun readmeNotExistsNotCached(year: Int, day: Int, level: Int) {
    GIVEN
      .currentYearDayAndLevelAre(year, day, level)
      .theFollowingRequestStub(readmeRequestMapping(html()))
      .noReadmeExistsForToday()
      .todaysReadmeIsNotCached()

    WHEN
      .createReadmeIsCalled()

    THEN
      .theFollowingRequestWasMade(requestPattern())
      .todaysReadmeIsCreatedCorrectly(markdown())
      .todaysReadmeHasBeenCached(html())
  }

  @ParameterizedTest
  @MethodSource("notStaleSource")
  fun readmeExistsNotStale(readmeLevel: Int, completionLevel: Int) {
    GIVEN
      .currentYearDayAndLevelAre(Y22, D1, L1)
      .todaysReadmeIsNotCached()
      .todaysReadmeExists(markdownForLevel(readmeLevel))
      .todaysCompletionLevelIs(completionLevel)

    WHEN
      .createReadmeIsCalled()

    THEN
      .noRequestsWereMadeForUrl(readmeUrl())
  }

  @ParameterizedTest
  @MethodSource("notStaleSource")
  fun readmeNotExistsCacheNotStale(cacheLevel: Int, completionLevel: Int) {
    GIVEN
      .currentYearDayAndLevelAre(Y22, D1, L1)
      .noReadmeExistsForToday()
      .todaysReadmeIsCached(htmlAtLevel(cacheLevel))
      .todaysCompletionLevelIs(completionLevel)

    WHEN
      .createReadmeIsCalled()

    THEN
      .noRequestsWereMadeForUrl(readmeUrl())
      .todaysReadmeIsCreatedCorrectly(markdownForLevel(cacheLevel))
  }

  @ParameterizedTest
  @MethodSource("staleSource")
  fun readmeNotExistsCacheIsStale(cacheLevel: Int, completionLevel: Int) {
    GIVEN
      .currentYearDayAndLevelAre(Y22, D1, L1)
      .noReadmeExistsForToday()
      .todaysReadmeIsCached(htmlAtLevel(cacheLevel))
      .todaysCompletionLevelIs(completionLevel)
      .theFollowingRequestStub(readmeRequestMapping(htmlAtLevel(completionLevel)))

    WHEN
      .createReadmeIsCalled()

    THEN
      .theFollowingRequestWasMade(requestPattern())
      .todaysReadmeIsCreatedCorrectly(markdownForLevel(completionLevel))
  }

  @ParameterizedTest
  @MethodSource("staleSource")
  fun readmeStaleNotCached(readmeLevel: Int, completionLevel: Int) {
    GIVEN
      .currentYearDayAndLevelAre(Y22, D1, L1)
      .todaysReadmeExists(markdownForLevel(readmeLevel))
      .todaysReadmeIsNotCached()
      .todaysCompletionLevelIs(completionLevel)
      .theFollowingRequestStub(readmeRequestMapping(htmlAtLevel(completionLevel)))

    WHEN
      .createReadmeIsCalled()

    THEN
      .theFollowingRequestWasMade(requestPattern())
      .todaysReadmeIsCreatedCorrectly(markdownForLevel(completionLevel))
      .todaysReadmeHasBeenCached(htmlAtLevel(completionLevel))
  }

  @ParameterizedTest
  @MethodSource("readmeAndCacheStaleSource")
  fun readmeAndCacheStale(readmeLevel: Int, cacheLevel: Int, completionLevel: Int) {
    GIVEN
      .currentYearDayAndLevelAre(Y22, D1, L1)
      .todaysReadmeExists(markdownForLevel(readmeLevel))
      .todaysReadmeIsCached(htmlAtLevel(cacheLevel))
      .todaysCompletionLevelIs(completionLevel)
      .theFollowingRequestStub(readmeRequestMapping(htmlAtLevel(completionLevel)))

    WHEN
      .createReadmeIsCalled()

    THEN
      .theFollowingRequestWasMade(requestPattern())
      .todaysReadmeIsCreatedCorrectly(markdownForLevel(completionLevel))
  }

  @ParameterizedTest
  @MethodSource("readmeStaleCacheNotStaleSource")
  fun readmeStaleCacheNotStale(readmeLevel: Int, cacheLevel: Int, completionLevel: Int) {
    GIVEN.currentYearDayAndLevelAre(Y22, D1, L1)
      .todaysReadmeExists(markdownForLevel(readmeLevel))
      .todaysReadmeIsCached(htmlAtLevel(cacheLevel))
      .todaysCompletionLevelIs(completionLevel)

    WHEN
      .createReadmeIsCalled()

    THEN
      .noRequestsWereMadeForUrl(readmeUrl())
      .todaysReadmeIsCreatedCorrectly(markdownForLevel(cacheLevel))
  }

  private fun htmlAtLevel(level: Int): String {
    val path = "${HTML_DIR}y${props.year()}/level/l$level.html"
    return reader.read(path)
  }

  private fun markdownForLevel(level: Int): String {
    val path = "${MARKDOWN_DIR}y${props.year()}/level/l$level.md"
    return reader.read(path)
  }

  private fun requestPattern() = getRequestedFor(urlEqualTo(readmeUrl()))
    .withCookie(SESSION_KEY, matching(SESSION))

  companion object {

    @JvmStatic
    private fun readmeNotExistsNotCachedSource() = listOf(
      Arguments.of(Y15, D1, L1),
      Arguments.of(Y23, D5, L1),
      Arguments.of(Y23, D6, L1),
    )

    @JvmStatic
    private fun staleSource() = listOf(
      Arguments.of(0, 1),
      Arguments.of(0, 2),
      Arguments.of(1, 2),
    )

    @JvmStatic
    private fun notStaleSource() = listOf(
      Arguments.of(0, 0),
      Arguments.of(1, 1),
      Arguments.of(1, 0),
      Arguments.of(2, 2),
      Arguments.of(2, 1),
      Arguments.of(2, 0),
    )

    @JvmStatic
    private fun readmeStaleCacheNotStaleSource() = listOf(
      Arguments.of(0, 1, 1),
      Arguments.of(0, 2, 1),
      Arguments.of(0, 2, 2),
      Arguments.of(1, 2, 2)
    )

    @JvmStatic
    private fun readmeAndCacheStaleSource() = listOf(
      Arguments.of(0, 0, 1),
      Arguments.of(0, 0, 2),
      Arguments.of(1, 0, 2),
      Arguments.of(0, 1, 2),
      Arguments.of(1, 1, 2),
    )
  }
}
