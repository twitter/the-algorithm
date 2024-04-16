package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.D scoverTw terCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.model.cand date.Copy ds
 mport com.tw ter.fr gate.pushserv ce.model. b s.D scoverTw terPush b s2Hydrator
 mport com.tw ter.fr gate.pushserv ce.model.ntab.D scoverTw terNtabRequestHydrator
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.Bas cRFPHPred cates
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.OutOfNetworkT etPred cates
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter. rm .pred cate.Na dPred cate

class D scoverTw terPushCand date(
  cand date: RawCand date w h D scoverTw terCand date,
  copy ds: Copy ds,
)(
   mpl c  val statsScoped: StatsRece ver,
  pushModelScorer: PushMLModelScorer)
    extends PushCand date
    w h D scoverTw terCand date
    w h D scoverTw terPush b s2Hydrator
    w h D scoverTw terNtabRequestHydrator {

  overr de val pushCopy d: Opt on[ nt] = copy ds.pushCopy d

  overr de val ntabCopy d: Opt on[ nt] = copy ds.ntabCopy d

  overr de val copyAggregat on d: Opt on[Str ng] = copy ds.aggregat on d

  overr de val target: Target = cand date.target

  overr de lazy val commonRecType: CommonRecom ndat onType = cand date.commonRecType

  overr de val   ghtedOpenOrNtabCl ckModelScorer: PushMLModelScorer = pushModelScorer

  overr de val statsRece ver: StatsRece ver =
    statsScoped.scope("D scoverTw terPushCand date")
}

case class AddressBookPushCand datePred cates(conf g: Conf g)
    extends Bas cRFPHPred cates[D scoverTw terPushCand date] {

   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )

  overr de val pred cates: L st[
    Na dPred cate[D scoverTw terPushCand date]
  ] =
    L st(
      Pred catesForCand date.paramPred cate(
        PushFeatureSw chParams.EnableAddressBookPush
      )
    )
}

case class CompleteOnboard ngPushCand datePred cates(conf g: Conf g)
    extends Bas cRFPHPred cates[D scoverTw terPushCand date] {

   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )

  overr de val pred cates: L st[
    Na dPred cate[D scoverTw terPushCand date]
  ] =
    L st(
      Pred catesForCand date.paramPred cate(
        PushFeatureSw chParams.EnableCompleteOnboard ngPush
      )
    )
}

case class PopGeoT etCand datePred cates(overr de val conf g: Conf g)
    extends OutOfNetworkT etPred cates[OutOfNetworkT etPushCand date] {

   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )

  overr de def postCand dateSpec f cPred cates: L st[
    Na dPred cate[OutOfNetworkT etPushCand date]
  ] = L st(
    Pred catesForCand date.htlFat guePred cate(
      PushFeatureSw chParams.NewUserPlaybookAllo dLastLog nH s
    )
  )
}
