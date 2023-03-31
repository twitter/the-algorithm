package com.twitter.simclusters_v2.hdfs_sources

object DataPaths {

  val InterestedIn2020Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_interested_in_20M_145K_2020"

  val InterestedIn2020ThriftPath =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_interested_in_20M_145K_2020_thrift"

  val InterestedInLite2020Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_interested_in_lite_20M_145K_2020"

  val InterestedInLite2020ThriftPath =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_interested_in_lite_20M_145K_2020_thrift"

  val KnownFor2020Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_known_for_20M_145K_2020"

  // keep this inside /user/cassowary/manhattan_sequence_files/ to use the latest 3 retention policy
  val KnownFor2020ThriftDatasetPath =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_known_for_20M_145K_2020_thrift"

  val OfflineClusterTopMediaTweets2020DatasetPath =
    "/user/cassowary/manhattan_sequence_files/cluster_top_media_tweets_20M_145K_2020"
}

/**
 * These should only be accessed from simclusters_v2 data pipeline for intermediate data, these
 * are not opt-out compliant and shouldn't be exposed externally.
 */
object InternalDataPaths {
  // Internal versions, not to be read or written outside of simcluster_v2

  private[simclusters_v2] val RawInterestedIn2020Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_raw_interested_in_20M_145K_2020"

  private[simclusters_v2] val RawInterestedInLite2020Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_raw_interested_in_lite_20M_145K_2020"

  private[simclusters_v2] val RawKnownForDec11Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_raw_known_for_20M_145K_dec11"

  private[simclusters_v2] val RawKnownForUpdatedPath =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_raw_known_for_20M_145K_updated"

  private[simclusters_v2] val RawKnownFor2020Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v2_raw_known_for_20M_145K_2020"
}
