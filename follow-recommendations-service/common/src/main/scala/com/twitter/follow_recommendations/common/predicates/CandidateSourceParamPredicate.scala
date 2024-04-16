package com.tw ter.follow_recom ndat ons.common.pred cates

 mport com.tw ter.follow_recom ndat ons.common.base.Pred cate
 mport com.tw ter.follow_recom ndat ons.common.base.Pred cateResult
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * T  pred cate allows us to f lter cand dates g ven  s s ce.
 * To avo d bucket d lut on,   only want to evaluate t  param (wh ch would  mpl c ly tr gger
 * bucket ng for FSParams) only  f t  cand date s ce fn y elds true.
 * T  param prov ded should be true w n   want to keep t  cand date and false ot rw se.
 */
class Cand dateS ceParamPred cate(
  val param: Param[Boolean],
  val reason: F lterReason,
  cand dateS ces: Set[Cand dateS ce dent f er])
    extends Pred cate[Cand dateUser] {
  overr de def apply(cand date: Cand dateUser): St ch[Pred cateResult] = {
    //   want to avo d evaluat ng t  param  f t  cand date s ce fn y elds false
     f (cand date.getCand dateS ces.keys.ex sts(cand dateS ces.conta ns) && !cand date.params(
        param)) {
      St ch.value(Pred cateResult. nval d(Set(reason)))
    } else {
      St ch.value(Pred cateResult.Val d)
    }
  }
}
