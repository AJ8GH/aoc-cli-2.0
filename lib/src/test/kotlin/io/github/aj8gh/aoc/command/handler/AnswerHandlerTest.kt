package io.github.aj8gh.aoc.command.handler

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.responseDefinition
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.resetAllRequests
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import com.github.tomakehurst.wiremock.http.Body
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import io.github.aj8gh.aoc.command.ANSWER_SHORT
import io.github.aj8gh.aoc.command.D1
import io.github.aj8gh.aoc.command.D2
import io.github.aj8gh.aoc.command.D3
import io.github.aj8gh.aoc.command.D4
import io.github.aj8gh.aoc.command.L1
import io.github.aj8gh.aoc.command.L2
import io.github.aj8gh.aoc.command.Y15
import io.github.aj8gh.aoc.command.Y25
import io.github.aj8gh.aoc.test.ANSWER
import io.github.aj8gh.aoc.test.BaseTest
import io.github.aj8gh.aoc.test.HTTP_PORT
import io.github.aj8gh.aoc.test.KT_PROFILE
import io.github.aj8gh.aoc.test.SESSION
import io.github.aj8gh.aoc.test.getEchoMessage
import io.github.aj8gh.aoc.test.getInputMapping
import io.github.aj8gh.aoc.test.html
import io.github.aj8gh.aoc.test.inputUrl
import io.github.aj8gh.aoc.test.markdown
import io.github.aj8gh.aoc.test.readmeRequestMapping
import io.github.aj8gh.aoc.test.readmeUrl
import io.github.aj8gh.aoc.test.steps.GIVEN
import io.github.aj8gh.aoc.test.steps.THEN
import io.github.aj8gh.aoc.test.steps.WHEN
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

private const val LEVEL_KEY = "level"
private const val ANSWER_KEY = "answer"
private const val SESSION_KEY = "session"
private const val UNKNOWN = "UNKNOWN"

@WireMockTest(httpPort = HTTP_PORT)
class AnswerHandlerTest : BaseTest() {

  @Test
  fun answer_HappyPath_NoCache() {
    GIVEN
      .theFollowingRequestStub(answerPostMapping(CORRECT_RESPONSE))
      .theFollowingRequestStub(readmeRequestMapping(html()))
      .theFollowingRequestStub(getInputMapping())

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .theFollowingRequestWasMade(answerPostPattern())
      .currentYearDayAndLevelAre(Y15, D1, L2)
      .theAnswerIsCachedFor(expected = ANSWER, year = Y15, day = D1, level = L2)
      .theCurrentDayReadmeLevelIs(L2)
      .theFollowingMessagesAreEchoed(
        CORRECT,
        "Creating README file...",
        getEchoMessage(Y15, D1, L2, KT_PROFILE),
      )

    resetAllRequests()
    // Answer should now be cached

    GIVEN
      .currentYearDayAndLevelAre(Y15, D1, L1)

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .noRequestsWereMadeForUrl(answerUrl(Y15, D1))
      .currentYearDayAndLevelAre(Y15, D1, L2)
      .theFollowingMessagesAreEchoed(
        CORRECT,
        "Creating README file...",
        getEchoMessage(Y15, D1, L2, KT_PROFILE),
      )
  }

  @Test
  fun answer_HappyPath_NoCacheLevel2() {
    GIVEN
      .theFollowingRequestStub(answerPostMapping(CORRECT_RESPONSE))
      .theFollowingRequestStub(
        readmeRequestMapping(
          html().lines()
            .subList(0, 119)
            .joinToString("") + "\nThe first half of this puzzle is complete! It provides one gold star: *"
        )
      )
      .theFollowingRequestStub(getInputMapping())

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .currentYearDayAndLevelAre(Y15, D1, L2)
      .theCurrentDayReadmeLevelIs(expected = L1)
      .theFollowingRequestWasMade(answerPostPattern())

    resetAllRequests()

    GIVEN
      .currentYearDayAndLevelAre(Y15, D1, L2)
      .theFollowingRequestStub(answerPostMapping(CORRECT_RESPONSE, L2))
      .theFollowingRequestStub(readmeRequestMapping(html()))
      .theFollowingRequestStub(readmeRequestMapping(html(day = D2), day = D2))
      .theFollowingRequestStub(getInputMapping(day = D2))

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .currentYearDayAndLevelAre(Y15, D2, L1)
      .theAnswerIsCachedFor(expected = ANSWER, year = Y15, day = D1, level = L2)
      .theReadmeLevelForDayIs(day = D1, expected = L2)
      .todaysReadmeIsCreatedCorrectly(markdown())
  }

  @Test
  fun answer_HappyPath_NoCacheLevel2_MaxYearAndDay() {
    GIVEN
      .currentDateTimeIs("2025-12-04T04:00:00.000Z")
      .currentYearDayAndLevelAre(Y25, D3, L2)
      .theFollowingRequestStub(answerPostMapping(CORRECT_RESPONSE, Y25, D3, L2))
      .theFollowingRequestStub(readmeRequestMapping(html()))

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .theFollowingRequestWasMade(answerPostPattern(Y25, D3, L2))
      .currentYearDayAndLevelAre(Y25, D4, L1)
      .noRequestsWereMadeForUrl(readmeUrl(Y25, D4))
      .noRequestsWereMadeForUrl(inputUrl(Y25, D4))
      .noRequestsWereMadeForUrl(answerUrl(Y25, D4))
  }

  @Test
  fun answer_HappyPath_Cached() {
    GIVEN
      .currentYearDayAndLevelAre(year = Y15, day = D1, level = L2)
      .theFollowingRequestStub(getInputMapping(day = D2))
      .theFollowingRequestStub(readmeRequestMapping(html(day = D2), day = D2))
      .theFollowingRequestStub(readmeRequestMapping(html()))

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .noRequestsWereMadeForUrl(answerUrl(Y15, D1))
      .currentYearDayAndLevelAre(Y15, D2, L1)
      .todaysReadmeIsCreatedCorrectly(markdown())
      .todaysReadmeHasBeenCached(html())
      .todaysInputExists()
  }

  @ParameterizedTest
  @MethodSource("inputProvider")
  fun answer_SadPath_Cached(answer: String, expectedResponse: String) {
    GIVEN
      .currentYearDayAndLevelAre(year = Y15, day = D1, level = L2)

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, answer))

    THEN
      .noRequestsWereMadeForUrl(answerUrl(Y15, D1))
      .currentYearDayAndLevelAre(Y15, D1, L2)
      .theFollowingMessageIsEchoed(expectedResponse)
  }

  @ParameterizedTest
  @ValueSource(strings = [TOO_LOW, TOO_HIGH, INCORRECT, WRONG_LEVEL, UNKNOWN])
  fun answer_SadPath_NoCache(expectedResponse: String) {
    GIVEN
      .theFollowingRequestStub(answerPostMapping(expectedResponse))

    WHEN
      .theAppIsRunWithArgs(listOf(ANSWER_SHORT, ANSWER))

    THEN
      .theFollowingRequestWasMade(answerPostPattern())
      .currentYearDayAndLevelAre(Y15, D1, L1)
      .theFollowingMessageIsEchoed(expectedResponse)
  }

  private fun answerPostMapping(
    response: String,
    year: Int = Y15,
    day: Int = D1,
    level: Int = L1
  ) = post(urlPathEqualTo(answerUrl(year, day)))
    .withCookie(SESSION_KEY, matching(SESSION))
    .withFormParam(LEVEL_KEY, equalTo(level.toString()))
    .withFormParam(ANSWER_KEY, equalTo(ANSWER))
    .willReturn(
      responseDefinition()
        .withStatus(200)
        .withResponseBody(Body(response))
    )

  private fun answerPostPattern(
    year: Int = Y15,
    day: Int = D1,
    level: Int = L1,
  ) = postRequestedFor(urlPathEqualTo(answerUrl(year, day)))
    .withCookie(SESSION_KEY, matching(SESSION))
    .withFormParam(LEVEL_KEY, equalTo(level.toString()))
    .withFormParam(ANSWER_KEY, equalTo(ANSWER))

  private fun answerUrl(year: Int, day: Int) = "/20$year/day/$day/answer"

  companion object {

    @JvmStatic
    private fun inputProvider() = listOf(
      Arguments.of("122", TOO_LOW),
      Arguments.of("124", TOO_HIGH),
      Arguments.of("abc", INCORRECT),
    )
  }
}

private const val CORRECT_RESPONSE = """
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="utf-8"/>
<title>Day 1 - Advent of Code 2024</title>
<link rel="stylesheet" type="text/css" href="/static/style.css?31"/>
<link rel="stylesheet alternate" type="text/css" href="/static/highcontrast.css?1" title="High Contrast"/>
<link rel="shortcut icon" href="/favicon.png"/>
<script>window.addEventListener('click', function(e,s,r){if(e.target.nodeName==='CODE'&&e.detail===3){s=window.getSelection();s.removeAllRanges();r=document.createRange();r.selectNodeContents(e.target);s.addRange(r);}});</script>
</head><!--




Oh, hello!  Funny seeing you here.

I appreciate your enthusiasm, but you aren't going to find much down here.
There certainly aren't clues to any of the puzzles.  The best surprises don't
even appear in the source until you unlock them for real.

Please be careful with automated requests; I'm not a massive company, and I can
only take so much traffic.  Please be considerate so that everyone gets to play.

If you're curious about how Advent of Code works, it's running on some custom
Perl code. Other than a few integrations (auth, analytics, social media), I
built the whole thing myself, including the design, animations, prose, and all
of the puzzles.

The puzzles are most of the work; preparing a new calendar and a new set of
puzzles each year takes all of my free time for 4-5 months. A lot of effort
went into building this thing - I hope you're enjoying playing it as much as I
enjoyed making it for you!

If you'd like to hang out, I'm @was.tl on Bluesky, @ericwastl@hachyderm.io on
Mastodon, and @ericwastl on Twitter.

- Eric Wastl


















































-->
<body>
<header><div><h1 class="title-global"><a href="/">Advent of Code</a></h1><nav><ul><li><a href="/2024/about">[About]</a></li><li><a href="/2024/events">[Events]</a></li><li><a href="https://cottonbureau.com/people/advent-of-code" target="_blank">[Shop]</a></li><li><a href="/2024/settings">[Settings]</a></li><li><a href="/2024/auth/logout">[Log Out]</a></li></ul></nav><div class="user">Adam Jonas <span class="star-count">1*</span></div></div><div><h1 class="title-event">&nbsp;&nbsp;<span class="title-event-wrap">0.0.0.0:</span><a href="/2024">2024</a><span class="title-event-wrap"></span></h1><nav><ul><li><a href="/2024">[Calendar]</a></li><li><a href="/2024/support">[AoC++]</a></li><li><a href="/2024/sponsors">[Sponsors]</a></li><li><a href="/2024/leaderboard">[Leaderboard]</a></li><li><a href="/2024/stats">[Stats]</a></li></ul></nav></div></header>

<div id="sidebar">
<div id="sponsor"><div class="quiet">Our <a href="/2024/sponsors">sponsors</a> help make Advent of Code possible:</div><div class="sponsor"><a href="/2024/sponsors/redirect?url=https%3A%2F%2Fwww%2Eplaystation%2Ecom%2Fen%2Dus%2Fcorporate%2Fplaystation%2Dcareers%2F" target="_blank" onclick="if(ga)ga('send','event','sponsor','sidebar',this.href);" rel="noopener">Sony Interactive Entertainment</a> - Pushing the boundaries of Play! A O X #</div></div>
</div><!--/sidebar-->

<main>
<article><p>That's the right answer!  You are <span class="day-success">one gold star</span> closer to finding the Chief Historian. <a href="/2024/day/1#part2">[Continue to Part Two]</a></p></article>
</main>

<!-- ga -->
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-69522494-1', 'auto');
ga('set', 'anonymizeIp', true);
ga('send', 'pageview');
</script>
<!-- /ga -->
</body>
</html>
"""
