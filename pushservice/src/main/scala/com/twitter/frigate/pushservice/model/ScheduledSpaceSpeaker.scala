package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Sc duledSpaceSpeakerCand date
 mport com.tw ter.fr gate.common.base.SpaceCand dateFanoutDeta ls
 mport com.tw ter.fr gate.common.ut l.FeatureSw chParams
 mport com.tw ter.fr gate.mag c_events.thr ftscala.Space tadata
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.model. b s.Sc duledSpaceSpeaker b s2Hydrator
 mport com.tw ter.fr gate.pushserv ce.model.ntab.Sc duledSpaceSpeakerNTabRequestHydrator
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.SpacePred cate
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.Bas cSendHandlerPred cates
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter. rm .pred cate.Na dPred cate
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ubs.thr ftscala.Aud oSpace
 mport com.tw ter.ut l.Future

class Sc duledSpaceSpeakerPushCand date(
  cand date: RawCand date w h Sc duledSpaceSpeakerCand date,
  hostUser: Opt on[User],
  copy ds: Copy ds,
  aud oSpaceStore: ReadableStore[Str ng, Aud oSpace]
)(
   mpl c  val statsScoped: StatsRece ver,
  pushModelScorer: PushMLModelScorer)
    extends PushCand date
    w h Sc duledSpaceSpeakerCand date
    w h Sc duledSpaceSpeaker b s2Hydrator
    w h SpaceCand dateFanoutDeta ls
    w h Sc duledSpaceSpeakerNTabRequestHydrator {

  overr de val startT  : Long = cand date.startT  

  overr de val hydratedHost: Opt on[User] = hostUser

  overr de val space d: Str ng = cand date.space d

  overr de val host d: Opt on[Long] = cand date.host d

  overr de val speaker ds: Opt on[Seq[Long]] = cand date.speaker ds

  overr de val l stener ds: Opt on[Seq[Long]] = cand date.l stener ds

  overr de val fr gateNot f cat on: Fr gateNot f cat on = cand date.fr gateNot f cat on

  overr de val pushCopy d: Opt on[ nt] = copy ds.pushCopy d

  overr de val ntabCopy d: Opt on[ nt] = copy ds.ntabCopy d

  overr de val copyAggregat on d: Opt on[Str ng] = copy ds.aggregat on d

  overr de val target: Target = cand date.target

  overr de val   ghtedOpenOrNtabCl ckModelScorer: PushMLModelScorer = pushModelScorer

  overr de lazy val aud oSpaceFut: Future[Opt on[Aud oSpace]] = aud oSpaceStore.get(space d)

  overr de val spaceFanout tadata: Opt on[Space tadata] = None

  overr de val statsRece ver: StatsRece ver =
    statsScoped.scope("Sc duledSpaceSpeakerCand date")
}

case class Sc duledSpaceSpeakerCand datePred cates(conf g: Conf g)
    extends Bas cSendHandlerPred cates[Sc duledSpaceSpeakerPushCand date] {

   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )

  overr de val preCand dateSpec f cPred cates: L st[
    Na dPred cate[Sc duledSpaceSpeakerPushCand date]
  ] = L st(
    SpacePred cate.sc duledSpaceStarted(
      conf g.aud oSpaceStore
    ),
    Pred catesForCand date.paramPred cate(FeatureSw chParams.EnableSc duledSpaceSpeakers)
  )
}
