package com.tw ter.follow_recom ndat ons.common.pred cates.sgs

 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.follow_recom ndat ons.common.models.Has nval dRelat onsh pUser ds
 mport com.tw ter.st ch.St ch
 mport javax. nject.S ngleton

@S ngleton
class  nval dRelat onsh pPred cate
    extends Pred cate[(Has nval dRelat onsh pUser ds, Cand dateUser)] {

  overr de def apply(
    pa r: (Has nval dRelat onsh pUser ds, Cand dateUser)
  ): St ch[Pred cateResult] = {

    val (targetUser, cand date) = pa r
    targetUser. nval dRelat onsh pUser ds match {
      case So (users) =>
         f (!users.conta ns(cand date. d)) {
           nval dRelat onsh pPred cate.Val dSt ch
        } else {
          St ch.value( nval dRelat onsh pPred cate. nval dRelat onsh pSt ch)
        }
      case None => St ch.value(Pred cateResult.Val d)
    }
  }
}

object  nval dRelat onsh pPred cate {
  val Val dSt ch: St ch[Pred cateResult.Val d.type] = St ch.value(Pred cateResult.Val d)
  val  nval dRelat onsh pSt ch: Pred cateResult. nval d =
    Pred cateResult. nval d(Set(F lterReason. nval dRelat onsh p))
}
