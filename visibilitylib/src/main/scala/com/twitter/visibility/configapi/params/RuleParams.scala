package com.tw ter.v s b l y.conf gap .params

 mport com.tw ter.t  l nes.conf gap .EnumParam
 mport com.tw ter.t  l nes.conf gap .Param

abstract class RuleParam[T](overr de val default: T) extends Param(default) {
  overr de val statNa : Str ng = s"RuleParam/${t .getClass.getS mpleNa }"
}

abstract class EnumRuleParam[T <: Enu rat on](overr de val default: T#Value, overr de val enum: T)
    extends EnumParam(default, enum) {
  overr de val statNa : Str ng = s"RuleParam/${t .getClass.getS mpleNa }"
}

pr vate[v s b l y] object RuleParams {
  object True extends RuleParam(true)
  object False extends RuleParam(false)

  object TestHoldbackParam extends RuleParam(true)

  object T etConversat onControlEnabledParam extends RuleParam(default = false)

  object EnableL m Repl esFollo rsConversat onRule extends RuleParam(default = false)

  object Commun yT etsEnabledParam extends RuleParam(default = false)

  object DropCommun yT etW hUndef nedCommun yRuleEnabledParam extends RuleParam(default = false)

  object EnableH ghPSpam T etScoreSearchT etLabelDropRuleParam extends RuleParam(false)

  object EnableS teSpamT etRuleParam extends RuleParam(false)

  object EnableH ghSpam T etContentScoreSearchLatestT etLabelDropRuleParam
      extends RuleParam(false)

  object EnableH ghSpam T etContentScoreSearchTopT etLabelDropRuleParam extends RuleParam(false)

  object NotGraduatedUserLabelRuleHoldbackExper  ntParam extends RuleParam(default = false)

  object EnableGoreAndV olenceTop cH ghRecallT etLabelRule extends RuleParam(default = false)

  object EnableBl nkBadDownrank ngRuleParam extends RuleParam(false)
  object EnableBl nkWorstDownrank ngRuleParam extends RuleParam(false)

  object EnableH ghSpam T etContentScoreTrendsTopT etLabelDropRuleParam
      extends RuleParam(default = false)

  object EnableH ghSpam T etContentScoreTrendsLatestT etLabelDropRuleParam
      extends RuleParam(default = false)

  object EnableCopypastaSpamDownrankConvosAbus veQual yRule extends RuleParam(default = false)
  object EnableCopypastaSpamSearchDropRule extends RuleParam(default = false)

  object EnableSpam UserModelT etDropRuleParam extends RuleParam(default = false)

  object EnableAvo dNsfwRulesParam extends RuleParam(false)

  object EnableReportedT et nterst  alRule extends RuleParam(default = false)

  object EnableReportedT et nterst  alSearchRule extends RuleParam(default = false)

  object EnableDropExclus veT etContentRule extends RuleParam(default = false)

  object EnableDropExclus veT etContentRuleFa lClosed extends RuleParam(default = false)

  object EnableTombstoneExclus veQtProf leT  l neParam extends RuleParam(default = false)

  object EnableDropAllExclus veT etsRuleParam extends RuleParam(false)
  object EnableDropAllExclus veT etsRuleFa lClosedParam extends RuleParam(false)

  object EnableDownrankSpamReplySect on ngRuleParam extends RuleParam(default = false)
  object EnableNsfwTextSect on ngRuleParam extends RuleParam(default = false)

  object EnableSearch p SafeSearchW houtUser nQueryDropRule extends RuleParam(false)

  object PromotedT et althEnforce ntHoldback extends RuleParam(default = true)
  object EnableT  l neHo PromotedT et althEnforce ntRules extends RuleParam(default = false)

  object EnableMutedKeywordF lter ngSpaceT leNot f cat onsRuleParam extends RuleParam(false)

  object EnableDropT etsW hGeoRestr cted d aRuleParam extends RuleParam(default = false)

  object EnableDropAllTrustedFr endsT etsRuleParam extends RuleParam(false)
  object EnableDropTrustedFr endsT etContentRuleParam extends RuleParam(false)

  object EnableDropAllCollab nv at onT etsRuleParam extends RuleParam(false)

  object EnableNsfwTextH ghPrec s onDropRuleParam extends RuleParam(false)

  object EnableL kely vsUserLabelDropRule extends RuleParam(false)

  object EnableCardUr RootDoma nCardDenyl stRule extends RuleParam(false)
  object EnableCommun yNon mberPollCardRule extends RuleParam(false)
  object EnableCommun yNon mberPollCardRuleFa lClosed extends RuleParam(false)

  object EnableExper  ntalNudgeEnabledParam extends RuleParam(false)

  object NsfwH ghPrec s onUserLabelAvo dT etRuleEnabledParam extends RuleParam(default = false)

  object EnableNewAdAvo danceRulesParam extends RuleParam(false)

  object EnableNsfaH ghRecallAdAvo danceParam extends RuleParam(false)

  object EnableNsfaKeywordsH ghPrec s onAdAvo danceParam extends RuleParam(false)

  object EnableStaleT etDropRuleParam extends RuleParam(false)
  object EnableStaleT etDropRuleFa lClosedParam extends RuleParam(false)

  object EnableDeleteStateT etRulesParam extends RuleParam(default = false)

  object EnableSpacesShar ngNsfwDropRulesParam extends RuleParam(default = true)

  object EnableV e r sSoftUserDropRuleParam extends RuleParam(default = false)

  object EnablePdnaQuotedT etTombstoneRuleParam extends RuleParam(default = true)
  object EnableSpamQuotedT etTombstoneRuleParam extends RuleParam(default = true)

  object EnableNsfwHpQuotedT etDropRuleParam extends RuleParam(default = false)
  object EnableNsfwHpQuotedT etTombstoneRuleParam extends RuleParam(default = false)

  object Enable nnerQuotedT etV e rBlocksAuthor nterst  alRuleParam
      extends RuleParam(default = false)
  object Enable nnerQuotedT etV e rMutesAuthor nterst  alRuleParam
      extends RuleParam(default = false)


  object EnableNewSens  ve d aSett ngs nterst  alsHo T  l neRulesParam extends RuleParam(false)

  object EnableNewSens  ve d aSett ngs nterst  alsConversat onRulesParam extends RuleParam(false)

  object EnableNewSens  ve d aSett ngs nterst  alsProf leT  l neRulesParam
      extends RuleParam(false)

  object EnableNewSens  ve d aSett ngs nterst  alsT etDeta lRulesParam extends RuleParam(false)

  object EnableLegacySens  ve d aHo T  l neRulesParam extends RuleParam(true)

  object EnableLegacySens  ve d aConversat onRulesParam extends RuleParam(true)

  object EnableLegacySens  ve d aProf leT  l neRulesParam extends RuleParam(true)

  object EnableLegacySens  ve d aT etDeta lRulesParam extends RuleParam(true)

  object EnableLegacySens  ve d aD rect ssagesRulesParam extends RuleParam(true)

  object EnableTox cReplyF lter ngConversat onRulesParam extends RuleParam(false)
  object EnableTox cReplyF lter ngNot f cat onsRulesParam extends RuleParam(false)

  object EnableSearchQueryMatc sT etAuthorCond  onParam extends RuleParam(default = false)

  object EnableSearchBas cBlockMuteRulesParam extends RuleParam(default = false)

  object EnableAbus veBehav orDropRuleParam extends RuleParam(default = false)
  object EnableAbus veBehav or nterst  alRuleParam extends RuleParam(default = false)
  object EnableAbus veBehav orL m edEngage ntsRuleParam extends RuleParam(default = false)

  object EnableNotGraduatedDownrankConvosAbus veQual yRuleParam extends RuleParam(default = false)
  object EnableNotGraduatedSearchDropRuleParam extends RuleParam(default = false)
  object EnableNotGraduatedDropRuleParam extends RuleParam(default = false)

  object EnableFosnrRuleParam extends RuleParam(default = false)

  object EnableAuthorBlocksV e rDropRuleParam extends RuleParam(default = false)
}
