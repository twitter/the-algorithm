package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Soc alContextAct on
 mport com.tw ter.fr gate.common.base.Soc alContextUserDeta ls
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etFavor eCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.model. b s.T etFavor eCand date b s2Hydrator
 mport com.tw ter.fr gate.pushserv ce.model.ntab.T etFavor eNTabRequestHydrator
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateHydrat onUt l.T etW hSoc alContextTra s
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.t etyp e.T etyP e
 mport com.tw ter.ut l.Future

class T etFavor ePushCand date(
  cand date: RawCand date w h T etW hSoc alContextTra s,
  soc alContextUserMap: Future[Map[Long, Opt on[User]]],
  author: Future[Opt on[User]],
  copy ds: Copy ds
)(
   mpl c  stats: StatsRece ver,
  pushModelScorer: PushMLModelScorer)
    extends PushCand date
    w h T etFavor eCand date
    w h Soc alContextUserDeta ls
    w h T etAuthorDeta ls
    w h T etFavor eNTabRequestHydrator
    w h T etFavor eCand date b s2Hydrator {
  overr de val statsRece ver: StatsRece ver = stats
  overr de val   ghtedOpenOrNtabCl ckModelScorer: PushMLModelScorer = pushModelScorer
  overr de val t et d: Long = cand date.t et d
  overr de val soc alContextAct ons: Seq[Soc alContextAct on] =
    cand date.soc alContextAct ons

  overr de val soc alContextAllTypeAct ons: Seq[Soc alContextAct on] =
    cand date.soc alContextAllTypeAct ons

  overr de lazy val scUserMap: Future[Map[Long, Opt on[User]]] = soc alContextUserMap
  overr de lazy val t etAuthor: Future[Opt on[User]] = author
  overr de lazy val commonRecType: CommonRecom ndat onType =
    cand date.commonRecType
  overr de val target: PushTypes.Target = cand date.target
  overr de lazy val t etyP eResult: Opt on[T etyP e.T etyP eResult] =
    cand date.t etyP eResult
  overr de val pushCopy d: Opt on[ nt] = copy ds.pushCopy d
  overr de val ntabCopy d: Opt on[ nt] = copy ds.ntabCopy d
  overr de val copyAggregat on d: Opt on[Str ng] = copy ds.aggregat on d
}
