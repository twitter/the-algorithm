package com.tw ter.follow_recom ndat ons.products.ho _t  l ne.conf gap 

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

object Ho T  l neParams {
  object EnableProduct extends Param[Boolean](false)

  object DefaultMaxResults extends Param[ nt](20)

  object EnableWr  ngServ ng tory
      extends FSParam[Boolean]("ho _t  l ne_enable_wr  ng_serv ng_ tory", false)

  object Durat onGuardra lToForceSuggest
      extends FSBoundedParam[Durat on](
        na  = "ho _t  l ne_durat on_guardra l_to_force_suggest_ n_h s",
        default = 0.h s,
        m n = 0.h s,
        max = 1000.h s)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromH s
  }

  object SuggestBasedFat gueDurat on
      extends FSBoundedParam[Durat on](
        na  = "ho _t  l ne_suggest_based_fat gue_durat on_ n_h s",
        default = 0.h s,
        m n = 0.h s,
        max = 1000.h s)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromH s
  }
}
