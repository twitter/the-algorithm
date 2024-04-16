package com.tw ter.v s b l y.conf gap .params

 mport com.tw ter.t  l nes.conf gap .Bounded
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FeatureNa 
 mport com.tw ter.t  l nes.conf gap .HasT  Convers on
 mport com.tw ter.t  l nes.conf gap .T  Convers on
 mport com.tw ter.ut l.T  
 mport com.tw ter.v s b l y.common.ModelScoreThresholds

pr vate[v s b l y] object FeatureSw chKey extends Enu rat on {
  type FeatureSw chKey = Str ng

  f nal val H ghSpam T etContentScoreSearchTopProdT etLabelDropFuleThreshold =
    "h gh_spam _t et_content_score_search_top_prod_t et_label_drop_rule_threshold"
  f nal val H ghSpam T etContentScoreSearchLatestProdT etLabelDropRuleThreshold =
    "h gh_spam _t et_content_score_search_latest_prod_t et_label_drop_rule_threshold"
  f nal val H ghSpam T etContentScoreTrendTopT etLabelDropRuleThreshold =
    "h gh_spam _t et_content_score_trend_top_t et_label_drop_rule_threshold"
  f nal val H ghSpam T etContentScoreTrendLatestT etLabelDropRuleThreshold =
    "h gh_spam _t et_content_score_trend_latest_t et_label_drop_rule_threshold"
  f nal val H ghSpam T etContentScoreConvoDownrankAbus veQual yThreshold =
    "h gh_spam _t et_content_score_convos_downrank ng_abus ve_qual y_threshold"

  f nal val NsfwAgeBasedDropRulesHoldbackParam =
    "nsfw_age_based_drop_rules_holdback"

  f nal val Commun yT etDropRuleEnabled =
    "commun y_t et_drop_rule_enabled"
  f nal val Commun yT etDropProtectedRuleEnabled =
    "commun y_t et_drop_protected_rule_enabled"
  f nal val Commun yT etL m edAct onsRulesEnabled =
    "commun y_t et_l m ed_act ons_rules_enabled"
  f nal val Commun yT et mberRemovedL m edAct onsRulesEnabled =
    "commun y_t et_ mber_removed_l m ed_act ons_rules_enabled"
  f nal val Commun yT etCommun yUnava lableL m edAct onsRulesEnabled =
    "commun y_t et_commun y_unava lable_l m ed_act ons_rules_enabled"
  f nal val Commun yT etNon mberL m edAct onsRuleEnabled =
    "commun y_t et_non_ mber_l m ed_act ons_rule_enabled"

  f nal val TrustedFr endsT etL m edEngage ntsRuleEnabled =
    "trusted_fr ends_t et_l m ed_engage nts_rule_enabled"

  f nal val CountrySpec f cNsfwContentGat ngCountr es =
    "country_spec f c_nsfw_content_gat ng_countr es"

  f nal val AgeGat ngAdultContentExper  ntCountr es =
    "age_gat ng_adult_content_exper  nt_countr es"
  f nal val AgeGat ngAdultContentExper  ntEnabled =
    "age_gat ng_adult_content_exper  nt_enabled"

  f nal val H ghTox c yModelScoreSpaceThreshold =
    "h gh_tox c y_model_score_space_threshold"

  f nal val CardUr RootDoma nDenyL st = "card_ur _root_doma n_deny_l st"

  f nal val Sk pT etDeta lL m edEngage ntsRuleEnabled =
    "sk p_t et_deta l_l m ed_engage nts_rule_enabled"

  f nal val AdAvo danceH ghTox c yModelScoreThreshold =
    "ad_avo dance_model_thresholds_h gh_tox c y_model"
  f nal val AdAvo danceReportedT etModelScoreThreshold =
    "ad_avo dance_model_thresholds_reported_t et_model"

  f nal val StaleT etL m edAct onsRulesEnabled =
    "stale_t et_l m ed_act ons_rules_enabled"

  f nal val FosnrFallbackDropRulesEnabled =
    "freedom_of_speech_not_reach_fallback_drop_rules_enabled"
  f nal val FosnrRulesEnabled =
    "freedom_of_speech_not_reach_rules_enabled"
}

abstract class FSRuleParam[T](overr de val na : FeatureNa , overr de val default: T)
    extends RuleParam(default)
    w h FSNa 

abstract class FSBoundedRuleParam[T](
  overr de val na : FeatureNa ,
  overr de val default: T,
  overr de val m n: T,
  overr de val max: T
)(
   mpl c  overr de val order ng: Order ng[T])
    extends RuleParam(default)
    w h Bounded[T]
    w h FSNa 

abstract class FST  RuleParam[T](
  overr de val na : FeatureNa ,
  overr de val default: T  ,
  overr de val t  Convers on: T  Convers on[T])
    extends RuleParam(default)
    w h HasT  Convers on[T]
    w h FSNa 

abstract class FSEnumRuleParam[T <: Enu rat on](
  overr de val na : FeatureNa ,
  overr de val default: T#Value,
  overr de val enum: T)
    extends EnumRuleParam(default, enum)
    w h FSNa 

pr vate[v s b l y] object FSRuleParams {
  object H ghSpam T etContentScoreSearchTopProdT etLabelDropRuleThresholdParam
      extends FSBoundedParam(
        FeatureSw chKey.H ghSpam T etContentScoreSearchTopProdT etLabelDropFuleThreshold,
        default = ModelScoreThresholds.H ghSpam T etContentScoreDefaultThreshold,
        m n = 0,
        max = 1)
  object H ghSpam T etContentScoreSearchLatestProdT etLabelDropRuleThresholdParam
      extends FSBoundedParam(
        FeatureSw chKey.H ghSpam T etContentScoreSearchLatestProdT etLabelDropRuleThreshold,
        default = ModelScoreThresholds.H ghSpam T etContentScoreDefaultThreshold,
        m n = 0,
        max = 1)
  object H ghSpam T etContentScoreTrendTopT etLabelDropRuleThresholdParam
      extends FSBoundedParam(
        FeatureSw chKey.H ghSpam T etContentScoreTrendTopT etLabelDropRuleThreshold,
        default = ModelScoreThresholds.H ghSpam T etContentScoreDefaultThreshold,
        m n = 0,
        max = 1)
  object H ghSpam T etContentScoreTrendLatestT etLabelDropRuleThresholdParam
      extends FSBoundedParam(
        FeatureSw chKey.H ghSpam T etContentScoreTrendLatestT etLabelDropRuleThreshold,
        default = ModelScoreThresholds.H ghSpam T etContentScoreDefaultThreshold,
        m n = 0,
        max = 1)
  object H ghSpam T etContentScoreConvoDownrankAbus veQual yThresholdParam
      extends FSBoundedParam(
        FeatureSw chKey.H ghSpam T etContentScoreConvoDownrankAbus veQual yThreshold,
        default = ModelScoreThresholds.H ghSpam T etContentScoreDefaultThreshold,
        m n = 0,
        max = 1)

  object Commun yT etDropRuleEnabledParam
      extends FSRuleParam(FeatureSw chKey.Commun yT etDropRuleEnabled, true)

  object Commun yT etDropProtectedRuleEnabledParam
      extends FSRuleParam(FeatureSw chKey.Commun yT etDropProtectedRuleEnabled, true)

  object Commun yT etL m edAct onsRulesEnabledParam
      extends FSRuleParam(FeatureSw chKey.Commun yT etL m edAct onsRulesEnabled, false)

  object Commun yT et mberRemovedL m edAct onsRulesEnabledParam
      extends FSRuleParam(
        FeatureSw chKey.Commun yT et mberRemovedL m edAct onsRulesEnabled,
        false)

  object Commun yT etCommun yUnava lableL m edAct onsRulesEnabledParam
      extends FSRuleParam(
        FeatureSw chKey.Commun yT etCommun yUnava lableL m edAct onsRulesEnabled,
        false)

  object Commun yT etNon mberL m edAct onsRuleEnabledParam
      extends FSRuleParam(FeatureSw chKey.Commun yT etNon mberL m edAct onsRuleEnabled, false)

  object TrustedFr endsT etL m edEngage ntsRuleEnabledParam
      extends FSRuleParam(FeatureSw chKey.TrustedFr endsT etL m edEngage ntsRuleEnabled, false)

  object Sk pT etDeta lL m edEngage ntRuleEnabledParam
      extends FSRuleParam(FeatureSw chKey.Sk pT etDeta lL m edEngage ntsRuleEnabled, false)


  object NsfwAgeBasedDropRulesHoldbackParam
      extends FSRuleParam(FeatureSw chKey.NsfwAgeBasedDropRulesHoldbackParam, true)

  object CountrySpec f cNsfwContentGat ngCountr esParam
      extends FSRuleParam[Seq[Str ng]](
        FeatureSw chKey.CountrySpec f cNsfwContentGat ngCountr es,
        default = Seq("au"))

  object AgeGat ngAdultContentExper  ntCountr esParam
      extends FSRuleParam[Seq[Str ng]](
        FeatureSw chKey.AgeGat ngAdultContentExper  ntCountr es,
        default = Seq.empty)
  object AgeGat ngAdultContentExper  ntRuleEnabledParam
      extends FSRuleParam(FeatureSw chKey.AgeGat ngAdultContentExper  ntEnabled, default = false)

  object H ghTox c yModelScoreSpaceThresholdParam
      extends FSBoundedParam(
        FeatureSw chKey.H ghTox c yModelScoreSpaceThreshold,
        default = ModelScoreThresholds.H ghTox c yModelScoreSpaceDefaultThreshold,
        m n = 0,
        max = 1)

  object CardUr RootDoma nDenyL stParam
      extends FSRuleParam[Seq[Str ng]](
        FeatureSw chKey.CardUr RootDoma nDenyL st,
        default = Seq.empty)

  object AdAvo danceH ghTox c yModelScoreThresholdParam
      extends FSBoundedParam(
        FeatureSw chKey.AdAvo danceH ghTox c yModelScoreThreshold,
        default = ModelScoreThresholds.AdAvo danceH ghTox c yModelScoreDefaultThreshold,
        m n = 0,
        max = 1)

  object AdAvo danceReportedT etModelScoreThresholdParam
      extends FSBoundedParam(
        FeatureSw chKey.AdAvo danceReportedT etModelScoreThreshold,
        default = ModelScoreThresholds.AdAvo danceReportedT etModelScoreDefaultThreshold,
        m n = 0,
        max = 1)

  object StaleT etL m edAct onsRulesEnabledParam
      extends FSRuleParam(FeatureSw chKey.StaleT etL m edAct onsRulesEnabled, false)

  object FosnrFallbackDropRulesEnabledParam
      extends FSRuleParam(FeatureSw chKey.FosnrFallbackDropRulesEnabled, false)
  object FosnrRulesEnabledParam extends FSRuleParam(FeatureSw chKey.FosnrRulesEnabled, true)
}
