package com.tw ter.fr gate.pushserv ce.take.pred cates

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.pred cate.CrtDec derPred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.ScarecrowPred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue.NtabCaretCl ckFat guePred cate
 mport com.tw ter. rm .pred cate.Na dPred cate

tra  TakeCommonPred cates {
  def conf g: Conf g

   mpl c  def statsRece ver: StatsRece ver

  lazy val rfphPrePred cates: L st[Na dPred cate[PushCand date]] = L st(
    CrtDec derPred cate(conf g.dec der),
    Pred catesForCand date. sChannelVal dPred cate,
  )

  lazy val sendHandlerPrePred cates: L st[Na dPred cate[PushCand date]] = L st(
    CrtDec derPred cate(conf g.dec der),
    Pred catesForCand date.enableSendHandlerCand dates,
    Pred catesForCand date.mr bHoldbackPred cate,
    Pred catesForCand date.targetUserEx sts,
    Pred catesForCand date.author nSoc alContext,
    Pred catesForCand date.recom ndedT et sAuthoredBySelf,
    Pred catesForCand date.self nSoc alContext,
    NtabCaretCl ckFat guePred cate()
  )

  lazy val postPred cates: L st[Na dPred cate[PushCand date]] = L st(
    ScarecrowPred cate(conf g.scarecrowC ckEventStore)
  )
}
