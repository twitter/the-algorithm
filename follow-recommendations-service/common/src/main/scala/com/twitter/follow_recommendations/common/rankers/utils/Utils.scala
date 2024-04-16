package com.tw ter.follow_recom ndat ons.common.rankers.ut ls

 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Score
 mport com.tw ter.follow_recom ndat ons.common.rankers.common.Ranker d.Ranker d

object Ut ls {

  /**
   * Add t  rank ng and scor ng  nfo for a l st of cand dates on a g ven rank ng stage.
   * @param cand dates A l st of Cand dateUser
   * @param rank ngStage Should use `Ranker.na ` as t  rank ng stage.
   * @return T  l st of Cand dateUser w h rank ng/scor ng  nfo added.
   */
  def addRank ng nfo(cand dates: Seq[Cand dateUser], rank ngStage: Str ng): Seq[Cand dateUser] = {
    cand dates.z pW h ndex.map {
      case (cand date, rank) =>
        // 1-based rank ng for better readab l y
        cand date.add nfoPerRank ngStage(rank ngStage, cand date.scores, rank + 1)
    }
  }

  def getCand dateScoreByRanker d(cand date: Cand dateUser, ranker d: Ranker d): Opt on[Score] =
    cand date.scores.flatMap { ss => ss.scores.f nd(_.ranker d.conta ns(ranker d)) }

  def getAllRanker ds(cand dates: Seq[Cand dateUser]): Seq[Ranker d] =
    cand dates.flatMap(_.scores.map(_.scores.flatMap(_.ranker d))).flatten.d st nct
}
