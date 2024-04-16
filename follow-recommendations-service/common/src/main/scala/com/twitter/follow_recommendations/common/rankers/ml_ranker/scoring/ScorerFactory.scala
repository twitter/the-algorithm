package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.scor ng

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d.Ranker d
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ScorerFactory @ nject() (
  postnuxProdScorer: PostnuxDeepb rdProdScorer,
  randomScorer: RandomScorer,
  stats: StatsRece ver) {

  pr vate val scorerFactoryStats = stats.scope("scorer_factory")
  pr vate val scorerStat = scorerFactoryStats.scope("scorer")

  def getScorers(
    ranker ds: Seq[Ranker d]
  ): Seq[Scorer] = {
    ranker ds.map { scorer d =>
      val scorer: Scorer = getScorerBy d(scorer d)
      // count # of t  s a ranker has been requested
      scorerStat.counter(scorer. d.toStr ng). ncr()
      scorer
    }
  }

  def getScorerBy d(scorer d: Ranker d): Scorer = scorer d match {
    case Ranker d.PostNuxProdRanker =>
      postnuxProdScorer
    case Ranker d.RandomRanker =>
      randomScorer
    case _ =>
      scorerStat.counter(" nval d_scorer_type"). ncr()
      throw new  llegalArgu ntExcept on("unknown_scorer_type")
  }
}
