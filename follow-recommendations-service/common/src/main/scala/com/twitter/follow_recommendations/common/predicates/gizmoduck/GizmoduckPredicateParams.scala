package com.tw ter.follow_recom ndat ons.common.pred cates.g zmoduck

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.convers ons.Durat onOps._

object G zmoduckPred cateParams {
  case object G zmoduckGetT  out
      extends FSBoundedParam[Durat on](
        na  = "g zmoduck_pred cate_t  out_ n_m ll s",
        default = 200.m ll second,
        m n = 1.m ll second,
        max = 500.m ll second)
      w h HasDurat onConvers on {
    overr de def durat onConvers on: Durat onConvers on = Durat onConvers on.FromM ll s
  }
  val MaxCac S ze:  nt = 250000
  val Cac TTL: Durat on = Durat on.fromH s(6)
}
