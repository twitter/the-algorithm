package com.twitter.search.common.schema.earlybird;

import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

/**
 * A list of existing Earlybird clusters.
 */
public enum EarlybirdCluster {
  /**
   * Realtime earlybird cluster. Has 100% of tweet for about 7 days.
   */
  REALTIME,
  /**
   * Protected earlybird cluster. Has only tweets from protected accounts.
   */
  PROTECTED,
  /**
   * Full archive cluster. Has all tweets until about 2 days ago.
   */
  FULL_ARCHIVE,
  /**
   * SuperRoot cluster. Talks to the other clusters instead of talking directly to earlybirds.
   */
  SUPERROOT,

  /**
   * A dedicated cluster for Candidate Generation use cases based on Earlybird in Home/PushService
   */
  REALTIME_CG;

  public String getNameForStats() {
    return name().toLowerCase();
  }

  public static boolean isArchive(EarlybirdCluster cluster) {
    return isClusterInSet(cluster, ARCHIVE_CLUSTERS);
  }

  public static boolean isTwitterMemoryFormatCluster(EarlybirdCluster cluster) {
    return isClusterInSet(cluster, TWITTER_IN_MEMORY_INDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS);
  }

  public static boolean hasEarlybirds(EarlybirdCluster cluster) {
    return cluster != SUPERROOT;
  }

  private static boolean isClusterInSet(EarlybirdCluster cluster, Set<EarlybirdCluster> set) {
    return set.contains(cluster);
  }

  protected static final ImmutableSet<EarlybirdCluster> ARCHIVE_CLUSTERS =
      ImmutableSet.of(FULL_ARCHIVE);

  @VisibleForTesting
  public static final ImmutableSet<EarlybirdCluster>
          TWITTER_IN_MEMORY_INDEX_FORMAT_GENERAL_PURPOSE_CLUSTERS =
      ImmutableSet.of(
          REALTIME,
          PROTECTED);

  @VisibleForTesting
  public static final ImmutableSet<EarlybirdCluster> TWITTER_IN_MEMORY_INDEX_FORMAT_ALL_CLUSTERS =
      ImmutableSet.of(
          REALTIME,
          PROTECTED,
          REALTIME_CG);

  /**
   * Constant for field used in general purpose clusters,
   * Note that GENERAL_PURPOSE_CLUSTERS does not include REALTIME_CG. If you wish to include REALTIME_CG,
   * please use ALL_CLUSTERS
   */
  protected static final ImmutableSet<EarlybirdCluster> GENERAL_PURPOSE_CLUSTERS =
      ImmutableSet.of(
          REALTIME,
          PROTECTED,
          FULL_ARCHIVE,
          SUPERROOT);

  protected static final ImmutableSet<EarlybirdCluster> ALL_CLUSTERS =
      ImmutableSet.of(
          REALTIME,
          PROTECTED,
          FULL_ARCHIVE,
          SUPERROOT,
          REALTIME_CG);
}
