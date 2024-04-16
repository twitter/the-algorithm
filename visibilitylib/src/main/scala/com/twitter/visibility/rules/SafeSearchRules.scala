package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableDownrankSpamReplySect on ngRuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableNotGraduatedSearchDropRuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableNsfwTextSect on ngRuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.NotGraduatedUserLabelRuleHoldbackExper  ntParam
 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.v s b l y.models.UserLabelValue
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.LoggedOutOrV e rNotFollow ngAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.LoggedOutOrV e rOpt nF lter ng
 mport com.tw ter.v s b l y.rules.Cond  on.NonAuthorV e r
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Cond  on.T etComposedBefore
 mport com.tw ter.v s b l y.rules.Cond  on.V e rDoesFollowAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.V e rOpt nF lter ngOnSearch
 mport com.tw ter.v s b l y.rules.Reason.Nsfw
 mport com.tw ter.v s b l y.rules.Reason.Unspec f ed
 mport com.tw ter.v s b l y.rules.RuleAct onS ceBu lder.T etSafetyLabelS ceBu lder

case object SafeSearchT etRules {

  object SafeSearchAbus veT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Unspec f ed),
        T etSafetyLabelType.Abus ve
      )
      w h DoesLogVerd ct {
    overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
      T etSafetyLabelS ceBu lder(T etSafetyLabelType.Abus ve))
  }

  object SafeSearchNsfwH ghPrec s onT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Nsfw),
        T etSafetyLabelType.NsfwH ghPrec s on
      )

  object SafeSearchGoreAndV olenceH ghPrec s onT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Nsfw),
        T etSafetyLabelType.GoreAndV olenceH ghPrec s on
      )

  object SafeSearchNsfwReported ur st csT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Nsfw),
        T etSafetyLabelType.NsfwReported ur st cs
      )

  object SafeSearchGoreAndV olenceReported ur st csT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Nsfw),
        T etSafetyLabelType.GoreAndV olenceReported ur st cs
      )

  object SafeSearchNsfwCard mageT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Nsfw),
        T etSafetyLabelType.NsfwCard mage
      )

  object SafeSearchNsfwH ghRecallT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Nsfw),
        T etSafetyLabelType.NsfwH ghRecall
      )

  object SafeSearchNsfwV deoT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Nsfw),
        T etSafetyLabelType.NsfwV deo
      )

  object SafeSearchNsfwTextT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Nsfw),
        T etSafetyLabelType.NsfwText
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTextSect on ngRuleParam)
  }

  object SafeSearchNsfwTextAuthorLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.DownrankSpamReply
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTextSect on ngRuleParam)
  }

  object SafeSearchGoreAndV olenceT etLabelRule
      extends Cond  onW hT etLabelRule(
        Drop(Unspec f ed),
        And(
          NonAuthorV e r,
          T etComposedBefore(T etSafetyLabelType.GoreAndV olence.DeprecatedAt),
          LoggedOutOrV e rOpt nF lter ng
        ),
        T etSafetyLabelType.GoreAndV olence
      )

  object SafeSearchUntrustedUrlT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Unspec f ed),
        T etSafetyLabelType.UntrustedUrl
      )

  object SafeSearchDownrankSpamReplyT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Unspec f ed),
        T etSafetyLabelType.DownrankSpamReply
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableDownrankSpamReplySect on ngRuleParam)
  }

  object SafeSearchDownrankSpamReplyAuthorLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.DownrankSpamReply
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableDownrankSpamReplySect on ngRuleParam)
  }

  object SafeSearchAutomat onNonFollo rT etLabelRule
      extends NonFollo rV e rOpt nF lter ngW hT etLabelRule(
        Drop(Unspec f ed),
        T etSafetyLabelType.Automat on
      )

  object SafeSearchDupl cate nt onNonFollo rT etLabelRule
      extends NonFollo rV e rOpt nF lter ngW hT etLabelRule(
        Drop(Unspec f ed),
        T etSafetyLabelType.Dupl cate nt on
      )

  object SafeSearchBystanderAbus veT etLabelRule
      extends NonAuthorV e rOpt nF lter ngW hT etLabelRule(
        Drop(Unspec f ed),
        T etSafetyLabelType.BystanderAbus ve
      )
}

case object UnsafeSearchT etRules {

  object UnsafeSearchNsfwH ghPrec s on nterst  alAllUsersT etLabelRule
      extends Cond  onW hT etLabelRule(
         nterst  al(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.NsfwH ghPrec s on
      )

  object UnsafeSearchGoreAndV olenceH ghPrec s onAllUsersT etLabelRule
      extends Cond  onW hT etLabelRule(
         nterst  al(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.GoreAndV olenceH ghPrec s on
      )

  object UnsafeSearchGoreAndV olenceH ghPrec s onAllUsersT etLabelDropRule
      extends Cond  onW hT etLabelRule(
        Drop(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.GoreAndV olenceH ghPrec s on
      )

  object UnsafeSearchNsfwReported ur st csAllUsersT etLabelRule
      extends Cond  onW hT etLabelRule(
         nterst  al(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.NsfwReported ur st cs
      )

  object UnsafeSearchNsfwReported ur st csAllUsersT etLabelDropRule
      extends Cond  onW hT etLabelRule(
        Drop(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.NsfwReported ur st cs
      )

  object UnsafeSearchNsfwH ghPrec s onAllUsersT etLabelDropRule
      extends Cond  onW hT etLabelRule(
        Drop(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.NsfwH ghPrec s on
      )

  object UnsafeSearchGoreAndV olenceReported ur st csAllUsersT etLabelRule
      extends Cond  onW hT etLabelRule(
         nterst  al(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.GoreAndV olenceReported ur st cs
      )

  object UnsafeSearchGoreAndV olenceReported ur st csAllUsersT etLabelDropRule
      extends Cond  onW hT etLabelRule(
        Drop(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.GoreAndV olenceReported ur st cs
      )

  object UnsafeSearchNsfwCard mageAllUsersT etLabelRule
      extends Cond  onW hT etLabelRule(
         nterst  al(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.NsfwCard mage
      )

  object UnsafeSearchNsfwCard mageAllUsersT etLabelDropRule
      extends Cond  onW hT etLabelRule(
        Drop(Nsfw),
        Not(V e rOpt nF lter ngOnSearch),
        T etSafetyLabelType.NsfwCard mage
      )

}

case object SafeSearchUserRules {

  object SafeSearchAbus veUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.Abus ve
      )

  object SafeSearchAbus veH ghRecallUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.Abus veH ghRecall,
        LoggedOutOrV e rNotFollow ngAuthor
      )

  object SafeSearchH ghRecallUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.NsfwH ghRecall,
        LoggedOutOrV e rNotFollow ngAuthor
      )

  object SafeSearchComprom sedUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.Comprom sed
      )

  object SafeSearchDupl cateContentUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.Dupl cateContent
      )

  object SafeSearchLowQual yUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.LowQual y
      )

  object SafeSearchNsfwH ghPrec s onUserLabelRule
      extends Cond  onW hUserLabelRule(
        Drop(Nsfw),
        LoggedOutOrV e rOpt nF lter ng,
        UserLabelValue.NsfwH ghPrec s on
      )

  object SafeSearchNsfwAvatar mageUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.NsfwAvatar mage
      )

  object SafeSearchNsfwBanner mageUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.NsfwBanner mage
      )

  object SafeSearchNsfwNearPerfectAuthorRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.NsfwNearPerfect
      )

  object SafeSearchReadOnlyUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.ReadOnly
      )

  object SafeSearchSpamH ghRecallUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.SpamH ghRecall
      )

  object SafeSearchSearchBlackl stUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.SearchBlackl st
      )

  object SafeSearchNsfwTextUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.NsfwText
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableNsfwTextSect on ngRuleParam)
  }

  object SafeSearchDoNotAmpl fyNonFollo rsUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.DoNotAmpl fy,
        prerequ s eCond  on = Not(V e rDoesFollowAuthor)
      )

  object SafeSearchNotGraduatedNonFollo rsUserLabelRule
      extends V e rOpt nF lter ngOnSearchUserLabelRule(
        Drop(Unspec f ed),
        UserLabelValue.NotGraduated,
        prerequ s eCond  on = Not(V e rDoesFollowAuthor)
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableNotGraduatedSearchDropRuleParam)

    overr de def holdbacks: Seq[RuleParam[Boolean]] =
      Seq(NotGraduatedUserLabelRuleHoldbackExper  ntParam)

  }
}
