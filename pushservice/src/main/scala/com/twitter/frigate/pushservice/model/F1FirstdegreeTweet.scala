package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.F1F rstDegree
 mport com.tw ter.fr gate.common.base.Soc alContextAct on
 mport com.tw ter.fr gate.common.base.Soc alGraphServ ceRelat onsh pMap
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes._
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.model. b s.F1F rstDegreeT et b s2HydratorForCand date
 mport com.tw ter.fr gate.pushserv ce.model.ntab.F1F rstDegreeT etNTabRequestHydrator
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.Bas cT etPred catesForRFPHW houtSGSPred cates
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateHydrat onUt l.T etW hSoc alContextTra s
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter. rm .pred cate.soc algraph.Relat onEdge
 mport com.tw ter.st ch.t etyp e.T etyP e
 mport com.tw ter.ut l.Future

class F1T etPushCand date(
  cand date: RawCand date w h T etW hSoc alContextTra s,
  author: Future[Opt on[User]],
  soc alGraphServ ceResultMap: Map[Relat onEdge, Boolean],
  copy ds: Copy ds
)(
   mpl c  stats: StatsRece ver,
  pushModelScorer: PushMLModelScorer)
    extends PushCand date
    w h F1F rstDegree
    w h T etAuthorDeta ls
    w h Soc alGraphServ ceRelat onsh pMap
    w h F1F rstDegreeT etNTabRequestHydrator
    w h F1F rstDegreeT et b s2HydratorForCand date {
  overr de val soc alContextAct ons: Seq[Soc alContextAct on] =
    cand date.soc alContextAct ons
  overr de val soc alContextAllTypeAct ons: Seq[Soc alContextAct on] =
    cand date.soc alContextAct ons
  overr de val statsRece ver: StatsRece ver = stats
  overr de val   ghtedOpenOrNtabCl ckModelScorer: PushMLModelScorer = pushModelScorer
  overr de val t et d: Long = cand date.t et d
  overr de lazy val t etyP eResult: Opt on[T etyP e.T etyP eResult] =
    cand date.t etyP eResult
  overr de lazy val t etAuthor: Future[Opt on[User]] = author
  overr de val target: PushTypes.Target = cand date.target
  overr de lazy val commonRecType: CommonRecom ndat onType =
    cand date.commonRecType
  overr de val pushCopy d: Opt on[ nt] = copy ds.pushCopy d
  overr de val ntabCopy d: Opt on[ nt] = copy ds.ntabCopy d
  overr de val copyAggregat on d: Opt on[Str ng] = copy ds.aggregat on d

  overr de val relat onsh pMap: Map[Relat onEdge, Boolean] = soc alGraphServ ceResultMap
}

case class F1T etCand datePred cates(overr de val conf g: Conf g)
    extends Bas cT etPred catesForRFPHW houtSGSPred cates[F1T etPushCand date] {
   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )
}
