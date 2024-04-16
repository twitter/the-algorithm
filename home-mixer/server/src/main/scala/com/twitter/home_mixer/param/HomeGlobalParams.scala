package com.tw ter.ho _m xer.param

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

/**
 *  nstant ate Params that do not relate to a spec f c product.
 *
 * @see [[com.tw ter.product_m xer.core.product.ProductParamConf g.supportedCl entFSNa ]]
 */
object Ho GlobalParams {

  /**
   * T  param  s used to d sable ads  nject on for t  l nes served by ho -m xer.
   *    s currently used to ma nta n user-role based no-ads l sts for automat on accounts,
   * and should NOT be used for ot r purposes.
   */
  object AdsD sable nject onBasedOnUserRoleParam
      extends FSParam("ho _m xer_ads_d sable_ nject on_based_on_user_role", false)

  object EnableSendScoresToCl ent
      extends FSParam[Boolean](
        na  = "ho _m xer_enable_send_scores_to_cl ent",
        default = false
      )

  object EnableNahFeedback nfoParam
      extends FSParam[Boolean](
        na  = "ho _m xer_enable_nah_feedback_ nfo",
        default = false
      )

  object MaxNumberReplace nstruct onsParam
      extends FSBoundedParam[ nt](
        na  = "ho _m xer_max_number_replace_ nstruct ons",
        default = 100,
        m n = 0,
        max = 200
      )

  object T  l nesPers stenceStoreMaxEntr esPerCl ent
      extends FSBoundedParam[ nt](
        na  = "ho _m xer_t  l nes_pers stence_store_max_entr es_per_cl ent",
        default = 1800,
        m n = 500,
        max = 5000
      )

  object EnableNewT etsP llAvatarsParam
      extends FSParam[Boolean](
        na  = "ho _m xer_enable_new_t ets_p ll_avatars",
        default = true
      )

  object EnableSoc alContextParam
      extends FSParam[Boolean](
        na  = "ho _m xer_enable_soc al_context",
        default = true
      )

  object EnableAdvert serBrandSafetySett ngsFeatureHydratorParam
      extends FSParam[Boolean](
        na  = "ho _m xer_enable_advert ser_brand_safety_sett ngs_feature_hydrator",
        default = true
      )

  object Enable mpress onBloomF lter
      extends FSParam[Boolean](
        na  = "ho _m xer_enable_ mpress on_bloom_f lter",
        default = false
      )

  object  mpress onBloomF lterFalsePos  veRateParam
      extends FSBoundedParam[Double](
        na  = "ho _m xer_ mpress on_bloom_f lter_false_pos  ve_rate",
        default = 0.005,
        m n = 0.001,
        max = 0.01
      )

  object EnableScr beServedCand datesParam
      extends FSParam[Boolean](
        na  = "ho _m xer_served_t ets_enable_scr b ng",
        default = true
      )
}
