package com.tw ter.t  l neranker.para ters.recap_hydrat on

 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param

object RecapHydrat onParams {

  /**
   * Enables semant c core, pengu n, and t etyp e content features  n recap hydrat on s ce.
   */
  object EnableContentFeaturesHydrat onParam extends Param(false)

  /**
   * add  onally enables tokens w n hydrat ng content features.
   */
  object EnableTokens nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "recap_hydrat on_enable_tokens_ n_content_features_hydrat on",
        default = false
      )

  /**
   * add  onally enables t et text w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableT etText nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "recap_hydrat on_enable_t et_text_ n_content_features_hydrat on",
        default = false
      )

  /**
   * add  onally enables conversat onControl w n hydrat ng content features.
   * T  only works  f EnableContentFeaturesHydrat onParam  s set to true
   */
  object EnableConversat onControl nContentFeaturesHydrat onParam
      extends FSParam(
        na  = "conversat on_control_ n_content_features_hydrat on_recap_hydrat on_enable",
        default = false
      )

  object EnableT et d aHydrat onParam
      extends FSParam(
        na  = "t et_ d a_hydrat on_recap_hydrat on_enable",
        default = false
      )

}
