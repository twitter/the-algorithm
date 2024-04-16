package com.tw ter.follow_recom ndat ons.common.pred cates

 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Param

class Cand dateParamPred cate[A <: HasParams](
  param: Param[Boolean],
  reason: F lterReason)
    extends Pred cate[A] {
  overr de def apply(cand date: A): St ch[Pred cateResult] = {
     f (cand date.params(param)) {
      St ch.value(Pred cateResult.Val d)
    } else {
      St ch.value(Pred cateResult. nval d(Set(reason)))
    }
  }
}
