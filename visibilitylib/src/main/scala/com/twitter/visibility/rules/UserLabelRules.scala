package com.tw ter.v s b l y.rules

 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.v s b l y.conf gap .conf gs.Dec derKey
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.conf gap .params.RuleParams._
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.models.UserLabelValue
 mport com.tw ter.v s b l y.models.UserLabelValue._
 mport com.tw ter.v s b l y.rules.Cond  on._
 mport com.tw ter.v s b l y.rules.Reason._
 mport com.tw ter.v s b l y.rules.RuleAct onS ceBu lder.UserSafetyLabelS ceBu lder

object Abus veRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      Abus ve
    )

object DoNotAmpl fyUserRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      DoNotAmpl fy
    )

object Abus veH ghRecallRule
    extends AuthorLabelAndNonFollo rV e rRule(
      Drop(Unspec f ed),
      Abus veH ghRecall
    )

object Comprom sedRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      Comprom sed
    )

object Dupl cateContentRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      Dupl cateContent
    )

object Engage ntSpam rRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      Engage ntSpam r
    )

object Engage ntSpam rH ghRecallRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      Engage ntSpam rH ghRecall
    )

object L veLowQual yRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      L veLowQual y
    )

object LowQual yRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      LowQual y
    )

object LowQual yH ghRecallRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      LowQual yH ghRecall
    )

object NotGraduatedRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      NotGraduated
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNotGraduatedDropRuleParam)
  overr de def holdbacks: Seq[RuleParam[Boolean]] = Seq(
    NotGraduatedUserLabelRuleHoldbackExper  ntParam)

}

abstract class BaseNsfwH ghPrec s onRule()
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      UserLabelValue.NsfwH ghPrec s on
    )
object NsfwH ghPrec s onRule
    extends BaseNsfwH ghPrec s onRule()

object NsfwH ghRecallRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      NsfwH ghRecall
    )

abstract class BaseNsfwNearPerfectAuthorRule()
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      NsfwNearPerfect
    )
object NsfwNearPerfectAuthorRule extends BaseNsfwNearPerfectAuthorRule()

object NsfwAvatar mageRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      NsfwAvatar mage
    )

object NsfwBanner mageRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      NsfwBanner mage
    )

object NsfwSens  veRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      NsfwSens  ve
    )

object ReadOnlyRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      ReadOnly
    )

object Recom ndat onsBlackl stRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      Recom ndat onsBlackl st
    )

sealed abstract class BaseSpamH ghRecallRule(val holdback: RuleParam[Boolean])
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      SpamH ghRecall
    ) {
  overr de val holdbacks: Seq[RuleParam[Boolean]] = Seq(holdback)
}

object SpamH ghRecallRule extends BaseSpamH ghRecallRule(RuleParams.False)

object Dec derableSpamH ghRecallRule extends BaseSpamH ghRecallRule(RuleParams.False)

object SearchBlackl stRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      SearchBlackl st
    )

object SearchNsfwTextRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      NsfwText
    ) {

  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableNsfwTextSect on ngRuleParam)
}

object Spam Follo rRule
    extends OnlyW nNotAuthorV e rRule(
      Drop(Unspec f ed),
      And(
        Or(
          AuthorHasLabel(Comprom sed),
          AuthorHasLabel(Engage ntSpam r),
          AuthorHasLabel(Engage ntSpam rH ghRecall),
          AuthorHasLabel(LowQual y),
          AuthorHasLabel(ReadOnly),
          AuthorHasLabel(SpamH ghRecall)
        ),
        Or(
          LoggedOutV e r,
          And(
            NonAuthorV e r,
            V e rHasUqfEnabled,
            Or(
              And(
                ProtectedV e r,
                LoggedOutOrV e rNotFollow ngAuthor,
                Not(AuthorDoesFollowV e r)
              ),
              And(Not(ProtectedV e r), LoggedOutOrV e rNotFollow ngAuthor)
            )
          )
        )
      )
    )

abstract class NonFollo rW hUqfUserLabelDropRule(labelValue: UserLabelValue)
    extends Cond  onW hUserLabelRule(
      Drop(Unspec f ed),
      And(
        Or(
          LoggedOutV e r,
          And(Not(V e rDoesFollowAuthor), V e rHasUqfEnabled)
        )
      ),
      labelValue
    )

object Engage ntSpam rNonFollo rW hUqfRule
    extends NonFollo rW hUqfUserLabelDropRule(
      Engage ntSpam r
    )

object Engage ntSpam rH ghRecallNonFollo rW hUqfRule
    extends NonFollo rW hUqfUserLabelDropRule(
      Engage ntSpam rH ghRecall
    )

object SpamH ghRecallNonFollo rW hUqfRule
    extends NonFollo rW hUqfUserLabelDropRule(
      SpamH ghRecall
    )

object Comprom sedNonFollo rW hUqfRule
    extends NonFollo rW hUqfUserLabelDropRule(
      Comprom sed
    )

object ReadOnlyNonFollo rW hUqfRule
    extends NonFollo rW hUqfUserLabelDropRule(
      ReadOnly
    )

object LowQual yNonFollo rW hUqfRule
    extends NonFollo rW hUqfUserLabelDropRule(
      LowQual y
    )

object TsV olat onRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      TsV olat on
    )

object DownrankSpamReplyAllV e rsRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      DownrankSpamReply
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySect on ngRuleParam)
}

object DownrankSpamReplyNonAuthorRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      DownrankSpamReply
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySect on ngRuleParam)
}

object DownrankSpamReplyNonFollo rW hUqfRule
    extends NonFollo rW hUqfUserLabelDropRule(DownrankSpamReply) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySect on ngRuleParam)
}

object NsfwTextAllUsersDropRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      NsfwText
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableNsfwTextSect on ngRuleParam)
}

object NsfwTextNonAuthorDropRule
    extends W nAuthorUserLabelPresentRule(
      Drop(Unspec f ed),
      DownrankSpamReply
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableNsfwTextSect on ngRuleParam)
}

abstract class Dec derableSpamH ghRecallAuthorLabelRule(act on: Act on)
    extends RuleW hConstantAct on(
      act on,
      And(
        NonAuthorV e r,
        SelfReply,
        AuthorHasLabel(SpamH ghRecall, shortC rcu able = false)
      )
    ) {
  overr de def preF lter(
    evaluat onContext: Evaluat onContext,
    featureMap: Map[Feature[_], Any],
    abDec der: Logg ngABDec der
  ): PreF lterResult = {
    F ltered
  }
}

object Dec derableSpamH ghRecallAuthorLabelDropRule
    extends Dec derableSpamH ghRecallAuthorLabelRule(Drop(Unspec f ed))

object Dec derableSpamH ghRecallAuthorLabelTombstoneRule
    extends Dec derableSpamH ghRecallAuthorLabelRule(Tombstone(Ep aph.Unava lable))

object DoNotAmpl fyNonFollo rRule
    extends AuthorLabelAndNonFollo rV e rRule(
      Drop(Unspec f ed),
      DoNotAmpl fy
    )

object NotGraduatedNonFollo rRule
    extends AuthorLabelAndNonFollo rV e rRule(
      Drop(Unspec f ed),
      NotGraduated
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNotGraduatedDropRuleParam)
  overr de def holdbacks: Seq[RuleParam[Boolean]] = Seq(
    NotGraduatedUserLabelRuleHoldbackExper  ntParam)

}

object DoNotAmpl fySect onUserRule
    extends AuthorLabelW hNot nnerC rcleOfFr endsRule(
      Conversat onSect onAbus veQual y,
      DoNotAmpl fy)
    w h DoesLogVerd ctDec dered {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    UserSafetyLabelS ceBu lder(DoNotAmpl fy))
  overr de def verd ctLogDec derKey = Dec derKey.EnableDownlevelRuleVerd ctLogg ng
}


object Spam UserModelH ghPrec s onDropT etRule
    extends AuthorLabelAndNonFollo rV e rRule(
      Drop(Unspec f ed),
      Spam UserModelH ghPrec s on,
    )
    w h DoesLogVerd ctDec dered {
  overr de def  sEnabled(params: Params): Boolean =
    params(EnableSpam UserModelT etDropRuleParam)
  overr de def verd ctLogDec derKey: Dec derKey.Value =
    Dec derKey.EnableSpam T etRuleVerd ctLogg ng
}

object L kely vsLabelNonFollo rDropUserRule extends L kely vsLabelNonFollo rDropRule

object SearchL kely vsLabelNonFollo rDropUserRule extends L kely vsLabelNonFollo rDropRule

object NsfwH ghPrec s onUserLabelAvo dT etRule
    extends UserHasLabelRule(
      Avo d(),
      UserLabelValue.NsfwH ghPrec s on
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    NsfwH ghPrec s onUserLabelAvo dT etRuleEnabledParam)
}
