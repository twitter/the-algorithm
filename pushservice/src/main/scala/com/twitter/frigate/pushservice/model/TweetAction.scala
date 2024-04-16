package com.tw ter.fr gate.pushserv ce.model

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Soc alContextAct ons
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.base.T etDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes._
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.pred cate._
 mport com.tw ter.fr gate.pushserv ce.take.pred cates.Bas cT etPred catesForRFPH

case class T etAct onCand datePred cates(overr de val conf g: Conf g)
    extends Bas cT etPred catesForRFPH[
      PushCand date w h T etCand date w h T etDeta ls w h Soc alContextAct ons
    ] {

   mpl c  val statsRece ver: StatsRece ver = conf g.statsRece ver.scope(getClass.getS mpleNa )

  overr de val preCand dateSpec f cPred cates = L st(Pred catesForCand date.m nSoc alContext(1))

  overr de val postCand dateSpec f cPred cates = L st(
    Pred catesForCand date.soc alContextBe ngFollo d(conf g.edgeStore),
    Pred catesForCand date.soc alContextBlock ngOrMut ng(conf g.edgeStore),
    Pred catesForCand date.soc alContextNotRet etFollow ng(conf g.edgeStore)
  )
}
