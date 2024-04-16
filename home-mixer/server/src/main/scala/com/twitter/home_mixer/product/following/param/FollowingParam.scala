package com.tw ter.ho _m xer.product.follow ng.param

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.WhoToFollowModuleD splayType
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.ut l.Durat on

object Follow ngParam {
  val SupportedCl entFSNa  = "follow ng_supported_cl ent"

  object ServerMaxResultsParam
      extends FSBoundedParam[ nt](
        na  = "follow ng_server_max_results",
        default = 100,
        m n = 1,
        max = 500
      )

  object EnableWhoToFollowCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "follow ng_enable_who_to_follow",
        default = true
      )

  object EnableAdsCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "follow ng_enable_ads",
        default = true
      )

  object EnableFl p nject onModuleCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "follow ng_enable_fl p_ nl ne_ nject on_module",
        default = true
      )

  object Fl p nl ne nject onModulePos  on
      extends FSBoundedParam[ nt](
        na  = "follow ng_fl p_ nl ne_ nject on_module_pos  on",
        default = 0,
        m n = 0,
        max = 1000
      )

  object WhoToFollowPos  onParam
      extends FSBoundedParam[ nt](
        na  = "follow ng_who_to_follow_pos  on",
        default = 5,
        m n = 0,
        max = 99
      )

  object WhoToFollowM n nject on ntervalParam
      extends FSBoundedParam[Durat on](
        "follow ng_who_to_follow_m n_ nject on_ nterval_ n_m nutes",
        default = 1800.m nutes,
        m n = 0.m nutes,
        max = 6000.m nutes)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM nutes
  }

  object WhoToFollowD splayType dParam
      extends FSEnumParam[WhoToFollowModuleD splayType.type](
        na  = "follow ng_enable_who_to_follow_d splay_type_ d",
        default = WhoToFollowModuleD splayType.Vert cal,
        enum = WhoToFollowModuleD splayType
      )

  object WhoToFollowD splayLocat onParam
      extends FSParam[Str ng](
        na  = "follow ng_who_to_follow_d splay_locat on",
        default = "t  l ne_reverse_chron"
      )

  object EnableFastAds
      extends FSParam[Boolean](
        na  = "follow ng_enable_fast_ads",
        default = true
      )
}
