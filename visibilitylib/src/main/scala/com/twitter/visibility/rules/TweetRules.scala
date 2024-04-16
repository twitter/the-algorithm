package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.common.act ons.L m edEngage ntReason
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.AdAvo danceH ghTox c yModelScoreThresholdParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.AdAvo danceReportedT etModelScoreThresholdParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.Commun yT etCommun yUnava lableL m edAct onsRulesEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.Commun yT etDropProtectedRuleEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.Commun yT etDropRuleEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.Commun yT etL m edAct onsRulesEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.Commun yT et mberRemovedL m edAct onsRulesEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.Commun yT etNon mberL m edAct onsRuleEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.StaleT etL m edAct onsRulesEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.TrustedFr endsT etL m edEngage ntsRuleEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.conf gap .params.RuleParams._
 mport com.tw ter.v s b l y.features.{T etDeleteReason => FeatureT etDeleteReason}
 mport com.tw ter.v s b l y.models.T etDeleteReason
 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.v s b l y.rules.Cond  on.V e r sExclus veT etAuthor
 mport com.tw ter.v s b l y.rules.Cond  on._
 mport com.tw ter.v s b l y.rules.Reason.Commun yT etAuthorRemoved
 mport com.tw ter.v s b l y.rules.Reason.Commun yT etH dden
 mport com.tw ter.v s b l y.rules.Reason.Nsfw
 mport com.tw ter.v s b l y.rules.Reason.StaleT et
 mport com.tw ter.v s b l y.rules.Reason.Unspec f ed
 mport com.tw ter.v s b l y.rules.RuleAct onS ceBu lder.T etSafetyLabelS ceBu lder

abstract class T etHasLabelRule(act on: Act on, t etSafetyLabelType: T etSafetyLabelType)
    extends RuleW hConstantAct on(act on, T etHasLabel(t etSafetyLabelType)) {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(t etSafetyLabelType))
}

abstract class Cond  onW hT etLabelRule(
  act on: Act on,
  cond  on: Cond  on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends RuleW hConstantAct on(act on, And(T etHasLabel(t etSafetyLabelType), cond  on)) {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(t etSafetyLabelType))
}

abstract class NonAuthorW hT etLabelRule(
  act on: Act on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(act on, NonAuthorV e r, t etSafetyLabelType) {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(t etSafetyLabelType))
}

abstract class NonFollo rW hT etLabelRule(
  act on: Act on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      act on,
      LoggedOutOrV e rNotFollow ngAuthor,
      t etSafetyLabelType
    )

abstract class NonAuthorAndNonFollo rW hT etLabelRule(
  act on: Act on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      act on,
      And(NonAuthorV e r, LoggedOutOrV e rNotFollow ngAuthor),
      t etSafetyLabelType
    )

abstract class NonFollo rW hUqfT etLabelRule(
  act on: Act on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      act on,
      Or(
        LoggedOutV e r,
        And(
          NonAuthorV e r,
          Not(V e rDoesFollowAuthor),
          V e rHasUqfEnabled
        )
      ),
      t etSafetyLabelType
    )

abstract class V e rW hUqfT etLabelRule(act on: Act on, labelValue: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(act on, V e rHasUqfEnabled, labelValue)

case object Conversat onControlRules {

  abstract class Conversat onControlBaseRule(cond  on: Cond  on)
      extends RuleW hConstantAct on(
        L m edEngage nts(L m edEngage ntReason.Conversat onControl),
        cond  on) {
    overr de def enabled: Seq[RuleParam[Boolean]] = Seq(T etConversat onControlEnabledParam)
  }

  object L m Repl esCommun yConversat onRule
      extends Conversat onControlBaseRule(
        And(
          T et sCommun yConversat on,
          Not(
            Or(
              LoggedOutV e r,
              Ret et,
              V e r sT etConversat onRootAuthor,
              V e r s nv edToT etConversat on,
              Conversat onRootAuthorDoesFollowV e r
            ))
        )
      )

  object L m Repl esFollo rsConversat onRule
      extends Conversat onControlBaseRule(
        And(
          T et sFollo rsConversat on,
          Not(
            Or(
              LoggedOutV e r,
              Ret et,
              V e r sT etConversat onRootAuthor,
              V e r s nv edToT etConversat on,
              V e rDoesFollowConversat onRootAuthor
            ))
        )
      )

  object L m Repl esBy nv at onConversat onRule
      extends Conversat onControlBaseRule(
        And(
          T et sBy nv at onConversat on,
          Not(
            Or(
              LoggedOutV e r,
              Ret et,
              V e r sT etConversat onRootAuthor,
              V e r s nv edToT etConversat on
            ))
        )
      )

}

abstract class NonAuthorV e rOpt nF lter ngW hT etLabelRule(
  act on: Act on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      act on,
      And(NonAuthorV e r, LoggedOutOrV e rOpt nF lter ng),
      t etSafetyLabelType)

abstract class NonFollo rV e rOpt nF lter ngW hT etLabelRule(
  act on: Act on,
  t etSafetyLabelType: T etSafetyLabelType)
    extends Cond  onW hT etLabelRule(
      act on,
      And(LoggedOutOrV e rNotFollow ngAuthor, LoggedOutOrV e rOpt nF lter ng),
      t etSafetyLabelType
    )

object T etNsfwUserDropRule extends RuleW hConstantAct on(Drop(Nsfw), T etHasNsfwUserAuthor)
object T etNsfwAdm nDropRule extends RuleW hConstantAct on(Drop(Nsfw), T etHasNsfwAdm nAuthor)

object NullcastedT etRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(Nullcast, Not(Ret et), Not( sQuoted nnerT et), Not(T et sCommun yT et)))

object MutedRet etsRule
    extends RuleW hConstantAct on(Drop(Unspec f ed), And(Ret et, V e rMutesRet etsFromAuthor))

abstract class F lterCommun yT etsRule(overr de val act on: Act on)
    extends RuleW hConstantAct on(act on, T et sCommun yT et) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(Commun yT etDropRuleEnabledParam)
}

object DropCommun yT etsRule extends F lterCommun yT etsRule(Drop(Commun yT etH dden))

object TombstoneCommun yT etsRule
    extends F lterCommun yT etsRule(Tombstone(Ep aph.Unava lable))

abstract class F lterCommun yT etCommun yNotV s bleRule(overr de val act on: Act on)
    extends RuleW hConstantAct on(
      act on,
      And(
        NonAuthorV e r,
        T et sCommun yT et,
        Not(Commun yT etCommun yV s ble),
      )) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(DropCommun yT etW hUndef nedCommun yRuleEnabledParam)
}

object DropCommun yT etCommun yNotV s bleRule
    extends F lterCommun yT etCommun yNotV s bleRule(Drop(Commun yT etH dden))

object TombstoneCommun yT etCommun yNotV s bleRule
    extends F lterCommun yT etCommun yNotV s bleRule(Tombstone(Ep aph.Unava lable))

abstract class F lterAllCommun yT etsRule(overr de val act on: Act on)
    extends RuleW hConstantAct on(act on, T et sCommun yT et) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(Commun yT etsEnabledParam)
}

object DropAllCommun yT etsRule extends F lterAllCommun yT etsRule(Drop(Unspec f ed))

object TombstoneAllCommun yT etsRule
    extends F lterAllCommun yT etsRule(Tombstone(Ep aph.Unava lable))

object DropOuterCommun yT etsRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(T et sCommun yT et, Not( sQuoted nnerT et)))

object DropAllH ddenCommun yT etsRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(T et sCommun yT et, Commun yT et sH dden))

abstract class F lterH ddenCommun yT etsRule(overr de val act on: Act on)
    extends RuleW hConstantAct on(
      act on,
      And(
        NonAuthorV e r,
        T et sCommun yT et,
        Commun yT et sH dden,
        Not(V e r sCommun yModerator)
      ))

object DropH ddenCommun yT etsRule
    extends F lterH ddenCommun yT etsRule(Drop(Commun yT etH dden))

object TombstoneH ddenCommun yT etsRule
    extends F lterH ddenCommun yT etsRule(Tombstone(Ep aph.Commun yT etH dden))

object DropAllAuthorRemovedCommun yT etsRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(T et sCommun yT et, Commun yT etAuthor sRemoved))

abstract class F lterAuthorRemovedCommun yT etsRule(overr de val act on: Act on)
    extends RuleW hConstantAct on(
      act on,
      And(
        NonAuthorV e r,
        T et sCommun yT et,
        Commun yT etAuthor sRemoved,
        Not(V e r sCommun yModerator)
      ))

object DropAuthorRemovedCommun yT etsRule
    extends F lterAuthorRemovedCommun yT etsRule(Drop(Commun yT etAuthorRemoved))

object TombstoneAuthorRemovedCommun yT etsRule
    extends F lterAuthorRemovedCommun yT etsRule(Tombstone(Ep aph.Unava lable))

abstract class F lterProtectedCommun yT etsRule(overr de val act on: Act on)
    extends RuleW hConstantAct on(
      act on,
      And(T et sCommun yT et, ProtectedAuthor, NonAuthorV e r)) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(Commun yT etDropProtectedRuleEnabledParam)
}

object DropProtectedCommun yT etsRule
    extends F lterProtectedCommun yT etsRule(Drop(Commun yT etH dden))

object TombstoneProtectedCommun yT etsRule
    extends F lterProtectedCommun yT etsRule(Tombstone(Ep aph.Unava lable))

abstract class Commun yT etCommun yUnava lableL m edAct onsRule(
  reason: L m edEngage ntReason,
  cond  on: Commun yT etCommun yUnava lable,
) extends RuleW hConstantAct on(
      L m edEngage nts(reason),
      And(
        Not(NonAuthorV e r),
        T et sCommun yT et,
        cond  on,
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    Commun yT etCommun yUnava lableL m edAct onsRulesEnabledParam)
}

object Commun yT etCommun yNotFoundL m edAct onsRule
    extends Commun yT etCommun yUnava lableL m edAct onsRule(
      L m edEngage ntReason.Commun yT etCommun yNotFound,
      Commun yT etCommun yNotFound,
    )

object Commun yT etCommun yDeletedL m edAct onsRule
    extends Commun yT etCommun yUnava lableL m edAct onsRule(
      L m edEngage ntReason.Commun yT etCommun yDeleted,
      Commun yT etCommun yDeleted,
    )

object Commun yT etCommun ySuspendedL m edAct onsRule
    extends Commun yT etCommun yUnava lableL m edAct onsRule(
      L m edEngage ntReason.Commun yT etCommun ySuspended,
      Commun yT etCommun ySuspended,
    )

abstract class Commun yT etModeratedL m edAct onsRule(
  reason: L m edEngage ntReason,
  cond  on: Commun yT et sModerated,
  enabledParam: RuleParam[Boolean],
) extends RuleW hConstantAct on(
      L m edEngage nts(reason),
      And(
        T et sCommun yT et,
        cond  on,
        Or(
          Not(NonAuthorV e r),
          V e r sCommun yModerator,
        )
      )) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(enabledParam)
}

object Commun yT et mberRemovedL m edAct onsRule
    extends Commun yT etModeratedL m edAct onsRule(
      L m edEngage ntReason.Commun yT et mberRemoved,
      Commun yT etAuthor sRemoved,
      Commun yT et mberRemovedL m edAct onsRulesEnabledParam,
    )

object Commun yT etH ddenL m edAct onsRule
    extends Commun yT etModeratedL m edAct onsRule(
      L m edEngage ntReason.Commun yT etH dden,
      Commun yT et sH dden,
      Commun yT etL m edAct onsRulesEnabledParam,
    )

abstract class Commun yT etL m edAct onsRule(
  reason: L m edEngage ntReason,
  cond  on: Cond  on,
) extends RuleW hConstantAct on(
      L m edEngage nts(reason),
      And(
        T et sCommun yT et,
        cond  on
      )) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(Commun yT etL m edAct onsRulesEnabledParam)
}

object Commun yT et mberL m edAct onsRule
    extends Commun yT etL m edAct onsRule(
      L m edEngage ntReason.Commun yT et mber,
      V e r sCommun y mber,
    )

object Commun yT etNon mberL m edAct onsRule
    extends Commun yT etL m edAct onsRule(
      L m edEngage ntReason.Commun yT etNon mber,
      Not(V e r sCommun y mber),
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    Commun yT etNon mberL m edAct onsRuleEnabledParam)
}

object ReportedT et nterst  alRule
    extends RuleW hConstantAct on(
       nterst  al(Reason.V e rReportedT et),
      And(
        NonAuthorV e r,
        Not(Ret et),
        V e rReportsT et
      )) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableReportedT et nterst  alRule)
}

object ReportedT et nterst  alSearchRule
    extends RuleW hConstantAct on(
       nterst  al(Reason.V e rReportedT et),
      And(
        NonAuthorV e r,
        Not(Ret et),
        V e rReportsT et
      )) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableReportedT et nterst  alSearchRule)
}

abstract class F lterExclus veT etContentRule(
  act on: Act on,
  add  onalCond  on: Cond  on = Cond  on.True)
    extends RuleW hConstantAct on(
      act on,
      And(
        add  onalCond  on,
        T et sExclus veContent,
        Or(
          LoggedOutV e r,
          Not(
            Or(
              V e r sExclus veT etAuthor,
              V e rSuperFollowsExclus veT etAuthor,
              And(
                Not(NonAuthorV e r),
                Not(Ret et)
              )
            )
          ),
        ),
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropExclus veT etContentRule)
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(
    EnableDropExclus veT etContentRuleFa lClosed)
}

object DropExclus veT etContentRule
    extends F lterExclus veT etContentRule(Drop(Reason.Exclus veT et))

object TombstoneExclus veT etContentRule
    extends F lterExclus veT etContentRule(Tombstone(Ep aph.SuperFollowsContent))

object TombstoneExclus veQuotedT etContentRule
    extends F lterExclus veT etContentRule(
      Tombstone(Ep aph.SuperFollowsContent),
       sQuoted nnerT et
    )

object DropAllExclus veT etsRule
    extends RuleW hConstantAct on(
      Drop(Reason.Exclus veT et),
      T et sExclus veContent
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropAllExclus veT etsRuleParam)
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(
    EnableDropAllExclus veT etsRuleFa lClosedParam)
}

object DropT etsW hGeoRestr cted d aRule
    extends RuleW hConstantAct on(Drop(Unspec f ed),  d aRestr cted nV e rCountry) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableDropT etsW hGeoRestr cted d aRuleParam)
}

object TrustedFr endsT etL m edEngage ntsRule
    extends RuleW hConstantAct on(
      L m edEngage nts(L m edEngage ntReason.TrustedFr endsT et),
      T et sTrustedFr endsContent
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    TrustedFr endsT etL m edEngage ntsRuleEnabledParam
  )
}

object DropAllTrustedFr endsT etsRule
    extends RuleW hConstantAct on(
      Drop(Reason.TrustedFr endsT et),
      T et sTrustedFr endsContent
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropAllTrustedFr endsT etsRuleParam)
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

object DropAllCollab nv at onT etsRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      T et sCollab nv at onContent
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropAllCollab nv at onT etsRuleParam)
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

abstract class F lterTrustedFr endsT etContentRule(act on: Act on)
    extends OnlyW nNotAuthorV e rRule(
      act on,
      And(
        T et sTrustedFr endsContent,
        Not(
          Or(
            V e r sTrustedFr endsT etAuthor,
            V e r sTrustedFr end
          )
        )
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropTrustedFr endsT etContentRuleParam)
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

object DropTrustedFr endsT etContentRule
    extends F lterTrustedFr endsT etContentRule(Drop(Reason.TrustedFr endsT et))

object TombstoneTrustedFr endsT etContentRule
    extends F lterTrustedFr endsT etContentRule(Tombstone(Ep aph.Unava lable))

object T etNsfwUserAdm nAvo dRule
    extends RuleW hConstantAct on(
      Avo d(),
      Or(
        T etHasNsfwUserAuthor,
        T etHasNsfwAdm nAuthor,
        NsfwUserAuthor,
        NsfwAdm nAuthor
      )
    )

object Avo dH ghTox c yModelScoreRule
    extends RuleW hConstantAct on(
      Avo d(),
      T etHasLabelW hScoreAboveThresholdW hParam(
        T etSafetyLabelType.H ghTox c yScore,
        AdAvo danceH ghTox c yModelScoreThresholdParam)
    )

object Avo dReportedT etModelScoreRule
    extends RuleW hConstantAct on(
      Avo d(),
      T etHasLabelW hScoreAboveThresholdW hParam(
        T etSafetyLabelType.H ghPReportedT etScore,
        AdAvo danceReportedT etModelScoreThresholdParam)
    )

object TombstoneDeletedOuterT etRule
    extends RuleW hConstantAct on(
      Tombstone(Ep aph.Deleted),
      And(
        Equals(FeatureT etDeleteReason, T etDeleteReason.Deleted),
        Not( sQuoted nnerT et)
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateT etRulesParam)
}

object TombstoneDeletedT etRule
    extends RuleW hConstantAct on(
      Tombstone(Ep aph.Deleted),
      And(
        Equals(FeatureT etDeleteReason, T etDeleteReason.Deleted),
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateT etRulesParam)
}

object TombstoneDeletedQuotedT etRule
    extends RuleW hConstantAct on(
      Tombstone(Ep aph.Deleted),
      And(
        Equals(FeatureT etDeleteReason, T etDeleteReason.Deleted),
         sQuoted nnerT et
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateT etRulesParam)
}

object TombstoneBounceDeletedT etRule
    extends RuleW hConstantAct on(
      Tombstone(Ep aph.BounceDeleted),
      Equals(FeatureT etDeleteReason, T etDeleteReason.BounceDeleted),
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateT etRulesParam)
}

object TombstoneBounceDeletedOuterT etRule
    extends RuleW hConstantAct on(
      Tombstone(Ep aph.BounceDeleted),
      And(
        Equals(FeatureT etDeleteReason, T etDeleteReason.BounceDeleted),
        Not( sQuoted nnerT et)
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateT etRulesParam)
}

object TombstoneBounceDeletedQuotedT etRule
    extends RuleW hConstantAct on(
      Tombstone(Ep aph.BounceDeleted),
      And(
        Equals(FeatureT etDeleteReason, T etDeleteReason.BounceDeleted),
         sQuoted nnerT et
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateT etRulesParam)
}


object DropStaleT etsRule
    extends RuleW hConstantAct on(
      Drop(StaleT et),
      And(T et sStaleT et, Not( sQuoted nnerT et), Not(Ret et), Not( sS ceT et))) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableStaleT etDropRuleParam)
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(
    EnableStaleT etDropRuleFa lClosedParam)
}

object StaleT etL m edAct onsRule
    extends RuleW hConstantAct on(
      L m edEngage nts(L m edEngage ntReason.StaleT et),
      T et sStaleT et) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(StaleT etL m edAct onsRulesEnabledParam)
}
