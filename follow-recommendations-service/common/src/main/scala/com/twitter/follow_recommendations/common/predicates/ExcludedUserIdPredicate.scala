package com.tw ter.follow_recom ndat ons.common.pred cates

 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason.Excluded d
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasExcludedUser ds
 mport com.tw ter.st ch.St ch

object ExcludedUser dPred cate extends Pred cate[(HasExcludedUser ds, Cand dateUser)] {

  val Val dSt ch: St ch[Pred cateResult.Val d.type] = St ch.value(Pred cateResult.Val d)
  val ExcludedSt ch: St ch[Pred cateResult. nval d] =
    St ch.value(Pred cateResult. nval d(Set(Excluded d)))

  overr de def apply(pa r: (HasExcludedUser ds, Cand dateUser)): St ch[Pred cateResult] = {
    val (excludedUser ds, cand date) = pa r
     f (excludedUser ds.excludedUser ds.conta ns(cand date. d)) {
      ExcludedSt ch
    } else {
      Val dSt ch
    }
  }
}
