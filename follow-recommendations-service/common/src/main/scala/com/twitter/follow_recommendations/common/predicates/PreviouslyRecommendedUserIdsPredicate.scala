package com.tw ter.follow_recom ndat ons.common.pred cates

 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.follow_recom ndat ons.common.models.HasPrev ousRecom ndat onsContext
 mport com.tw ter.st ch.St ch
 mport javax. nject.S ngleton

@S ngleton
class Prev ouslyRecom ndedUser dsPred cate
    extends Pred cate[(HasPrev ousRecom ndat onsContext, Cand dateUser)] {
  overr de def apply(
    pa r: (HasPrev ousRecom ndat onsContext, Cand dateUser)
  ): St ch[Pred cateResult] = {

    val (targetUser, cand date) = pa r

    val prev ouslyRecom ndedUser Ds = targetUser.prev ouslyRecom ndedUser Ds

     f (!prev ouslyRecom ndedUser Ds.conta ns(cand date. d)) {
      Prev ouslyRecom ndedUser dsPred cate.Val dSt ch
    } else {
      Prev ouslyRecom ndedUser dsPred cate.AlreadyRecom ndedSt ch
    }
  }
}

object Prev ouslyRecom ndedUser dsPred cate {
  val Val dSt ch: St ch[Pred cateResult.Val d.type] = St ch.value(Pred cateResult.Val d)
  val AlreadyRecom ndedSt ch: St ch[Pred cateResult. nval d] =
    St ch.value(Pred cateResult. nval d(Set(F lterReason.AlreadyRecom nded)))
}
