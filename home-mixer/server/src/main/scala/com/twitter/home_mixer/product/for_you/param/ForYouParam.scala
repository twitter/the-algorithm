package com.tw ter.ho _m xer.product.for_ .param

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.ho _m xer.param.dec der.Dec derKey
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.WhoToFollowModuleD splayType
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .dec der.BooleanDec derParam
 mport com.tw ter.ut l.Durat on

object For Param {
  val SupportedCl entFSNa  = "for_ _supported_cl ent"

  object EnableTop cSoc alContextF lterParam
      extends FSParam[Boolean](
        na  = "for_ _enable_top c_soc al_context_f lter",
        default = true
      )

  object EnableVer f edAuthorSoc alContextBypassParam
      extends FSParam[Boolean](
        na  = "for_ _enable_ver f ed_author_soc al_context_bypass",
        default = true
      )

  object EnableT  l neScorerCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "for_ _enable_t  l ne_scorer_cand date_p pel ne",
        default = false
      )

  object EnableScoredT etsCand dateP pel neParam
      extends BooleanDec derParam(Dec derKey.EnableFor ScoredT etsCand dateP pel ne)

  object EnableWhoToFollowCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "for_ _enable_who_to_follow",
        default = true
      )

  object EnableWhoToSubscr beCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "for_ _enable_who_to_subscr be",
        default = true
      )

  object EnableT etPrev ewsCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "for_ _enable_t et_prev ews_cand date_p pel ne",
        default = true
      )

  object EnablePushToHo M xerP pel neParam
      extends FSParam[Boolean](
        na  = "for_ _enable_push_to_ho _m xer_p pel ne",
        default = false
      )

  object EnableScoredT etsM xerP pel neParam
      extends FSParam[Boolean](
        na  = "for_ _enable_scored_t ets_m xer_p pel ne",
        default = true
      )

  object ServerMaxResultsParam
      extends FSBoundedParam[ nt](
        na  = "for_ _server_max_results",
        default = 35,
        m n = 1,
        max = 500
      )

  object AdsNumOrgan c emsParam
      extends FSBoundedParam[ nt](
        na  = "for_ _ads_num_organ c_ ems",
        default = 35,
        m n = 1,
        max = 100
      )

  object WhoToFollowPos  onParam
      extends FSBoundedParam[ nt](
        na  = "for_ _who_to_follow_pos  on",
        default = 5,
        m n = 0,
        max = 99
      )

  object WhoToFollowM n nject on ntervalParam
      extends FSBoundedParam[Durat on](
        "for_ _who_to_follow_m n_ nject on_ nterval_ n_m nutes",
        default = 1800.m nutes,
        m n = 0.m nutes,
        max = 6000.m nutes)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM nutes
  }

  object WhoToFollowD splayType dParam
      extends FSEnumParam[WhoToFollowModuleD splayType.type](
        na  = "for_ _enable_who_to_follow_d splay_type_ d",
        default = WhoToFollowModuleD splayType.Vert cal,
        enum = WhoToFollowModuleD splayType
      )

  object WhoToFollowD splayLocat onParam
      extends FSParam[Str ng](
        na  = "for_ _who_to_follow_d splay_locat on",
        default = "t  l ne"
      )

  object WhoToSubscr bePos  onParam
      extends FSBoundedParam[ nt](
        na  = "for_ _who_to_subscr be_pos  on",
        default = 7,
        m n = 0,
        max = 99
      )

  object WhoToSubscr beM n nject on ntervalParam
      extends FSBoundedParam[Durat on](
        "for_ _who_to_subscr be_m n_ nject on_ nterval_ n_m nutes",
        default = 1800.m nutes,
        m n = 0.m nutes,
        max = 6000.m nutes)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM nutes
  }

  object WhoToSubscr beD splayType dParam
      extends FSEnumParam[WhoToFollowModuleD splayType.type](
        na  = "for_ _enable_who_to_subscr be_d splay_type_ d",
        default = WhoToFollowModuleD splayType.Vert cal,
        enum = WhoToFollowModuleD splayType
      )

  object T etPrev ewsPos  onParam
      extends FSBoundedParam[ nt](
        na  = "for_ _t et_prev ews_pos  on",
        default = 3,
        m n = 0,
        max = 99
      )

  object T etPrev ewsM n nject on ntervalParam
      extends FSBoundedParam[Durat on](
        "for_ _t et_prev ews_m n_ nject on_ nterval_ n_m nutes",
        default = 2.h s,
        m n = 0.m nutes,
        max = 600.m nutes)
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromM nutes
  }

  object T etPrev ewsMaxCand datesParam
      extends FSBoundedParam[ nt](
        na  = "for_ _t et_prev ews_max_cand dates",
        default = 1,
        m n = 0,
        // NOTE: prev ews are  njected at a f xed pos  on, so max cand dates = 1
        // to avo d bunch ng of prev ews.
        max = 1
      )

  object EnableFl p nject onModuleCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "for_ _enable_fl p_ nl ne_ nject on_module",
        default = true
      )

  object Fl p nl ne nject onModulePos  on
      extends FSBoundedParam[ nt](
        na  = "for_ _fl p_ nl ne_ nject on_module_pos  on",
        default = 0,
        m n = 0,
        max = 1000
      )

  object ClearCac OnPtr {
    object EnableParam
        extends FSParam[Boolean](
          na  = "for_ _clear_cac _ptr_enable",
          default = false
        )

    case object M nEntr esParam
        extends FSBoundedParam[ nt](
          na  = "for_ _clear_cac _ptr_m n_entr es",
          default = 10,
          m n = 0,
          max = 35
        )
  }

  object EnableClearCac OnPushToHo 
      extends FSParam[Boolean](
        na  = "for_ _enable_clear_cac _push_to_ho ",
        default = false
      )

  object EnableServedCand dateKafkaPubl sh ngParam
      extends FSParam[Boolean](
        na  = "for_ _enable_served_cand date_kafka_publ sh ng",
        default = true
      )

  object Exper  ntStatsParam
      extends FSParam[Str ng](
        na  = "for_ _exper  nt_stats",
        default = ""
      )
}
