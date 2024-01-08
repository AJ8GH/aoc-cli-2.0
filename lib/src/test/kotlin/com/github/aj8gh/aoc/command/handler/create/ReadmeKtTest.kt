package com.github.aj8gh.aoc.command.handler.create

import com.github.aj8gh.aoc.*
import com.github.aj8gh.aoc.http.SESSION_KEY
import com.github.aj8gh.aoc.io.read
import com.github.aj8gh.aoc.properties.day
import com.github.aj8gh.aoc.properties.year
import com.github.aj8gh.aoc.util.*
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

private const val HTML_DIR = "${TEST_RESOURCES_ROOT}html/"
private const val MARKDOWN_DIR = "${TEST_RESOURCES_ROOT}markdown/"

@WireMockTest(httpPort = HTTP_PORT)
class ReadmeKtTest : BaseTest() {

  @ParameterizedTest
  @MethodSource("readmeNotExistsNotCached")
  fun readmeNotExistsNotCached(year: Int, day: Int, level: Int) {
    givenCurrentYearDayAndLevelAre(year, day, level)
    andTheFollowingRequestStub(requestMapping(html()))
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

    thenNoRequestsWereMadeForUrl(url())
  }

  @ParameterizedTest
  @MethodSource("notStale")
  fun readmeNotExistsCacheNotStale(cacheLevel: Int, completionLevel: Int) {
    givenCurrentYearDayAndLevelAre(Y22, D1, L1)
    andNoReadmeExistsForToday()
    andTodaysReadmeIsCached(htmlAtLevel(cacheLevel))
    andTodaysCompletionLevelIs(completionLevel)

    whenCreateReadmeIsCalled()

    thenNoRequestsWereMadeForUrl(url())
    andTodaysReadmeIsCreatedCorrectly(markdownForLevel(cacheLevel))
  }

  @ParameterizedTest
  @MethodSource("stale")
  fun readmeNotExistsCacheIsStale(cacheLevel: Int, completionLevel: Int) {
    givenCurrentYearDayAndLevelAre(Y22, D1, L1)
    andNoReadmeExistsForToday()
    andTodaysReadmeIsCached(htmlAtLevel(cacheLevel))
    andTodaysCompletionLevelIs(completionLevel)
    andTheFollowingRequestStub(requestMapping(htmlAtLevel(completionLevel)))

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
    andTheFollowingRequestStub(requestMapping(htmlAtLevel(completionLevel)))

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
    andTheFollowingRequestStub(requestMapping(htmlAtLevel(completionLevel)))

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

    thenNoRequestsWereMadeForUrl(url())
    andTodaysReadmeIsCreatedCorrectly(markdownForLevel(cacheLevel))
  }

  private fun requestMapping(response: String) = get(url())
    .withCookie(SESSION_KEY, matching(SESSION))
    .willReturn(
      responseDefinition()
        .withResponseBody(Body(response))
    )

  private fun html() = read("${HTML_DIR}y${year()}/d${day()}.html")

  private fun htmlAtLevel(level: Int) = read("${HTML_DIR}y${year()}/l$level.html")

  private fun markdown() = read("${MARKDOWN_DIR}y${year()}/d${day()}.md")

  private fun markdownForLevel(level: Int) = read("${MARKDOWN_DIR}y${year()}/l$level.md")

  private fun requestPattern() = getRequestedFor(urlEqualTo(url()))
    .withCookie(SESSION_KEY, matching(SESSION))

  private fun url() = "/20${year()}/day/${day()}"

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
