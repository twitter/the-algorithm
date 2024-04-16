package com.tw ter.follow_recom ndat ons.common.pred cates.hss

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.ut l.Durat on

object HssPred cateParams {
  object HssCseScoreThreshold
      extends FSBoundedParam[Double](
        "hss_pred cate_cse_score_threshold",
        default = 0.992d,
        m n = 0.0d,
        max = 1.0d)

  object HssNsfwScoreThreshold
      extends FSBoundedParam[Double](
        "hss_pred cate_nsfw_score_threshold",
        default = 1.5d,
        m n = -100.0d,
        max = 100.0d)

  object HssAp T  out
      extends FSBoundedParam[Durat on](
        na  = "hss_pred cate_t  out_ n_m ll s",
        default = 200.m ll second,
        m n = 1.m ll second,
        max = 500.m ll second)
      w h HasDurat onConvers on {
    overr de def durat onConvers on: Durat onConvers on = Durat onConvers on.FromM ll s
  }

}
