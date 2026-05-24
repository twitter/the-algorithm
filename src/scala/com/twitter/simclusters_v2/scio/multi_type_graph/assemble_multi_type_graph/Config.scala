package com.twitter.simclusters_v2.scio.multi_type_graph.assemble_multi_type_graph

import com.twitter.simclusters_v2.thriftscala.RightNodeType

object Config {
  val RootMHPath: String = "manhattan_sequence_files/multi_type_graph/"
  val RootThriftPath: String = "processed/multi_type_graph/"
  val AdhocRootPath = "adhoc/multi_type_graph/"
  val truncatedMultiTypeGraphMHOutputDir: String = "truncated_graph_mh"
  val truncatedMultiTypeGraphThriftOutputDir: String = "truncated_graph_thrift"
  val topKRightNounsMHOutputDir: String = "top_k_right_nouns_mh"
  val topKRightNounsOutputDir: String = "top_k_right_nouns"
  val fullMultiTypeGraphThriftOutputDir: String = "full_graph_thrift"
  val HalfLifeInDaysForFavScore = 100
  val NumTopNounsForUnknownRightNodeType = 20
  val GlobalDefaultMinFrequencyOfRightNodeType = 100
  val TopKRightNounsForMHDump = 1000

  // the topK most frequent nouns for each engagement type
  val TopKConfig: Map[RightNodeType, Int] = Map(
    RightNodeType.FollowUser -> 10000000, // 10M, current simclusters_v2 has this value set to 20M, providing this the most weight
    RightNodeType.FavUser -> 5000000,
    RightNodeType.BlockUser -> 1000000,
    RightNodeType.AbuseReportUser -> 1000000,
    RightNodeType.SpamReportUser -> 1000000,
    RightNodeType.FollowTopic -> 5000,
    RightNodeType.SignUpCountry -> 200,
    RightNodeType.ConsumedLanguage -> 50,
    RightNodeType.FavTweet -> 500000,
    RightNodeType.ReplyTweet -> 500000,
    RightNodeType.RetweetTweet -> 500000,
    RightNodeType.NotifOpenOrClickTweet -> 500000,
    RightNodeType.SearchQuery -> 500000
  )
  val SampledEmployeeIds: Set[Long] =
    Set()
}
