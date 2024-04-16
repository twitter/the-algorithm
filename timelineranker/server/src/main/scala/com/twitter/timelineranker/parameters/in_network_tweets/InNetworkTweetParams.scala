package com.tw ter.t  l neranker.para ters. n_network_t ets

 mport com.tw ter.t  l neranker.para ters.recap.RecapQueryContext
 mport com.tw ter.t  l nes.conf gap .dec der._
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

object  nNetworkT etParams {
   mport RecapQueryContext._

  /**
   * Controls l m  on t  number of follo d users fetc d from SGS.
   *
   * T  spec f c default value below  s for blender-t  l nes par y.
   */
  object MaxFollo dUsersParam
      extends FSBoundedParam[ nt](
        na  = "recycled_max_follo d_users",
        default = MaxFollo dUsers.default,
        m n = MaxFollo dUsers.bounds.m n nclus ve,
        max = MaxFollo dUsers.bounds.max nclus ve
      )

  /**
   * Controls l m  on t  number of h s for Earlyb rd.
   *
   */
  object RelevanceOpt onsMaxH sToProcessParam
      extends FSBoundedParam[ nt](
        na  = "recycled_relevance_opt ons_max_h s_to_process",
        default = 500,
        m n = 100,
        max = 20000
      )

  /**
   * Fallback value for max mum number of search results,  f not spec f ed by query.maxCount
   */
  object DefaultMaxT etCount extends Param(200)

  /**
   *   mult ply maxCount (caller suppl ed value) by t  mult pl er and fetch those many
   * cand dates from search so that   are left w h suff c ent number of cand dates after
   * hydrat on and f lter ng.
   */
  object MaxCountMult pl erParam
      extends Param(MaxCountMult pl er.default)
      w h Dec derValueConverter[Double] {
    overr de def convert:  ntConverter[Double] =
      OutputBound ntConverter[Double](d v deDec derBy100 _, MaxCountMult pl er.bounds)
  }

  /**
   * Enable [[SearchQueryBu lder.createExcludedS ceT et dsQuery]]
   */
  object EnableExcludeS ceT et dsQueryParam
      extends FSParam[Boolean](
        na  = "recycled_exclude_s ce_t et_ ds_query_enable",
        default = false
      )

  object EnableEarlyb rdReturnAllResultsParam
      extends FSParam[Boolean](
        na  = "recycled_enable_earlyb rd_return_all_results",
        default = true
      )

  /**
   * FS-controlled param to enable ant -d lut on transform for DDG-16198
   */
  object RecycledMaxFollo dUsersEnableAnt D lut onParam
      extends FSParam[Boolean](
        na  = "recycled_max_follo d_users_enable_ant _d lut on",
        default = false
      )

  /**
   * Enables semant c core, pengu n, and t etyp e content features  n recycled s ce.
   */
  object EnableContentFeaturesHydrat onParam extends Param(default = true)

  /**
   * add  onally enables tokens w n hydrat ng content features.
   */
  object EnableTokens nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "recycled_enable_tokens_ n_content_features_hydrat on",
        default = false
      )

  /**
   * add  onally enables t et text w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableT etText nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "recycled_enable_t et_text_ n_content_features_hydrat on",
        default = false
      )

  /**
   * Enables hydrat ng root t et of  n-network repl es and extended repl es
   */
  object EnableReplyRootT etHydrat onParam
      extends FSParam(
        na  = "recycled_enable_reply_root_t et_hydrat on",
        default = true
      )

  /**
   * add  onally enables conversat onControl w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableConversat onControl nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "conversat on_control_ n_content_features_hydrat on_recycled_enable",
        default = false
      )

  object EnableT et d aHydrat onParam
      extends FSParam(
        na  = "t et_ d a_hydrat on_recycled_enable",
        default = false
      )

  object EnableEarlyb rdRealt  CgM grat onParam
      extends FSParam(
        na  = "recycled_enable_earlyb rd_realt  _cg_m grat on",
        default = false
      )

}
