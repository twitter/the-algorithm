package com.tw ter.follow_recom ndat ons.common.pred cates

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

object  nact vePred cateParams {
  case object Default nact v yThreshold
      extends FSBoundedParam[ nt](
        na  = " nact ve_pred cate_default_ nact v y_threshold",
        default = 60,
        m n = 1,
        max = 500
      )
  case object UseEggF lter extends Param[Boolean](true)
  case object M ghtBeD sabled extends FSParam[Boolean](" nact ve_pred cate_m ght_be_d sabled", true)
  case object OnlyD sableForNewUserStateCand dates
      extends FSParam[Boolean](
        " nact ve_pred cate_only_d sable_for_new_user_state_cand dates",
        false)
}
