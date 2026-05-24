package com.twitter.follow_recommendations.common.models

import com.twitter.follow_recommendations.{thriftscala => t}
import com.twitter.follow_recommendations.logging.{thriftscala => offline}

case class RankingInfo(
  scores: Option[Scores],
  rank: Option[Int]) {

  def toThrift: t.RankingInfo = {
    t.RankingInfo(scores.map(_.toThrift), rank)
  }

  def toOfflineThrift: offline.RankingInfo = {
    offline.RankingInfo(scores.map(_.toOfflineThrift), rank)
  }
}

object RankingInfo {

  def fromThrift(rankingInfo: t.RankingInfo): RankingInfo = {
    RankingInfo(
      scores = rankingInfo.scores.map(Scores.fromThrift),
      rank = rankingInfo.rank
    )
  }

}
