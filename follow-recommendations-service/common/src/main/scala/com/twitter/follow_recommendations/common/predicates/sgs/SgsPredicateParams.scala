package com.tw ter.follow_recom ndat ons.common.pred cates.sgs

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.convers ons.Durat onOps._

object SgsPred cateParams {
  case object SgsRelat onsh psPred cateT  out
      extends FSBoundedParam[Durat on](
        na  = "sgs_pred cate_relat onsh ps_t  out_ n_m ll s",
        default = 300.m ll second,
        m n = 1.m ll second,
        max = 1000.m ll second)
      w h HasDurat onConvers on {
    overr de def durat onConvers on: Durat onConvers on = Durat onConvers on.FromM ll s
  }
}
