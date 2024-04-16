package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.common.ModelScoreThresholds
 mport com.tw ter.v s b l y.common.act ons.Avo dReason
 mport com.tw ter.v s b l y.common.act ons.Avo dReason.M ghtNotBeSu ableForAds
 mport com.tw ter.v s b l y.common.act ons.L m edEngage ntReason
 mport com.tw ter.v s b l y.common.act ons.T etV s b l yNudgeReason
 mport com.tw ter.v s b l y.conf gap .conf gs.Dec derKey
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.H ghSpam T etContentScoreSearchLatestProdT etLabelDropRuleThresholdParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.H ghSpam T etContentScoreSearchTopProdT etLabelDropRuleThresholdParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.H ghSpam T etContentScoreTrendLatestT etLabelDropRuleThresholdParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.H ghSpam T etContentScoreTrendTopT etLabelDropRuleThresholdParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.Sk pT etDeta lL m edEngage ntRuleEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams._
 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.v s b l y.rules.Cond  on._
 mport com.tw ter.v s b l y.rules.Cond  on.{True => TrueCond  on}
 mport com.tw ter.v s b l y.rules.Reason._
 mport com.tw ter.v s b l y.rules.RuleAct onS ceBu lder.T etSafetyLabelS ceBu lder

object Abus veT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.Abus ve
    )
    w h DoesLogVerd ct

object Abus veNonFollo rT etLabelRule
    extends NonFollo rW hT etLabelRule(
      Drop(Tox c y),
      T etSafetyLabelType.Abus ve
    )

object Abus veUqfNonFollo rT etLabelRule
    extends NonFollo rW hUqfT etLabelRule(
      Drop(Tox c y),
      T etSafetyLabelType.Abus ve
    )

object Abus veH ghRecallT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.Abus veH ghRecall
    )

object Abus veH ghRecallNonFollo rT etLabelRule
    extends NonFollo rW hT etLabelRule(
       nterst  al(Poss blyUndes rable),
      T etSafetyLabelType.Abus veH ghRecall
    )

object Automat onT etLabelRule
    extends NonFollo rW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.Automat on
    )

object BystanderAbus veT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.BystanderAbus ve
    )

object BystanderAbus veNonFollo rT etLabelRule
    extends NonFollo rW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.BystanderAbus ve
    )

abstract class Dupl cateContentT etLabelRule(act on: Act on)
    extends NonAuthorW hT etLabelRule(
      act on,
      T etSafetyLabelType.Dupl cateContent
    )

object Dupl cateContentT etLabelDropRule
    extends Dupl cateContentT etLabelRule(Drop(T etLabelDupl cateContent))

object Dupl cateContentT etLabelTombstoneRule
    extends Dupl cateContentT etLabelRule(Tombstone(Ep aph.Unava lable))

object Dupl cate nt onT etLabelRule
    extends NonFollo rW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.Dupl cate nt on
    )

object Dupl cate nt onUqfT etLabelRule
    extends NonFollo rW hUqfT etLabelRule(
      Drop(T etLabelDupl cate nt on),
      T etSafetyLabelType.Dupl cate nt on
    )

object GoreAndV olenceT etLabelRule
    extends Cond  onW hT etLabelRule(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        T etComposedBefore(T etSafetyLabelType.GoreAndV olence.DeprecatedAt)
      ),
      T etSafetyLabelType.GoreAndV olence
    )

object L veLowQual yT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.L veLowQual y
    )

object LowQual y nt onT etLabelRule
    extends RuleW hConstantAct on(
      Drop(LowQual y nt on),
      And(
        T etHasLabelForPerspect valUser(T etSafetyLabelType.LowQual y nt on),
        V e rHasUqfEnabled
      )
    )

abstract class NsfwCard mageT etLabelBaseRule(
  overr de val act on: Act on,
  val add  onalCond  on: Cond  on = TrueCond  on,
) extends RuleW hConstantAct on(
      act on,
      And(
        add  onalCond  on,
        T etHasLabel(T etSafetyLabelType.NsfwCard mage)
      )
    )

object NsfwCard mageT etLabelRule
    extends NsfwCard mageT etLabelBaseRule(
      act on = Drop(Nsfw),
      add  onalCond  on = NonAuthorV e r,
    )

object NsfwCard mageAllUsersT etLabelRule
    extends NsfwCard mageT etLabelBaseRule(
      act on =  nterst  al(Nsfw)
    )

object NsfwCard mageAvo dAllUsersT etLabelRule
    extends NsfwCard mageT etLabelBaseRule(
      act on = Avo d(So (Avo dReason.Conta nsNsfw d a)),
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)

  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object NsfwCard mageAvo dAdPlace ntAllUsersT etLabelRule
    extends NsfwCard mageT etLabelBaseRule(
      act on = Avo d(So (Avo dReason.Conta nsNsfw d a)),
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)
}

object SearchAvo dT etNsfwAdm nRule
    extends RuleW hConstantAct on(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etHasNsfwAdm nAuthor
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)
}

object SearchAvo dT etNsfwUserRule
    extends RuleW hConstantAct on(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etHasNsfwUserAuthor
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)
}

object NsfwCard mageAllUsersT etLabelDropRule
    extends NsfwCard mageT etLabelBaseRule(
      act on = Drop(Nsfw),
    )

object H ghProact veTosScoreT etLabelDropRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.H ghProact veTosScore
    )

object H ghProact veTosScoreT etLabelDropSearchRule
    extends NonAuthorAndNonFollo rW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.H ghProact veTosScore
    )

object NsfwH ghPrec s onT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwH ghPrec s on
    )

object NsfwH ghPrec s onAllUsersT etLabelDropRule
    extends T etHasLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwH ghPrec s on
    )

object NsfwH ghPrec s on nnerQuotedT etLabelRule
    extends Cond  onW hT etLabelRule(
      Drop(Nsfw),
      And( sQuoted nnerT et, NonAuthorV e r),
      T etSafetyLabelType.NsfwH ghPrec s on
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwHpQuotedT etDropRuleParam)
}

object NsfwH ghPrec s onTombstone nnerQuotedT etLabelRule
    extends Cond  onW hT etLabelRule(
      Tombstone(Ep aph.Unava lable),
      And( sQuoted nnerT et, NonAuthorV e r),
      T etSafetyLabelType.NsfwH ghPrec s on
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwHpQuotedT etTombstoneRuleParam)
}

object GoreAndV olenceH ghPrec s onT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.GoreAndV olenceH ghPrec s on
    )

object NsfwReported ur st csT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwReported ur st cs
    )

object GoreAndV olenceReported ur st csT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.GoreAndV olenceReported ur st cs
    )

object NsfwH ghPrec s on nterst  alAllUsersT etLabelRule
    extends T etHasLabelRule(
       nterst  al(Nsfw),
      T etSafetyLabelType.NsfwH ghPrec s on
    )
    w h DoesLogVerd ct

object GoreAndV olenceH ghPrec s onAvo dAllUsersT etLabelRule
    extends T etHasLabelRule(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etSafetyLabelType.GoreAndV olenceH ghPrec s on
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)

  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object GoreAndV olenceH ghPrec s onAllUsersT etLabelRule
    extends T etHasLabelRule(
       nterst  al(Nsfw),
      T etSafetyLabelType.GoreAndV olenceH ghPrec s on
    )
    w h DoesLogVerd ct {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.GoreAndV olenceH ghPrec s on)
  )
}

object NsfwReported ur st csAvo dAllUsersT etLabelRule
    extends T etHasLabelRule(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etSafetyLabelType.NsfwReported ur st cs
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)

  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object NsfwReported ur st csAvo dAdPlace ntAllUsersT etLabelRule
    extends T etHasLabelRule(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etSafetyLabelType.NsfwReported ur st cs
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)

  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object NsfwReported ur st csAllUsersT etLabelRule
    extends T etHasLabelRule(
       nterst  al(Nsfw),
      T etSafetyLabelType.NsfwReported ur st cs
    )

object GoreAndV olenceReported ur st csAllUsersT etLabelRule
    extends T etHasLabelRule(
       nterst  al(Nsfw),
      T etSafetyLabelType.GoreAndV olenceReported ur st cs
    )

object GoreAndV olenceReported ur st csAvo dAllUsersT etLabelRule
    extends T etHasLabelRule(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etSafetyLabelType.GoreAndV olenceReported ur st cs
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)

  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object GoreAndV olenceReported ur st csAvo dAdPlace ntAllUsersT etLabelRule
    extends T etHasLabelRule(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etSafetyLabelType.GoreAndV olenceReported ur st cs
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvo dNsfwRulesParam)

  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object GoreAndV olenceH ghPrec s onAllUsersT etLabelDropRule
    extends T etHasLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.GoreAndV olenceH ghPrec s on
    )

object NsfwReported ur st csAllUsersT etLabelDropRule
    extends T etHasLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwReported ur st cs
    )

object GoreAndV olenceReported ur st csAllUsersT etLabelDropRule
    extends T etHasLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.GoreAndV olenceReported ur st cs
    )

object NsfwH ghRecallT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwH ghRecall
    )

object NsfwH ghRecallAllUsersT etLabelDropRule
    extends T etHasLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwH ghRecall
    )

abstract class PdnaT etLabelRule(
  overr de val act on: Act on,
  val add  onalCond  on: Cond  on)
    extends Cond  onW hT etLabelRule(
      act on,
      And(NonAuthorV e r, add  onalCond  on),
      T etSafetyLabelType.Pdna
    )

object PdnaT etLabelRule extends PdnaT etLabelRule(Drop(PdnaT et), Cond  on.True)

object PdnaT etLabelTombstoneRule
    extends PdnaT etLabelRule(Tombstone(Ep aph.Unava lable), Cond  on.True)

object PdnaQuotedT etLabelTombstoneRule
    extends PdnaT etLabelRule(Tombstone(Ep aph.Unava lable), Cond  on. sQuoted nnerT et) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnablePdnaQuotedT etTombstoneRuleParam)
}

object PdnaAllUsersT etLabelRule
    extends T etHasLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.Pdna
    )

object SearchBlackl stT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.SearchBlackl st
    )

object SearchBlackl stH ghRecallT etLabelDropRule
    extends NonAuthorAndNonFollo rW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.SearchBlackl stH ghRecall
    )

abstract class SpamT etLabelRule(
  overr de val act on: Act on,
  val add  onalCond  on: Cond  on)
    extends Cond  onW hT etLabelRule(
      act on,
      And(NonAuthorV e r, add  onalCond  on),
      T etSafetyLabelType.Spam
    )
    w h DoesLogVerd ct

object SpamT etLabelRule extends SpamT etLabelRule(Drop(T etLabeledSpam), Cond  on.True)

object SpamT etLabelTombstoneRule
    extends SpamT etLabelRule(Tombstone(Ep aph.Unava lable), Cond  on.True)

object SpamQuotedT etLabelTombstoneRule
    extends SpamT etLabelRule(Tombstone(Ep aph.Unava lable), Cond  on. sQuoted nnerT et) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableSpamQuotedT etTombstoneRuleParam)
}

object SpamAllUsersT etLabelRule
    extends T etHasLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.Spam
    )

abstract class BounceT etLabelRule(overr de val act on: Act on)
    extends NonAuthorW hT etLabelRule(
      act on,
      T etSafetyLabelType.Bounce
    )

object BounceT etLabelRule extends BounceT etLabelRule(Drop(Bounce))

object BounceT etLabelTombstoneRule extends BounceT etLabelRule(Tombstone(Ep aph.Bounced))

abstract class BounceOuterT etLabelRule(overr de val act on: Act on)
    extends Cond  onW hT etLabelRule(
      act on,
      And(Not(Cond  on. sQuoted nnerT et), NonAuthorV e r),
      T etSafetyLabelType.Bounce
    )

object BounceOuterT etTombstoneRule extends BounceOuterT etLabelRule(Tombstone(Ep aph.Bounced))

object BounceQuotedT etTombstoneRule
    extends Cond  onW hT etLabelRule(
      Tombstone(Ep aph.Bounced),
      Cond  on. sQuoted nnerT et,
      T etSafetyLabelType.Bounce
    )

object BounceAllUsersT etLabelRule
    extends T etHasLabelRule(
      Drop(Bounce),
      T etSafetyLabelType.Bounce
    )


abstract class SpamH ghRecallT etLabelRule(act on: Act on)
    extends NonAuthorW hT etLabelRule(
      act on,
      T etSafetyLabelType.SpamH ghRecall
    )

object SpamH ghRecallT etLabelDropRule
    extends SpamH ghRecallT etLabelRule(Drop(SpamH ghRecallT et))

object SpamH ghRecallT etLabelTombstoneRule
    extends SpamH ghRecallT etLabelRule(Tombstone(Ep aph.Unava lable))

object UntrustedUrlAllV e rsT etLabelRule
    extends T etHasLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.UntrustedUrl
    )

object DownrankSpamReplyAllV e rsT etLabelRule
    extends T etHasLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.DownrankSpamReply
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySect on ngRuleParam)
}

object UntrustedUrlT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.UntrustedUrl
    )

object DownrankSpamReplyT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.DownrankSpamReply
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySect on ngRuleParam)
}

object UntrustedUrlUqfNonFollo rT etLabelRule
    extends NonFollo rW hUqfT etLabelRule(
      Drop(UntrustedUrl),
      T etSafetyLabelType.UntrustedUrl
    )

object DownrankSpamReplyUqfNonFollo rT etLabelRule
    extends NonFollo rW hUqfT etLabelRule(
      Drop(SpamReplyDownRank),
      T etSafetyLabelType.DownrankSpamReply
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySect on ngRuleParam)
}

object NsfaH ghRecallT etLabelRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        T etHasLabel(T etSafetyLabelType.NsfaH ghRecall)
      )
    )

object NsfaH ghRecallT etLabel nterst  alRule
    extends RuleW hConstantAct on(
       nterst  al(Unspec f ed),
      And(
        NonAuthorV e r,
        T etHasLabel(T etSafetyLabelType.NsfaH ghRecall)
      )
    )

object NsfwV deoT etLabelDropRule
    extends NonAuthorW hT etLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwV deo
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTextSect on ngRuleParam)
}

object NsfwTextT etLabelDropRule
    extends NonAuthorW hT etLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwText
    )

object NsfwV deoAllUsersT etLabelDropRule
    extends T etHasLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwV deo
    )

object NsfwTextAllUsersT etLabelDropRule
    extends T etHasLabelRule(
      Drop(Nsfw),
      T etSafetyLabelType.NsfwText
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTextSect on ngRuleParam)
}

abstract class BaseLowQual yT etLabelRule(act on: Act on)
    extends RuleW hConstantAct on(
      act on,
      And(
        T etHasLabel(T etSafetyLabelType.LowQual y),
        T etComposedBefore(Publ c nterest.Pol cyConf g.LowQual yProxyLabelStart),
        NonAuthorV e r
      )
    )
    w h DoesLogVerd ct {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.LowQual y))
}

object LowQual yT etLabelDropRule extends BaseLowQual yT etLabelRule(Drop(LowQual yT et))

object LowQual yT etLabelTombstoneRule
    extends BaseLowQual yT etLabelRule(Tombstone(Ep aph.Unava lable))

abstract class SafetyCr s sLevelDropRule(level:  nt, cond  on: Cond  on = TrueCond  on)
    extends Cond  onW hT etLabelRule(
      Drop(Unspec f ed),
      And(
        NonAuthorV e r,
        cond  on,
        T etHasSafetyLabelW hScoreEq nt(T etSafetyLabelType.SafetyCr s s, level)
      ),
      T etSafetyLabelType.SafetyCr s s
    )

object SafetyCr s sAnyLevelDropRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.SafetyCr s s
    )

object SafetyCr s sLevel2DropRule extends SafetyCr s sLevelDropRule(2, Not(V e rDoesFollowAuthor))

object SafetyCr s sLevel3DropRule extends SafetyCr s sLevelDropRule(3, Not(V e rDoesFollowAuthor))

object SafetyCr s sLevel4DropRule extends SafetyCr s sLevelDropRule(4)

abstract class SafetyCr s sLevelSect onRule(level:  nt)
    extends Cond  onW hNot nnerC rcleOfFr endsRule(
      Conversat onSect onAbus veQual y,
      And(
        T etHasLabel(T etSafetyLabelType.SafetyCr s s),
        T etHasSafetyLabelW hScoreEq nt(T etSafetyLabelType.SafetyCr s s, level))
    ) {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.SafetyCr s s))
}

object SafetyCr s sLevel3Sect onRule
    extends SafetyCr s sLevelSect onRule(3)
    w h DoesLogVerd ctDec dered {
  overr de def verd ctLogDec derKey = Dec derKey.EnableDownlevelRuleVerd ctLogg ng
}

object SafetyCr s sLevel4Sect onRule
    extends SafetyCr s sLevelSect onRule(4)
    w h DoesLogVerd ctDec dered {
  overr de def verd ctLogDec derKey = Dec derKey.EnableDownlevelRuleVerd ctLogg ng
}

object DoNotAmpl fyDropRule
    extends NonFollo rW hT etLabelRule(Drop(Unspec f ed), T etSafetyLabelType.DoNotAmpl fy)

object DoNotAmpl fyAllV e rsDropRule
    extends T etHasLabelRule(Drop(Unspec f ed), T etSafetyLabelType.DoNotAmpl fy)

object DoNotAmpl fySect onRule
    extends Cond  onW hNot nnerC rcleOfFr endsRule(
      Conversat onSect onAbus veQual y,
      T etHasLabel(T etSafetyLabelType.DoNotAmpl fy))

object H ghPSpam ScoreAllV e rDropRule
    extends T etHasLabelRule(Drop(Unspec f ed), T etSafetyLabelType.H ghPSpam T etScore)

object H ghPSpam T etScoreSearchT etLabelDropRule
    extends RuleW hConstantAct on(
      act on = Drop(Unspec f ed),
      cond  on = And(
        LoggedOutOrV e rNotFollow ngAuthor,
        T etHasLabelW hScoreAboveThreshold(
          T etSafetyLabelType.H ghPSpam T etScore,
          ModelScoreThresholds.H ghPSpam T etScoreThreshold)
      )
    )
    w h DoesLogVerd ctDec dered {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableH ghPSpam T etScoreSearchT etLabelDropRuleParam)
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.H ghPSpam T etScore))
  overr de def verd ctLogDec derKey: Dec derKey.Value =
    Dec derKey.EnableSpam T etRuleVerd ctLogg ng
}

object AdsManagerDenyL stAllUsersT etLabelRule
    extends T etHasLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.AdsManagerDenyL st
    )

abstract class S teSpamT etLabelRule(act on: Act on)
    extends NonAuthorW hT etLabelRule(
      act on,
      T etSafetyLabelType.S teSpamT et
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableS teSpamT etRuleParam)
}

object S teSpamT etLabelDropRule extends S teSpamT etLabelRule(Drop(T etLabeledSpam))

object S teSpamT etLabelTombstoneRule
    extends S teSpamT etLabelRule(Tombstone(Ep aph.Unava lable))

object S teSpamT etLabelDropSearchRule extends S teSpamT etLabelRule(Drop(Unspec f ed))

object H ghSpam T etContentScoreSearchLatestT etLabelDropRule
    extends RuleW hConstantAct on(
      act on = Drop(Unspec f ed),
      cond  on = And(
        Not( sT et nT etLevelStcmHoldback),
        LoggedOutOrV e rNotFollow ngAuthor,
        T etHasLabelW hScoreAboveThresholdW hParam(
          T etSafetyLabelType.H ghSpam T etContentScore,
          H ghSpam T etContentScoreSearchLatestProdT etLabelDropRuleThresholdParam)
      )
    )
    w h DoesLogVerd ctDec dered {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.H ghSpam T etContentScore))
  overr de def verd ctLogDec derKey: Dec derKey.Value =
    Dec derKey.EnableSpam T etRuleVerd ctLogg ng
}

object H ghSpam T etContentScoreSearchTopT etLabelDropRule
    extends RuleW hConstantAct on(
      act on = Drop(Unspec f ed),
      cond  on = And(
        Not( sT et nT etLevelStcmHoldback),
        LoggedOutOrV e rNotFollow ngAuthor,
        T etHasLabelW hScoreAboveThresholdW hParam(
          T etSafetyLabelType.H ghSpam T etContentScore,
          H ghSpam T etContentScoreSearchTopProdT etLabelDropRuleThresholdParam)
      )
    )
    w h DoesLogVerd ctDec dered {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.H ghSpam T etContentScore))
  overr de def verd ctLogDec derKey: Dec derKey.Value =
    Dec derKey.EnableSpam T etRuleVerd ctLogg ng

}

object H ghSpam T etContentScoreTrendsTopT etLabelDropRule
    extends RuleW hConstantAct on(
      act on = Drop(Unspec f ed),
      cond  on = And(
        Not( sT et nT etLevelStcmHoldback),
        LoggedOutOrV e rNotFollow ngAuthor,
         sTrendCl ckS ceSearchResult,
        T etHasLabelW hScoreAboveThresholdW hParam(
          T etSafetyLabelType.H ghSpam T etContentScore,
          H ghSpam T etContentScoreTrendTopT etLabelDropRuleThresholdParam)
      )
    )
    w h DoesLogVerd ctDec dered {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.H ghSpam T etContentScore))
  overr de def verd ctLogDec derKey: Dec derKey.Value =
    Dec derKey.EnableSpam T etRuleVerd ctLogg ng

}

object H ghSpam T etContentScoreTrendsLatestT etLabelDropRule
    extends RuleW hConstantAct on(
      act on = Drop(Unspec f ed),
      cond  on = And(
        Not( sT et nT etLevelStcmHoldback),
        LoggedOutOrV e rNotFollow ngAuthor,
         sTrendCl ckS ceSearchResult,
        T etHasLabelW hScoreAboveThresholdW hParam(
          T etSafetyLabelType.H ghSpam T etContentScore,
          H ghSpam T etContentScoreTrendLatestT etLabelDropRuleThresholdParam)
      )
    )
    w h DoesLogVerd ctDec dered {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.H ghSpam T etContentScore))
  overr de def verd ctLogDec derKey: Dec derKey.Value =
    Dec derKey.EnableSpam T etRuleVerd ctLogg ng
}

object GoreAndV olenceTop cH ghRecallT etLabelRule
    extends NonAuthorW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.GoreAndV olenceTop cH ghRecall
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableGoreAndV olenceTop cH ghRecallT etLabelRule)
}

object CopypastaSpamAllV e rsT etLabelRule
    extends T etHasLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.CopypastaSpam
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableCopypastaSpamSearchDropRule)
}

object CopypastaSpamAllV e rsSearchT etLabelRule
    extends T etHasLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.CopypastaSpam
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableCopypastaSpamSearchDropRule)
}

object CopypastaSpamNonFollo rSearchT etLabelRule
    extends NonFollo rW hT etLabelRule(
      Drop(Unspec f ed),
      T etSafetyLabelType.CopypastaSpam
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableCopypastaSpamSearchDropRule)
}

object CopypastaSpamAbus veQual yT etLabelRule
    extends Cond  onW hNot nnerC rcleOfFr endsRule(
      Conversat onSect onAbus veQual y,
      T etHasLabel(T etSafetyLabelType.CopypastaSpam)
    )
    w h DoesLogVerd ctDec dered {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableCopypastaSpamDownrankConvosAbus veQual yRule)
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.CopypastaSpam))
  overr de def verd ctLogDec derKey = Dec derKey.EnableDownlevelRuleVerd ctLogg ng
}

object Dynam cProductAdL m edEngage ntT etLabelRule
    extends T etHasLabelRule(
      L m edEngage nts(L m edEngage ntReason.Dynam cProductAd),
      T etSafetyLabelType.Dynam cProductAd)

object Sk pT etDeta lL m edEngage ntT etLabelRule
    extends AlwaysActRule(L m edEngage nts(L m edEngage ntReason.Sk pT etDeta l)) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    Sk pT etDeta lL m edEngage ntRuleEnabledParam)
}

object Dynam cProductAdDropT etLabelRule
    extends T etHasLabelRule(Drop(Unspec f ed), T etSafetyLabelType.Dynam cProductAd)

object NsfwTextH ghPrec s onT etLabelDropRule
    extends RuleW hConstantAct on(
      Drop(Reason.Nsfw),
      And(
        NonAuthorV e r,
        Or(
          T etHasLabel(T etSafetyLabelType.Exper  ntalSens  ve llegal2),
          T etHasLabel(T etSafetyLabelType.NsfwTextH ghPrec s on)
        )
      )
    )
    w h DoesLogVerd ct {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTextH ghPrec s onDropRuleParam)
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.NsfwTextH ghPrec s on))
}


object Exper  ntalNudgeLabelRule
    extends T etHasLabelRule(
      T etV s b l yNudge(T etV s b l yNudgeReason.Exper  ntalNudgeSafetyLabelReason),
      T etSafetyLabelType.Exper  ntalNudge) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableExper  ntalNudgeEnabledParam)
}

object NsfwTextT etLabelAvo dRule
    extends RuleW hConstantAct on(
      Avo d(),
      Or(
        T etHasLabel(T etSafetyLabelType.Exper  ntalSens  ve llegal2),
        T etHasLabel(T etSafetyLabelType.NsfwTextH ghPrec s on)
      )
    ) {
  overr de def act onS ceBu lder: Opt on[RuleAct onS ceBu lder] = So (
    T etSafetyLabelS ceBu lder(T etSafetyLabelType.NsfwTextH ghPrec s on))
}

object DoNotAmpl fyT etLabelAvo dRule
    extends T etHasLabelRule(
      Avo d(),
      T etSafetyLabelType.DoNotAmpl fy
    ) {
  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object NsfaH ghPrec s onT etLabelAvo dRule
    extends T etHasLabelRule(
      Avo d(),
      T etSafetyLabelType.NsfaH ghPrec s on
    ) {
  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object NsfwH ghPrec s onT etLabelAvo dRule
    extends T etHasLabelRule(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etSafetyLabelType.NsfwH ghPrec s on
    ) {
  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}

object NsfwH ghRecallT etLabelAvo dRule
    extends T etHasLabelRule(
      Avo d(So (Avo dReason.Conta nsNsfw d a)),
      T etSafetyLabelType.NsfwH ghRecall
    ) {
  overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
    new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
}
