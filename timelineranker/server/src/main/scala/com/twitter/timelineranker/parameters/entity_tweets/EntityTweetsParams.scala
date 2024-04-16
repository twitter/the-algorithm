package com.tw ter.t  l neranker.para ters.ent y_t ets

 mport com.tw ter.t  l neranker.dec der.Dec derKey
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object Ent yT etsParams {

  /**
   * Controls l m  on t  number of follo d users fetc d from SGS.
   */
  object MaxFollo dUsersParam
      extends FSBoundedParam[ nt](
        na  = "ent y_t ets_max_follo d_users",
        default = 1000,
        m n = 0,
        max = 5000
      )

  /**
   * Enables semant c core, pengu n, and t etyp e content features  n ent y t ets s ce.
   */
  object EnableContentFeaturesHydrat onParam
      extends Dec derParam[Boolean](
        dec der = Dec derKey.Ent yT etsEnableContentFeaturesHydrat on,
        default = false
      )

  /**
   * add  onally enables tokens w n hydrat ng content features.
   */
  object EnableTokens nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "ent y_t ets_enable_tokens_ n_content_features_hydrat on",
        default = false
      )

  /**
   * add  onally enables t et text w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableT etText nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "ent y_t ets_enable_t et_text_ n_content_features_hydrat on",
        default = false
      )

  /**
   * add  onally enables conversat onControl w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableConversat onControl nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "conversat on_control_ n_content_features_hydrat on_ent y_enable",
        default = false
      )

  object EnableT et d aHydrat onParam
      extends FSParam(
        na  = "t et_ d a_hydrat on_ent y_t ets_enable",
        default = false
      )

}
