package com.tw ter.fr gate.pushserv ce

 mport com.tw ter.fr gate.common.base.Cand date
 mport com.tw ter.fr gate.common.base.Soc alGraphServ ceRelat onsh pMap
 mport com.tw ter.fr gate.common.base.T etAuthor
 mport com.tw ter.fr gate.common.rec_types.RecTypes. s nNetworkT etType
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter. rm .pred cate.Pred cate

package object pred cate {
   mpl c  class Cand datesW hAuthorFollowPred cates(
    pred cate: Pred cate[
      PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap
    ]) {
    def applyOnlyToAuthorBe ngFollowPred cates: Pred cate[Cand date] =
      pred cate.opt onalOn[Cand date](
        {
          case cand date: PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap
               f  s nNetworkT etType(cand date.commonRecType) =>
            So (cand date)
          case _ =>
            None
        },
        m ss ngResult = true
      )
  }

   mpl c  class T etCand dateW hT etAuthor(
    pred cate: Pred cate[
      PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap
    ]) {
    def applyOnlyToBas cT etPred cates: Pred cate[Cand date] =
      pred cate.opt onalOn[Cand date](
        {
          case cand date: PushCand date w h T etAuthor w h Soc alGraphServ ceRelat onsh pMap
               f  s nNetworkT etType(cand date.commonRecType) =>
            So (cand date)
          case _ =>
            None
        },
        m ss ngResult = true
      )
  }
}
