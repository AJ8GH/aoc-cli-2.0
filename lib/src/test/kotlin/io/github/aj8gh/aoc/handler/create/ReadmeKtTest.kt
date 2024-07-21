package io.github.aj8gh.aoc.handler.create

import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.github.aj8gh.aoc.*
import io.github.aj8gh.aoc.http.SESSION_KEY
import io.github.aj8gh.aoc.io.read
import io.github.aj8gh.aoc.properties.year
import io.github.aj8gh.aoc.util.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@WireMockTest(httpPort = HTTP_PORT)
class ReadmeKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("readmeNotExistsNotCached")
  fun readmeNotExistsNotCached(year: Int, day: Int, level: Int) {
    givenCurrentYearDayAndLevelAre(year, day, level)
    andTheFollowingRequestStub(readmeRequestMapping(html()))
    andNoReadmeExistsForToday()
    andTodaysReadmeIsNotCached()

    whenCreateReadmeIsCalled()

    thenTheFollowingRequestWasMade(requestPattern())
    andTodaysReadmeIsCreatedCorrectly(markdown())
    andTodaysReadmeHasBeenCached(html())
  }

  @ParameterizedTest
  @MethodSource("notStale")
  fun readmeExistsNotStale(readmeLevel: Int, completionLevel: Int) {
    givenCurrentYearDayAndLevelAre(Y22, D1, L1)
    andTodaysReadmeIsNotCached()
    andTodaysReadmeExists(markdownForLevel(readmeLevel))
    andTodaysCompletionLevelIs(completionLevel)

    whenCreateReadmeIsCalled()

    thenNoRequestsWereMadeForUrl(readmeUrl())
  }

  @ParameterizedTest
  @MethodSource("notStale")
  fun readmeNotExistsCacheNotStale(cacheLevel: Int, completionLevel: Int) {
    givenCurrentYearDayAndLevelAre(Y22, D1, L1)
    andNoReadmeExistsForToday()
    andTodaysReadmeIsCached(htmlAtLevel(cacheLevel))
    andTodaysCompletionLevelIs(completionLevel)

    whenCreateReadmeIsCalled()

    thenNoRequestsWereMadeForUrl(readmeUrl())
    andTodaysReadmeIsCreatedCorrectly(markdownForLevel(cacheLevel))
  }

  @ParameterizedTest
  @MethodSource("stale")
  fun readmeNotExistsCacheIsStale(cacheLevel: Int, completionLevel: Int) {
    givenCurrentYearDayAndLevelAre(Y22, D1, L1)
    andNoReadmeExistsForToday()
    andTodaysReadmeIsCached(htmlAtLevel(cacheLevel))
    andTodaysCompletionLevelIs(completionLevel)
    andTheFollowingRequestStub(readmeRequestMapping(htmlAtLevel(completionLevel)))

    whenCreateReadmeIsCalled()

    thenTheFollowingRequestWasMade(requestPattern())
    andTodaysReadmeIsCreatedCorrectly(markdownForLevel(completionLevel))
  }

  @ParameterizedTest
  @MethodSource("stale")
  fun readmeStaleNotCached(readmeLevel: Int, completionLevel: Int) {
    givenCurrentYearDayAndLevelAre(Y22, D1, L1)
    andTodaysReadmeExists(markdownForLevel(readmeLevel))
    andTodaysReadmeIsNotCached()
    andTodaysCompletionLevelIs(completionLevel)
    andTheFollowingRequestStub(readmeRequestMapping(htmlAtLevel(completionLevel)))

    whenCreateReadmeIsCalled()

    thenTheFollowingRequestWasMade(requestPattern())
    andTodaysReadmeIsCreatedCorrectly(markdownForLevel(completionLevel))
    andTodaysReadmeHasBeenCached(htmlAtLevel(completionLevel))
  }

  @ParameterizedTest
  @MethodSource("readmeAndCacheStale")
  fun readmeAndCacheStale(readmeLevel: Int, cacheLevel: Int, completionLevel: Int) {
    givenCurrentYearDayAndLevelAre(Y22, D1, L1)
    andTodaysReadmeExists(markdownForLevel(readmeLevel))
    andTodaysReadmeIsCached(htmlAtLevel(cacheLevel))
    andTodaysCompletionLevelIs(completionLevel)
    andTheFollowingRequestStub(readmeRequestMapping(htmlAtLevel(completionLevel)))

    whenCreateReadmeIsCalled()

    thenTheFollowingRequestWasMade(requestPattern())
    andTodaysReadmeIsCreatedCorrectly(markdownForLevel(completionLevel))
  }

  @ParameterizedTest
  @MethodSource("readmeStaleCacheNotStale")
  fun readmeStaleCacheNotStale(readmeLevel: Int, cacheLevel: Int, completionLevel: Int) {
    givenCurrentYearDayAndLevelAre(Y22, D1, L1)
    andTodaysReadmeExists(markdownForLevel(readmeLevel))
    andTodaysReadmeIsCached(htmlAtLevel(cacheLevel))
    andTodaysCompletionLevelIs(completionLevel)

    whenCreateReadmeIsCalled()

    thenNoRequestsWereMadeForUrl(readmeUrl())
    andTodaysReadmeIsCreatedCorrectly(markdownForLevel(cacheLevel))
  }

  private fun htmlAtLevel(level: Int) = read("${HTML_DIR}y${year()}/level/l$level.html")

  private fun markdownForLevel(level: Int) = read("${MARKDOWN_DIR}y${year()}/level/l$level.md")

  private fun requestPattern() = getRequestedFor(urlEqualTo(readmeUrl()))
    .withCookie(SESSION_KEY, matching(SESSION))

  companion object {

    @JvmStatic
    private fun readmeNotExistsNotCached() = listOf(
      Arguments.of(Y15, D1, L1),
      Arguments.of(Y23, D5, L1),
      Arguments.of(Y23, D6, L1),
    )

    @JvmStatic
    private fun stale() = listOf(
      Arguments.of(0, 1),
      Arguments.of(0, 2),
      Arguments.of(1, 2),
    )

    @JvmStatic
    private fun notStale() = listOf(
      Arguments.of(0, 0),
      Arguments.of(1, 1),
      Arguments.of(1, 0),
      Arguments.of(2, 2),
      Arguments.of(2, 1),
      Arguments.of(2, 0),
    )

    @JvmStatic
    private fun readmeStaleCacheNotStale() = listOf(
      Arguments.of(0, 1, 1),
      Arguments.of(0, 2, 1),
      Arguments.of(0, 2, 2),
      Arguments.of(1, 2, 2)
    )

    @JvmStatic
    private fun readmeAndCacheStale() = listOf(
      Arguments.of(0, 0, 1),
      Arguments.of(0, 0, 2),
      Arguments.of(1, 0, 2),
      Arguments.of(0, 1, 2),
      Arguments.of(1, 1, 2),
    )
  }
}
