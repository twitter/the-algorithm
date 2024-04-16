package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.F1F rstDegree
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.ut l.Future

tra  F1F rstDegreeT et b s2HydratorForCand date
    extends T etCand date b s2Hydrator
    w h RankedSoc alContext b s2Hydrator {
  self: PushCand date w h F1F rstDegree w h T etAuthorDeta ls =>

  overr de lazy val scopedStats: StatsRece ver = statsRece ver.scope(getClass.getS mpleNa )

  overr de lazy val t etModelValues: Future[Map[Str ng, Str ng]] = {
    for {
      superModelValues <- super.t etModelValues
      t et nl neModelValues <- t et nl neAct onModelValue
    } y eld {
      superModelValues ++ ot rModelValues ++  d aModelValue ++ t et nl neModelValues ++  nl neV deo d aMap
    }
  }
}
