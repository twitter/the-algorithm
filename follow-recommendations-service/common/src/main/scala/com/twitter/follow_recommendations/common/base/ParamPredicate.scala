package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason.ParamReason
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Param

case class ParamPred cate[Request <: HasParams](param: Param[Boolean]) extends Pred cate[Request] {

  def apply(request: Request): St ch[Pred cateResult] = {
     f (request.params(param)) {
      St ch.value(Pred cateResult.Val d)
    } else {
      St ch.value(Pred cateResult. nval d(Set(ParamReason(param.statNa ))))
    }
  }
}
