package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Mag cFanoutProductLaunchCand date
 mport com.tw ter.fr gate.common.ut l.{FeatureSw chParams => FS}
 mport com.tw ter.fr gate.mag c_events.thr ftscala.Mag cEventsReason
 mport com.tw ter.fr gate.mag c_events.thr ftscala.ProductType
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.pred cate.mag c_fanout.Mag cFanoutPred catesUt l
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.model. b s.Mag cFanoutProductLaunch b s2Hydrator
 mport com.tw ter.fr gate.pushserv ce.model.ntab.Mag cFanoutProductLaunchNtabRequestHydrator
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.mag c_fanout.Mag cFanoutPred catesForCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue.Mag cFanoutNtabCaretFat guePred cate
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.Bas cSendHandlerPred cates
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter. rm .pred cate.Na dPred cate

class Mag cFanoutProductLaunchPushCand date(
  cand date: RawCand date w h Mag cFanoutProductLaunchCand date,
  copy ds: Copy ds
)(
   mpl c  val statsScoped: StatsRece ver,
  pushModelScorer: PushMLModelScorer)
    extends PushCand date
    w h Mag cFanoutProductLaunchCand date
    w h Mag cFanoutProductLaunch b s2Hydrator
    w h Mag cFanoutProductLaunchNtabRequestHydrator {

  overr de val fr gateNot f cat on: Fr gateNot f cat on = cand date.fr gateNot f cat on

  overr de val pushCopy d: Opt on[ nt] = copy ds.pushCopy d

  overr de val ntabCopy d: Opt on[ nt] = copy ds.ntabCopy d

  overr de val push d: Long = cand date.push d

  overr de val productLaunchType: ProductType = cand date.productLaunchType

  overr de val cand dateMag cEventsReasons: Seq[Mag cEventsReason] =
    cand date.cand dateMag cEventsReasons

  overr de val copyAggregat on d: Opt on[Str ng] = copy ds.aggregat on d

  overr de val target: Target = cand date.target

  overr de val   ghtedOpenOrNtabCl ckModelScorer: PushMLModelScorer = pushModelScorer

  overr de val statsRece ver: StatsRece ver =
    statsScoped.scope("Mag cFanoutProductLaunchPushCand date")
}

case class Mag cFanoutProductLaunchPushCand datePred cates(conf g: Conf g)
    extends Bas cSendHandlerPred cates[Mag cFanoutProductLaunchPushCand date] {

   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )

  overr de val preCand dateSpec f cPred cates: L st[
    Na dPred cate[Mag cFanoutProductLaunchPushCand date]
  ] =
    L st(
      Pred catesForCand date. sDev ceEl g bleForCreatorPush,
      Pred catesForCand date.exceptedPred cate(
        "excepted_ s_target_blue_ver f ed",
        Mag cFanoutPred catesUt l.shouldSk pBlueVer f edC ckForCand date,
        Pred catesForCand date. sTargetBlueVer f ed.fl p
      ), // no need to send  f target  s already Blue Ver f ed
      Pred catesForCand date.exceptedPred cate(
        "excepted_ s_target_legacy_ver f ed",
        Mag cFanoutPred catesUt l.shouldSk pLegacyVer f edC ckForCand date,
        Pred catesForCand date. sTargetLegacyVer f ed.fl p
      ), // no need to send  f target  s already Legacy Ver f ed
      Pred catesForCand date.exceptedPred cate(
        "excepted_ s_target_super_follow_creator",
        Mag cFanoutPred catesUt l.shouldSk pSuperFollowCreatorC ckForCand date,
        Pred catesForCand date. sTargetSuperFollowCreator.fl p
      ), // no need to send  f target  s already Super Follow Creator
      Pred catesForCand date.paramPred cate(
        FS.EnableMag cFanoutProductLaunch
      ),
      Mag cFanoutPred catesForCand date.mag cFanoutProductLaunchFat gue(),
    )

  overr de val postCand dateSpec f cPred cates: L st[
    Na dPred cate[Mag cFanoutProductLaunchPushCand date]
  ] =
    L st(
      Mag cFanoutNtabCaretFat guePred cate(),
    )
}
