package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object S m larUserExpanderParams {

  case object EnableNonD rectFollowExpans on
      extends FSParam[Boolean]("s m lar_user_enable_non_d rect_follow_expans on", true)

  case object EnableS msExpandSeedAccountsSort
      extends FSParam[Boolean]("s m lar_user_enable_s ms_expander_seed_account_sort", false)

  case object DefaultExpans on nputCount
      extends FSBoundedParam[ nt](
        na  = "s m lar_user_default_expans on_ nput_count",
        default =  nteger.MAX_VALUE,
        m n = 0,
        max =  nteger.MAX_VALUE)

  case object DefaultF nalCand datesReturnedCount
      extends FSBoundedParam[ nt](
        na  = "s m lar_user_default_f nal_cand dates_returned_count",
        default =  nteger.MAX_VALUE,
        m n = 0,
        max =  nteger.MAX_VALUE)

  case object DefaultEnable mpl c EngagedExpans on
      extends FSParam[Boolean]("s m lar_user_enable_ mpl c _engaged_expans on", true)

}
