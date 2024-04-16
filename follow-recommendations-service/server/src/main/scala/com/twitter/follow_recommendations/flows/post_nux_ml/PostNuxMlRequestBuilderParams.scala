package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on

object PostNuxMlRequestBu lderParams {
  case object Top c dFetchBudget
      extends FSBoundedParam[Durat on](
        na  = "post_nux_ml_request_bu lder_top c_ d_fetch_budget_m ll s",
        default = 200.m ll second,
        m n = 80.m ll second,
        max = 400.m ll second)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM ll s
  }

  case object D sm ssed dScanBudget
      extends FSBoundedParam[Durat on](
        na  = "post_nux_ml_request_bu lder_d sm ssed_ d_scan_budget_m ll s",
        default = 200.m ll second,
        m n = 80.m ll second,
        max = 400.m ll second)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM ll s
  }

  case object WTF mpress onsScanBudget
      extends FSBoundedParam[Durat on](
        na  = "post_nux_ml_request_bu lder_wtf_ mpress ons_scan_budget_m ll s",
        default = 200.m ll second,
        m n = 80.m ll second,
        max = 400.m ll second)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM ll s
  }

  case object Enable nval dRelat onsh pPred cate
      extends FSParam[Boolean](
        na  = "post_nux_ml_request_bu lder_enable_ nval d_relat onsh p_pred cate",
        false)
}
