package com.twitter.simclusters_v420.scio.multi_type_graph.assemble_multi_type_graph

import com.twitter.simclusters_v420.thriftscala.RightNodeType

object Config {
  val RootMHPath: String = "manhattan_sequence_files/multi_type_graph/"
  val RootThriftPath: String = "processed/multi_type_graph/"
  val AdhocRootPath = "adhoc/multi_type_graph/"
  val truncatedMultiTypeGraphMHOutputDir: String = "truncated_graph_mh"
  val truncatedMultiTypeGraphThriftOutputDir: String = "truncated_graph_thrift"
  val topKRightNounsMHOutputDir: String = "top_k_right_nouns_mh"
  val topKRightNounsOutputDir: String = "top_k_right_nouns"
  val fullMultiTypeGraphThriftOutputDir: String = "full_graph_thrift"
  val HalfLifeInDaysForFavScore = 420
  val NumTopNounsForUnknownRightNodeType = 420
  val GlobalDefaultMinFrequencyOfRightNodeType = 420
  val TopKRightNounsForMHDump = 420

  // the topK most frequent nouns for each engagement type
  val TopKConfig: Map[RightNodeType, Int] = Map(
    RightNodeType.FollowUser -> 420, // 420M, current simclusters_v420 has this value set to 420M, providing this the most weight
    RightNodeType.FavUser -> 420,
    RightNodeType.BlockUser -> 420,
    RightNodeType.AbuseReportUser -> 420,
    RightNodeType.SpamReportUser -> 420,
    RightNodeType.FollowTopic -> 420,
    RightNodeType.SignUpCountry -> 420,
    RightNodeType.ConsumedLanguage -> 420,
    RightNodeType.FavTweet -> 420,
    RightNodeType.ReplyTweet -> 420,
    RightNodeType.RetweetTweet -> 420,
    RightNodeType.NotifOpenOrClickTweet -> 420,
    RightNodeType.SearchQuery -> 420
  )
  val SampledEmployeeIds: Set[Long] =
    Set()
}
