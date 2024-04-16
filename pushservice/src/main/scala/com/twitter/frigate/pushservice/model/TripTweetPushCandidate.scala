package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.OutOfNetworkT etCand date
 mport com.tw ter.fr gate.common.base.Top cCand date
 mport com.tw ter.fr gate.common.base.Tr pCand date
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.model. b s.OutOfNetworkT et b s2HydratorForCand date
 mport com.tw ter.fr gate.pushserv ce.model.ntab.OutOfNetworkT etNTabRequestHydrator
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.OutOfNetworkT etPred cates
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.t etyp e.T etyP e
 mport com.tw ter.top cl st ng.utt.Local zedEnt y
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pDoma n
 mport com.tw ter.ut l.Future

class Tr pT etPushCand date(
  cand date: RawCand date w h OutOfNetworkT etCand date w h Tr pCand date,
  author: Future[Opt on[User]],
  copy ds: Copy ds
)(
   mpl c  stats: StatsRece ver,
  pushModelScorer: PushMLModelScorer)
    extends PushCand date
    w h Tr pCand date
    w h Top cCand date
    w h OutOfNetworkT etCand date
    w h T etAuthorDeta ls
    w h OutOfNetworkT etNTabRequestHydrator
    w h OutOfNetworkT et b s2HydratorForCand date {
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
  overr de lazy val semant cCoreEnt y d: Opt on[Long] = None
  overr de lazy val local zedUttEnt y: Opt on[Local zedEnt y] = None
  overr de lazy val algor hmCR: Opt on[Str ng] = None
  overr de val tr pDoma n: Opt on[collect on.Set[Tr pDoma n]] = cand date.tr pDoma n
}

case class Tr pT etCand datePred cates(overr de val conf g: Conf g)
    extends OutOfNetworkT etPred cates[Tr pT etPushCand date] {

   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )

}
