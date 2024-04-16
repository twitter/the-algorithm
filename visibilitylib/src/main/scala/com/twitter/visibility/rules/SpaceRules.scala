package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.H ghTox c yModelScoreSpaceThresholdParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableMutedKeywordF lter ngSpaceT leNot f cat onsRuleParam
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.Coord natedHarmfulAct v yH ghRecall
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.DoNotAmpl fy
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.M slead ngH ghRecall
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.NsfwH ghPrec s on
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.NsfwH ghRecall
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.UntrustedUrl
 mport com.tw ter.v s b l y.models.UserLabelValue.Abus ve
 mport com.tw ter.v s b l y.models.UserLabelValue.Bl nkWorst
 mport com.tw ter.v s b l y.models.UserLabelValue.DelayedRe d at on
 mport com.tw ter.v s b l y.models.UserLabelValue.NsfwAvatar mage
 mport com.tw ter.v s b l y.models.UserLabelValue.NsfwBanner mage
 mport com.tw ter.v s b l y.models.UserLabelValue.NsfwNearPerfect
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.HatefulH ghRecall
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.H ghTox c yModelScore
 mport com.tw ter.v s b l y.models.SpaceSafetyLabelType.V olenceH ghRecall
 mport com.tw ter.v s b l y.models.UserLabelValue
 mport com.tw ter.v s b l y.rules.Cond  on._
 mport com.tw ter.v s b l y.rules.Reason.Nsfw
 mport com.tw ter.v s b l y.rules.Reason.Unspec f ed

object SpaceRules {

  abstract class SpaceHasLabelRule(
    act on: Act on,
    safetyLabelType: SpaceSafetyLabelType)
      extends RuleW hConstantAct on(act on, And(SpaceHasLabel(safetyLabelType), NonAuthorV e r))

  abstract class SpaceHasLabelAndNonFollo rRule(
    act on: Act on,
    safetyLabelType: SpaceSafetyLabelType)
      extends RuleW hConstantAct on(
        act on,
        And(SpaceHasLabel(safetyLabelType), LoggedOutOrV e rNotFollow ngAuthor))

  abstract class AnySpaceHostOrAdm nHasLabelRule(
    act on: Act on,
    userLabel: UserLabelValue)
      extends W nAuthorUserLabelPresentRule(act on, userLabel)

  abstract class AnySpaceHostOrAdm nHasLabelAndNonFollo rRule(
    act on: Act on,
    userLabel: UserLabelValue)
      extends Cond  onW hUserLabelRule(act on, LoggedOutOrV e rNotFollow ngAuthor, userLabel)


  object SpaceDoNotAmpl fyAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspec f ed),
        DoNotAmpl fy,
      )

  object SpaceDoNotAmpl fyNonFollo rDropRule
      extends SpaceHasLabelAndNonFollo rRule(
        Drop(Unspec f ed),
        DoNotAmpl fy,
      )

  object SpaceCoordHarmfulAct v yH ghRecallAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspec f ed),
        Coord natedHarmfulAct v yH ghRecall,
      )

  object SpaceCoordHarmfulAct v yH ghRecallNonFollo rDropRule
      extends SpaceHasLabelAndNonFollo rRule(
        Drop(Unspec f ed),
        Coord natedHarmfulAct v yH ghRecall,
      )

  object SpaceUntrustedUrlAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspec f ed),
        UntrustedUrl,
      )

  object SpaceUntrustedUrlNonFollo rDropRule
      extends SpaceHasLabelAndNonFollo rRule(
        Drop(Unspec f ed),
        UntrustedUrl,
      )

  object SpaceM slead ngH ghRecallNonFollo rDropRule
      extends SpaceHasLabelAndNonFollo rRule(
        Drop(Unspec f ed),
        M slead ngH ghRecall,
      )

  object SpaceNsfwH ghPrec s onAllUsers nterst  alRule
      extends SpaceHasLabelRule(
         nterst  al(Nsfw),
        NsfwH ghPrec s on,
      )

  object SpaceNsfwH ghPrec s onAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Nsfw),
        NsfwH ghPrec s on,
      )

  object SpaceNsfwH ghPrec s onNonFollo rDropRule
      extends SpaceHasLabelAndNonFollo rRule(
        Drop(Nsfw),
        NsfwH ghPrec s on,
      )

  object SpaceNsfwH ghPrec s onSafeSearchNonFollo rDropRule
      extends RuleW hConstantAct on(
        Drop(Nsfw),
        And(
          SpaceHasLabel(NsfwH ghPrec s on),
          NonAuthorV e r,
          LoggedOutOrV e rOpt nF lter ng,
          Not(V e rDoesFollowAuthor),
        ),
      )

  object SpaceNsfwH ghRecallAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Nsfw),
        NsfwH ghRecall,
      )

  object SpaceNsfwH ghRecallNonFollo rDropRule
      extends SpaceHasLabelAndNonFollo rRule(
        Drop(Nsfw),
        NsfwH ghRecall,
      )

  object SpaceNsfwH ghRecallSafeSearchNonFollo rDropRule
      extends RuleW hConstantAct on(
        Drop(Nsfw),
        And(
          SpaceHasLabel(NsfwH ghRecall),
          NonAuthorV e r,
          LoggedOutOrV e rOpt nF lter ng,
          Not(V e rDoesFollowAuthor),
        ),
      )

  object SpaceHatefulH ghRecallAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspec f ed),
        HatefulH ghRecall,
      )

  object SpaceV olenceH ghRecallAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspec f ed),
        V olenceH ghRecall,
      )

  object SpaceH ghTox c yScoreNonFollo rDropRule
      extends RuleW hConstantAct on(
        Drop(Unspec f ed),
        And(
          SpaceHasLabelW hScoreAboveThresholdW hParam(
            H ghTox c yModelScore,
            H ghTox c yModelScoreSpaceThresholdParam
          ),
          NonAuthorV e r,
          LoggedOutOrV e rNotFollow ngAuthor,
        )
      )
      w h Exper  ntalRule


  object V e rHasMatch ngMutedKeyword nSpaceT leForNot f cat onsRule
      extends OnlyW nNotAuthorV e rRule(
        Drop(Reason.MutedKeyword),
        Cond  on.V e rHasMatch ngKeyword nSpaceT leForNot f cat ons
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
      EnableMutedKeywordF lter ngSpaceT leNot f cat onsRuleParam)

  }


  object UserAbus veNonFollo rDropRule
      extends AnySpaceHostOrAdm nHasLabelAndNonFollo rRule(
        Drop(Unspec f ed),
        Abus ve
      )

  object UserBl nkWorstAllUsersDropRule
      extends AnySpaceHostOrAdm nHasLabelRule(
        Drop(Unspec f ed),
        Bl nkWorst
      )

  object UserNsfwNearPerfectNonFollo rDropRule
      extends AnySpaceHostOrAdm nHasLabelAndNonFollo rRule(
        Drop(Nsfw),
        NsfwNearPerfect
      )

  object UserNsfwH ghPrec s onNonFollo rDropRule
      extends AnySpaceHostOrAdm nHasLabelAndNonFollo rRule(
        Drop(Nsfw),
        UserLabelValue.NsfwH ghPrec s on
      )

  object UserNsfwAvatar mageNonFollo rDropRule
      extends AnySpaceHostOrAdm nHasLabelAndNonFollo rRule(
        Drop(Nsfw),
        NsfwAvatar mage
      )

  object UserNsfwBanner mageNonFollo rDropRule
      extends AnySpaceHostOrAdm nHasLabelAndNonFollo rRule(
        Drop(Nsfw),
        NsfwBanner mage
      )
}
