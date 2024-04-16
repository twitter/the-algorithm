package com.tw ter.follow_recom ndat ons.common.pred cates.sgs

 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.st ch.St ch
 mport javax. nject.S ngleton

@S ngleton
class RecentFollow ngPred cate extends Pred cate[(HasRecentFollo dUser ds, Cand dateUser)] {

  overr de def apply(pa r: (HasRecentFollo dUser ds, Cand dateUser)): St ch[Pred cateResult] = {

    val (targetUser, cand date) = pa r
    targetUser.recentFollo dUser dsSet match {
      case So (users) =>
         f (!users.conta ns(cand date. d)) {
          RecentFollow ngPred cate.Val dSt ch
        } else {
          RecentFollow ngPred cate.AlreadyFollo dSt ch
        }
      case None => RecentFollow ngPred cate.Val dSt ch
    }
  }
}

object RecentFollow ngPred cate {
  val Val dSt ch: St ch[Pred cateResult.Val d.type] = St ch.value(Pred cateResult.Val d)
  val AlreadyFollo dSt ch: St ch[Pred cateResult. nval d] =
    St ch.value(Pred cateResult. nval d(Set(F lterReason.AlreadyFollo d)))
}
