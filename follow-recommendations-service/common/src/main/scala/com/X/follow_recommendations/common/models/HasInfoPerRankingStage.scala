package com.X.follow_recommendations.common.models

trait HasInfoPerRankingStage {
  def infoPerRankingStage: Option[scala.collection.Map[String, RankingInfo]]
}
