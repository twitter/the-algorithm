package com.twitter.simclusters_v420.scalding
package multi_type_graph.assemble_multi_type_graph

import com.twitter.simclusters_v420.thriftscala.RightNodeType

object Config {

  val User = System.getenv("USER")
  val RootPath: String = s"/user/$User/manhattan_sequence_files/multi_type_simclusters/"
  val RootThriftPath: String = s"/user/$User/processed/multi_type_simclusters/"
  val AdhocRootPrefix = s"/gcs/user/$User/adhoc/multi_type_simclusters/"
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
