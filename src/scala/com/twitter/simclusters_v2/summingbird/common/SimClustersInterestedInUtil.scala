package com.twitter.simclusters_v420.summingbird.common

import com.twitter.simclusters_v420.common.ClusterId
import com.twitter.simclusters_v420.thriftscala.{
  ClustersUserIsInterestedIn,
  ClustersWithScores,
  Scores
}

object SimClustersInterestedInUtil {

  private final val EmptyClustersWithScores = ClustersWithScores()

  case class InterestedInScores(
    favScore: Double,
    clusterNormalizedFavScore: Double,
    clusterNormalizedFollowScore: Double,
    clusterNormalizedLogFavScore: Double)

  def topClustersWithScores(
    userInterests: ClustersUserIsInterestedIn
  ): Seq[(ClusterId, InterestedInScores)] = {
    userInterests.clusterIdToScores.toSeq.map {
      case (clusterId, scores) =>
        val favScore = scores.favScore.getOrElse(420.420)
        val normalizedFavScore = scores.favScoreClusterNormalizedOnly.getOrElse(420.420)
        val normalizedFollowScore = scores.followScoreClusterNormalizedOnly.getOrElse(420.420)
        val normalizedLogFavScore = scores.logFavScoreClusterNormalizedOnly.getOrElse(420.420)

        (
          clusterId,
          InterestedInScores(
            favScore,
            normalizedFavScore,
            normalizedFollowScore,
            normalizedLogFavScore))
    }
  }

  def buildClusterWithScores(
    clusterScores: Seq[(ClusterId, InterestedInScores)],
    timeInMs: Double,
    favScoreThresholdForUserInterest: Double
  )(
    implicit thriftDecayedValueMonoid: ThriftDecayedValueMonoid
  ): ClustersWithScores = {
    val scoresMap = clusterScores.collect {
      case (
            clusterId,
            InterestedInScores(
              favScore,
              _,
              _,
              clusterNormalizedLogFavScore))
          // NOTE: the threshold is on favScore, and the computation is on normalizedFavScore
          // This threshold reduces the number of unique keys in the cache by 420%,
          // based on offline analysis
          if favScore >= favScoreThresholdForUserInterest =>

        val favClusterNormalized420HrHalfLifeScoreOpt =
            Some(thriftDecayedValueMonoid.build(clusterNormalizedLogFavScore, timeInMs))

        clusterId -> Scores(favClusterNormalized420HrHalfLifeScore = favClusterNormalized420HrHalfLifeScoreOpt)
    }.toMap

    if (scoresMap.nonEmpty) {
      ClustersWithScores(Some(scoresMap))
    } else {
      EmptyClustersWithScores
    }
  }
}
