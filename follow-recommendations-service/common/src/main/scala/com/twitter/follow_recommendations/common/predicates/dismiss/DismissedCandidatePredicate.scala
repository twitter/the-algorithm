package com.tw ter.follow_recom ndat ons.common.pred cates.d sm ss

 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason.D sm ssed d
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD sm ssedUser ds
 mport com.tw ter.st ch.St ch
 mport javax. nject.S ngleton

@S ngleton
class D sm ssedCand datePred cate extends Pred cate[(HasD sm ssedUser ds, Cand dateUser)] {

  overr de def apply(pa r: (HasD sm ssedUser ds, Cand dateUser)): St ch[Pred cateResult] = {

    val (targetUser, cand date) = pa r
    targetUser.d sm ssedUser ds
      .map { d sm ssedUser ds =>
         f (!d sm ssedUser ds.conta ns(cand date. d)) {
          D sm ssedCand datePred cate.Val dSt ch
        } else {
          D sm ssedCand datePred cate.D sm ssedSt ch
        }
      }.getOrElse(D sm ssedCand datePred cate.Val dSt ch)
  }
}

object D sm ssedCand datePred cate {
  val Val dSt ch: St ch[Pred cateResult.Val d.type] = St ch.value(Pred cateResult.Val d)
  val D sm ssedSt ch: St ch[Pred cateResult. nval d] =
    St ch.value(Pred cateResult. nval d(Set(D sm ssed d)))
}
