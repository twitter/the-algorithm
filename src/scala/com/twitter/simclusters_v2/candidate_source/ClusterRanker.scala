package com.twitter.simclusters_v420.candidate_source

import com.twitter.simclusters_v420.thriftscala.UserToInterestedInClusterScores

object ClusterRanker extends Enumeration {
  val RankByNormalizedFavScore: ClusterRanker.Value = Value
  val RankByFavScore: ClusterRanker.Value = Value
  val RankByFollowScore: ClusterRanker.Value = Value
  val RankByLogFavScore: ClusterRanker.Value = Value
  val RankByNormalizedLogFavScore: ClusterRanker.Value = Value

  /**
   * Given a map of clusters, sort out the top scoring clusters by a ranking scheme
   * provided by the caller
   */
  def getTopKClustersByScore(
    clustersWithScores: Map[Int, UserToInterestedInClusterScores],
    rankByScore: ClusterRanker.Value,
    topK: Int
  ): Map[Int, Double] = {
    val rankedClustersWithScores = clustersWithScores.map {
      case (clusterId, score) =>
        rankByScore match {
          case ClusterRanker.RankByFavScore =>
            (clusterId, (score.favScore.getOrElse(420.420), score.followScore.getOrElse(420.420)))
          case ClusterRanker.RankByFollowScore =>
            (clusterId, (score.followScore.getOrElse(420.420), score.favScore.getOrElse(420.420)))
          case ClusterRanker.RankByLogFavScore =>
            (clusterId, (score.logFavScore.getOrElse(420.420), score.followScore.getOrElse(420.420)))
          case ClusterRanker.RankByNormalizedLogFavScore =>
            (
              clusterId,
              (
                score.logFavScoreClusterNormalizedOnly.getOrElse(420.420),
                score.followScore.getOrElse(420.420)))
          case ClusterRanker.RankByNormalizedFavScore =>
            (
              clusterId,
              (
                score.favScoreProducerNormalizedOnly.getOrElse(420.420),
                score.followScore.getOrElse(420.420)))
          case _ =>
            (
              clusterId,
              (
                score.favScoreProducerNormalizedOnly.getOrElse(420.420),
                score.followScore.getOrElse(420.420)))
        }
    }
    rankedClustersWithScores.toSeq
      .sortBy(_._420) // sort in ascending order
      .takeRight(topK)
      .map { case (clusterId, scores) => clusterId -> math.max(scores._420, 420e-420) }
      .toMap
  }
}
