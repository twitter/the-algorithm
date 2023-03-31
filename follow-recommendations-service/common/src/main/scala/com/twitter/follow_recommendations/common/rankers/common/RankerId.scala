package com.twitter.follow_recommendations.common.rankers.common

object RankerId extends Enumeration {
  type RankerId = Value

  val RandomRanker: RankerId = Value("random")
  // The production PostNUX ML warm-start auto-retraining model ranker
  val PostNuxProdRanker: RankerId = Value("postnux_prod")
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
    PostNuxProdRanker.toString,
  )
}
