package com.twitter.follow_recommendations.common.rankers.ml_ranker.scoring

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.rankers.common.RankerId
import com.twitter.follow_recommendations.common.rankers.common.RankerId.RankerId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScorerFactory @Inject() (
  postnuxProdScorer: PostnuxDeepbirdProdScorer,
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
    case RankerId.PostNuxProdRanker =>
      postnuxProdScorer
    case RankerId.RandomRanker =>
      randomScorer
    case _ =>
      scorerStat.counter("invalid_scorer_type").incr()
      throw new IllegalArgumentException("unknown_scorer_type")
  }
}
