package com.tw ter.fr gate.pushserv ce.take.pred cates

 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.base.T etDeta ls
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.pred cate.Pred catesForCand date
 mport com.tw ter. rm .pred cate.Na dPred cate

tra  OutOfNetworkT etPred cates[C <: PushCand date w h T etCand date w h T etDeta ls]
    extends Bas cT etPred catesForRFPH[C] {

  overr de lazy val preCand dateSpec f cPred cates: L st[Na dPred cate[C]] =
    L st(
      Pred catesForCand date.authorNotBe ngFollo d(conf g.edgeStore)
    )
}
