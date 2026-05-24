package com.twitter.search.earlybird;

import scala.Option;

import com.google.common.annotations.VisibleForTesting;

import com.twitter.decider.Decider;

public final class RecentTweetRestriction {
  private static final String RECENT_TWEETS_THRESHOLD = "recent_tweets_threshold";
  private static final String QUERY_CACHE_UNTIL_TIME = "query_cache_until_time";

  @VisibleForTesting
  public static final int DEFAULT_RECENT_TWEET_SECONDS = 15;

  private RecentTweetRestriction() {
  }

  /**
   * Returns the point in time (in seconds past the unix epoch) before which all tweets will be
   * completely indexed. This is required by some clients, because they rely on Earlybird monotonically
   * indexing tweets by ID and that tweets are completely indexed when they see them.
   *
   * @param lastTime The time at which the most recent tweet was indexed, in seconds since the unix
   * epoch.
   */
  public static int recentTweetsUntilTime(Decider decider, int lastTime) {
    return untilTimeSeconds(decider, lastTime, RECENT_TWEETS_THRESHOLD);
  }

  /**
   * Returns the point in time (in seconds past the unix epoch) before which all tweets will be
   * completely indexed. This is required by some clients, because they rely on Earlybird monotonically
   * indexing tweets by ID and that tweets are completely indexed when they see them.
   *
   * @param lastTime The time at which the most recent tweet was indexed, in seconds since the unix
   * epoch.
   */
  public static int queryCacheUntilTime(Decider decider, int lastTime) {
    return untilTimeSeconds(decider, lastTime, QUERY_CACHE_UNTIL_TIME);
  }

  private static int untilTimeSeconds(Decider decider, int lastTime, String deciderKey) {
    int recentTweetSeconds = getRecentTweetSeconds(decider, deciderKey);

    if (recentTweetSeconds == 0) {
      return 0;
    }

    return lastTime - recentTweetSeconds;
  }

  private static int getRecentTweetSeconds(Decider decider, String deciderKey) {
    Option<Object> deciderValue = decider.getAvailability(deciderKey);
    if (deciderValue.isDefined()) {
      return (int) deciderValue.get();
    }
    return DEFAULT_RECENT_TWEET_SECONDS;
  }
}
