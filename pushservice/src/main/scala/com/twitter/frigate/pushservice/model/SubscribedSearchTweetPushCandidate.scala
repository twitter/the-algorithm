package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Subscr bedSearchT etCand date
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.model. b s.Subscr bedSearchT et b s2Hydrator
 mport com.tw ter.fr gate.pushserv ce.model.ntab.Subscr bedSearchT etNtabRequestHydrator
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.Bas cT etPred catesForRFPH
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.t etyp e.T etyP e
 mport com.tw ter.ut l.Future

class Subscr bedSearchT etPushCand date(
  cand date: RawCand date w h Subscr bedSearchT etCand date,
  author: Opt on[User],
  copy ds: Copy ds
)(
   mpl c  stats: StatsRece ver,
  pushModelScorer: PushMLModelScorer)
    extends PushCand date
    w h Subscr bedSearchT etCand date
    w h T etAuthorDeta ls
    w h Subscr bedSearchT et b s2Hydrator
    w h Subscr bedSearchT etNtabRequestHydrator {
  overr de def t etAuthor: Future[Opt on[User]] = Future.value(author)

  overr de def   ghtedOpenOrNtabCl ckModelScorer: PushMLModelScorer = pushModelScorer

  overr de def t et d: Long = cand date.t et d

  overr de def pushCopy d: Opt on[ nt] = copy ds.pushCopy d

  overr de def ntabCopy d: Opt on[ nt] = copy ds.ntabCopy d

  overr de def copyAggregat on d: Opt on[Str ng] = copy ds.aggregat on d

  overr de def target: PushTypes.Target = cand date.target

  overr de def searchTerm: Str ng = cand date.searchTerm

  overr de def t  BoundedLand ngUrl: Opt on[Str ng] = None

  overr de def statsRece ver: StatsRece ver = stats

  overr de def t etyP eResult: Opt on[T etyP e.T etyP eResult] = cand date.t etyP eResult
}

case class Subscr bedSearchT etCand datePred cates(overr de val conf g: Conf g)
    extends Bas cT etPred catesForRFPH[Subscr bedSearchT etPushCand date] {
   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )
}
