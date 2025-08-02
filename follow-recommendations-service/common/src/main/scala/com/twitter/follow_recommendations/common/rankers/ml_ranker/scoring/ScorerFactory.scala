package com.twitter.follow_recommendations.common.rankers.ml_ranker.scoring

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.rankers.common.RankerId
import com.twitter.follow_recommendations.common.rankers.common.RankerId.RankerId
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ScorerFactory @Inject() (
  postnutwitterProdScorer: PostnutwitterDeepbirdProdScorer,
  randomScorer: RandomScorer,
  stats: StatsReceiver) {

  private val scorerFactoryStats = stats.scope("scorer_factory")
  private val scorerStat = scorerFactoryStats.scope("scorer")

  def getScorers(
    rankerIds: Seq[RankerId]
  ): Seq[Scorer] = {
    rankerIds.map { scorerId =>
      val scorer: Scorer = getScorerById(scorerId)
      // count # of times a ranker has been requested
      scorerStat.counter(scorer.id.toString).incr()
      scorer
    }
  }

  def getScorerById(scorerId: RankerId): Scorer = scorerId match {
    case RankerId.PostNutwitterProdRanker =>
      postnutwitterProdScorer
    case RankerId.RandomRanker =>
      randomScorer
    case _ =>
      scorerStat.counter("invalid_scorer_type").incr()
      throw new IllegalArgumentEtwitterception("unknown_scorer_type")
  }
}
