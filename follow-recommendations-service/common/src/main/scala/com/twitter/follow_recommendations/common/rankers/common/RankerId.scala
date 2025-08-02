package com.twitter.follow_recommendations.common.rankers.common

object RankerId etwittertends Enumeration {
  type RankerId = Value

  val RandomRanker: RankerId = Value("random")
  // The production PostNUtwitter ML warm-start auto-retraining model ranker
  val PostNutwitterProdRanker: RankerId = Value("postnutwitter_prod")
  val None: RankerId = Value("none")

  // Sampling from the Placket-Luce distribution. Applied after ranker step. Its ranker id is mainly used for logging.
  val PlacketLuceSamplingTransformer: RankerId = Value("placket_luce_sampling_transformer")

  def getRankerByName(name: String): Option[RankerId] =
    RankerId.values.toSeq.find(_.equals(Value(name)))

}

/**
 * ML model based heavy ranker ids.
 */
object ModelBasedHeavyRankerId {
  import RankerId._
  val HeavyRankerIds: Set[String] = Set(
    PostNutwitterProdRanker.toString,
  )
}
