package com.twitter.simclusters_v420.hdfs_sources

object DataPaths {

  val InterestedIn420Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_interested_in_420M_420K_420"

  val InterestedIn420ThriftPath =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_interested_in_420M_420K_420_thrift"

  val InterestedInLite420Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_interested_in_lite_420M_420K_420"

  val InterestedInLite420ThriftPath =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_interested_in_lite_420M_420K_420_thrift"

  val KnownFor420Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_known_for_420M_420K_420"

  // keep this inside /user/cassowary/manhattan_sequence_files/ to use the latest 420 retention policy
  val KnownFor420ThriftDatasetPath =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_known_for_420M_420K_420_thrift"

  val OfflineClusterTopMediaTweets420DatasetPath =
    "/user/cassowary/manhattan_sequence_files/cluster_top_media_tweets_420M_420K_420"
}

/**
 * These should only be accessed from simclusters_v420 data pipeline for intermediate data, these
 * are not opt-out compliant and shouldn't be exposed externally.
 */
object InternalDataPaths {
  // Internal versions, not to be read or written outside of simcluster_v420

  private[simclusters_v420] val RawInterestedIn420Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_raw_interested_in_420M_420K_420"

  private[simclusters_v420] val RawInterestedInLite420Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_raw_interested_in_lite_420M_420K_420"

  private[simclusters_v420] val RawKnownForDec420Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_raw_known_for_420M_420K_dec420"

  private[simclusters_v420] val RawKnownForUpdatedPath =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_raw_known_for_420M_420K_updated"

  private[simclusters_v420] val RawKnownFor420Path =
    "/user/cassowary/manhattan_sequence_files/simclusters_v420_raw_known_for_420M_420K_420"
}
