package com.twitter.search.common.relevance.features;

/**
 * Defines relevance related constants that are used at both ingestion time and
 * earlybird scoring time.
 */
public final class RelevanceSignalConstants {
  // user reputation
  public static final byte UNSET_REPUTATION_SENTINEL = Byte.MIN_VALUE;
  public static final byte MAX_REPUTATION = 100;
  public static final byte MIN_REPUTATION = 0;
  // below overall CDF of ~10%, default value for new users,
  // given as a goodwill value in case it is unset
  public static final byte GOODWILL_REPUTATION = 17;

  // text score
  public static final byte UNSET_TEXT_SCORE_SENTINEL = Byte.MIN_VALUE;
  // roughly at overall CDF of ~10%, given as a goodwill value in case it is unset
  public static final byte GOODWILL_TEXT_SCORE = 19;

  private RelevanceSignalConstants() {
  }

  // check whether the specified user rep value is valid
  public static boolean isValidUserReputation(int userRep) {
    return userRep != UNSET_REPUTATION_SENTINEL
           && userRep >= MIN_REPUTATION
           && userRep < MAX_REPUTATION;
  }
}
