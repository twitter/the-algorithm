package com.twitter.search.common.util.earlybird;

import com.twitter.search.earlybird.thrift.ThriftSearchResultsRelevanceStats;

public final class ThriftSearchResultsRelevanceStatsUtil {
  private ThriftSearchResultsRelevanceStatsUtil() { }

  /**
   * Adding ThriftSearchResultsRelevanceStats from one set of results onto a base set.
   * Assumes all values are set on both of the inputs.
   *
   * @param base the stats to add to.
   * @param delta the stats to be added.
   */
  public static void addRelevanceStats(ThriftSearchResultsRelevanceStats base,
                                       ThriftSearchResultsRelevanceStats delta) {
    base.setNumScored(base.getNumScored() + delta.getNumScored());
    base.setNumSkipped(base.getNumSkipped() + delta.getNumSkipped());
    base.setNumSkippedForAntiGaming(
            base.getNumSkippedForAntiGaming() + delta.getNumSkippedForAntiGaming());
    base.setNumSkippedForLowReputation(
            base.getNumSkippedForLowReputation() + delta.getNumSkippedForLowReputation());
    base.setNumSkippedForLowTextScore(
            base.getNumSkippedForLowTextScore() + delta.getNumSkippedForLowTextScore());
    base.setNumSkippedForSocialFilter(
            base.getNumSkippedForSocialFilter() + delta.getNumSkippedForSocialFilter());
    base.setNumSkippedForLowFinalScore(
            base.getNumSkippedForLowFinalScore() + delta.getNumSkippedForLowFinalScore());
    if (delta.getOldestScoredTweetAgeInSeconds() > base.getOldestScoredTweetAgeInSeconds()) {
      base.setOldestScoredTweetAgeInSeconds(delta.getOldestScoredTweetAgeInSeconds());
    }

    base.setNumFromDirectFollows(base.getNumFromDirectFollows() + delta.getNumFromDirectFollows());
    base.setNumFromTrustedCircle(base.getNumFromTrustedCircle() + delta.getNumFromTrustedCircle());
    base.setNumReplies(base.getNumReplies() + delta.getNumReplies());
    base.setNumRepliesTrusted(base.getNumRepliesTrusted() + delta.getNumRepliesTrusted());
    base.setNumRepliesOutOfNetwork(
            base.getNumRepliesOutOfNetwork() + delta.getNumRepliesOutOfNetwork());
    base.setNumSelfTweets(base.getNumSelfTweets() + delta.getNumSelfTweets());
    base.setNumWithMedia(base.getNumWithMedia() + delta.getNumWithMedia());
    base.setNumWithNews(base.getNumWithNews() + delta.getNumWithNews());
    base.setNumSpamUser(base.getNumSpamUser() + delta.getNumSpamUser());
    base.setNumOffensive(base.getNumOffensive() + delta.getNumOffensive());
    base.setNumBot(base.getNumBot() + delta.getNumBot());
  }
}
