package com.tw ter.v s b l y.rules

 mport com.tw ter.spam.rtf.thr ftscala.SafetyResultReason
 mport com.tw ter.ut l. mo ze
 mport com.tw ter.v s b l y.common.act ons.AppealableReason
 mport com.tw ter.v s b l y.common.act ons.Avo dReason.M ghtNotBeSu ableForAds
 mport com.tw ter.v s b l y.common.act ons.L m edEngage ntReason
 mport com.tw ter.v s b l y.common.act ons.Soft ntervent onD splayType
 mport com.tw ter.v s b l y.common.act ons.Soft ntervent onReason
 mport com.tw ter.v s b l y.common.act ons.L m edAct onsPol cy
 mport com.tw ter.v s b l y.common.act ons.L m edAct on
 mport com.tw ter.v s b l y.common.act ons.converter.scala.L m edAct onTypeConverter
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.FosnrFallbackDropRulesEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.FosnrRulesEnabledParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableFosnrRuleParam
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.features.T etSafetyLabels
 mport com.tw ter.v s b l y.models.T etSafetyLabel
 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.v s b l y.models.V olat onLevel
 mport com.tw ter.v s b l y.rules.ComposableAct ons.ComposableAct onsW h nterst  alL m edEngage nts
 mport com.tw ter.v s b l y.rules.ComposableAct ons.ComposableAct onsW hSoft ntervent on
 mport com.tw ter.v s b l y.rules.ComposableAct ons.ComposableAct onsW hAppealable
 mport com.tw ter.v s b l y.rules.ComposableAct ons.ComposableAct onsW h nterst  al
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.NonAuthorV e r
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Cond  on.V e rDoesNotFollowAuthorOfFosnrV olat ngT et
 mport com.tw ter.v s b l y.rules.Cond  on.V e rFollowsAuthorOfFosnrV olat ngT et
 mport com.tw ter.v s b l y.rules.FreedomOfSpeechNotReach.DefaultV olat onLevel
 mport com.tw ter.v s b l y.rules.Reason._
 mport com.tw ter.v s b l y.rules.State.Evaluated

object FreedomOfSpeechNotReach {

  val DefaultV olat onLevel = V olat onLevel.Level1

  def reasonToSafetyResultReason(reason: Reason): SafetyResultReason =
    reason match {
      case HatefulConduct => SafetyResultReason.FosnrHatefulConduct
      case Abus veBehav or => SafetyResultReason.FosnrAbus veBehav or
      case _ => SafetyResultReason.FosnrUnspec f ed
    }

  def reasonToSafetyResultReason(reason: AppealableReason): SafetyResultReason =
    reason match {
      case AppealableReason.HatefulConduct(_) => SafetyResultReason.FosnrHatefulConduct
      case AppealableReason.Abus veBehav or(_) => SafetyResultReason.FosnrAbus veBehav or
      case _ => SafetyResultReason.FosnrUnspec f ed
    }

  val El g bleT etSafetyLabelTypes: Seq[T etSafetyLabelType] =
    Seq(V olat onLevel.Level4, V olat onLevel.Level3, V olat onLevel.Level2, V olat onLevel.Level1)
      .map {
        V olat onLevel.v olat onLevelToSafetyLabels.get(_).getOrElse(Set()).toSeq
      }.reduceLeft {
        _ ++ _
      }

  pr vate val El g bleT etSafetyLabelTypesSet = El g bleT etSafetyLabelTypes.toSet

  def extractT etSafetyLabel(featureMap: Map[Feature[_], _]): Opt on[T etSafetyLabel] = {
    val t etSafetyLabels = featureMap(T etSafetyLabels)
      .as nstanceOf[Seq[T etSafetyLabel]]
      .flatMap { tsl =>
         f (FreedomOfSpeechNotReach.El g bleT etSafetyLabelTypesSet.conta ns(tsl.labelType)) {
          So (tsl.labelType -> tsl)
        } else {
          None
        }
      }
      .toMap

    FreedomOfSpeechNotReach.El g bleT etSafetyLabelTypes.flatMap(t etSafetyLabels.get). adOpt on
  }

  def el g bleT etSafetyLabelTypesToAppealableReason(
    labelType: T etSafetyLabelType,
    v olat onLevel: V olat onLevel
  ): AppealableReason = {
    labelType match {
      case T etSafetyLabelType.FosnrHatefulConduct =>
        AppealableReason.HatefulConduct(v olat onLevel.level)
      case T etSafetyLabelType.FosnrHatefulConductLowSever ySlur =>
        AppealableReason.HatefulConduct(v olat onLevel.level)
      case _ =>
        AppealableReason.Unspec f ed(v olat onLevel.level)
    }
  }

  def l m edAct onConverter(
    l m edAct onStr ngs: Opt on[Seq[Str ng]]
  ): Opt on[L m edAct onsPol cy] = {
    val l m edAct ons = l m edAct onStr ngs.map { l m edAct onStr ng =>
      l m edAct onStr ng
        .map(act on => L m edAct onTypeConverter.fromStr ng(act on)).map { act on =>
          act on match {
            case So (a) => So (L m edAct on(a, None))
            case _ => None
          }
        }.flatten
    }
    l m edAct ons.map(act ons => L m edAct onsPol cy(act ons))
  }
}

object FreedomOfSpeechNotReachReason {
  def unapply(soft ntervent on: Soft ntervent on): Opt on[AppealableReason] = {
    soft ntervent on.reason match {
      case Soft ntervent onReason.FosnrReason(appealableReason) => So (appealableReason)
      case _ => None
    }
  }

  def unapply(
     nterst  alL m edEngage nts:  nterst  alL m edEngage nts
  ): Opt on[AppealableReason] = {
     nterst  alL m edEngage nts.l m edEngage ntReason match {
      case So (L m edEngage ntReason.FosnrReason(appealableReason)) => So (appealableReason)
      case _ => None
    }
  }

  def unapply(
     nterst  al:  nterst  al
  ): Opt on[AppealableReason] = {
     nterst  al.reason match {
      case Reason.FosnrReason(appealableReason) => So (appealableReason)
      case _ => None
    }
  }

  def unapply(
    appealable: Appealable
  ): Opt on[AppealableReason] = {
    Reason.toAppealableReason(appealable.reason, appealable.v olat onLevel)
  }

  def unapply(
    act on: Act on
  ): Opt on[AppealableReason] = {
    act on match {
      case a: Soft ntervent on =>
        a match {
          case FreedomOfSpeechNotReachReason(r) => So (r)
          case _ => None
        }
      case a:  nterst  alL m edEngage nts =>
        a match {
          case FreedomOfSpeechNotReachReason(r) => So (r)
          case _ => None
        }
      case a:  nterst  al =>
        a match {
          case FreedomOfSpeechNotReachReason(r) => So (r)
          case _ => None
        }
      case a: Appealable =>
        a match {
          case FreedomOfSpeechNotReachReason(r) => So (r)
          case _ => None
        }
      case ComposableAct onsW hSoft ntervent on(FreedomOfSpeechNotReachReason(appealableReason)) =>
        So (appealableReason)
      case ComposableAct onsW h nterst  alL m edEngage nts(
            FreedomOfSpeechNotReachReason(appealableReason)) =>
        So (appealableReason)
      case ComposableAct onsW h nterst  al(FreedomOfSpeechNotReachReason(appealableReason)) =>
        So (appealableReason)
      case ComposableAct onsW hAppealable(FreedomOfSpeechNotReachReason(appealableReason)) =>
        So (appealableReason)
      case _ => None
    }
  }
}

object FreedomOfSpeechNotReachAct ons {

  tra  FreedomOfSpeechNotReachAct onBu lder[T <: Act on] extends Act onBu lder[T] {
    def w hV olat onLevel(v olat onLevel: V olat onLevel): FreedomOfSpeechNotReachAct onBu lder[T]
  }

  case class DropAct on(v olat onLevel: V olat onLevel = DefaultV olat onLevel)
      extends FreedomOfSpeechNotReachAct onBu lder[Drop] {

    overr de def act onType: Class[_] = classOf[Drop]

    overr de val act onSever y = 16
    pr vate def toRuleResult: Reason => RuleResult =  mo ze { r => RuleResult(Drop(r), Evaluated) }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(Reason.fromAppealableReason(appealableReason))
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class AppealableAct on(v olat onLevel: V olat onLevel = DefaultV olat onLevel)
      extends FreedomOfSpeechNotReachAct onBu lder[T et nterst  al] {

    overr de def act onType: Class[_] = classOf[Appealable]

    overr de val act onSever y = 17
    pr vate def toRuleResult: Reason => RuleResult =  mo ze { r =>
      RuleResult(
        T et nterst  al(
           nterst  al = None,
          soft ntervent on = None,
          l m edEngage nts = None,
          downrank = None,
          avo d = So (Avo d(None)),
           d a nterst  al = None,
          t etV s b l yNudge = None,
          abus veQual y = None,
          appealable = So (Appealable(r, v olat onLevel = v olat onLevel))
        ),
        Evaluated
      )
    }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(Reason.fromAppealableReason(appealableReason))
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class AppealableAvo dL m edEngage ntsAct on(
    v olat onLevel: V olat onLevel = DefaultV olat onLevel,
    l m edAct onStr ngs: Opt on[Seq[Str ng]])
      extends FreedomOfSpeechNotReachAct onBu lder[Appealable] {

    overr de def act onType: Class[_] = classOf[AppealableAvo dL m edEngage ntsAct on]

    overr de val act onSever y = 17
    pr vate def toRuleResult: Reason => RuleResult =  mo ze { r =>
      RuleResult(
        T et nterst  al(
           nterst  al = None,
          soft ntervent on = None,
          l m edEngage nts = So (
            L m edEngage nts(
              toL m edEngage ntReason(
                Reason
                  .toAppealableReason(r, v olat onLevel)
                  .getOrElse(AppealableReason.Unspec f ed(v olat onLevel.level))),
              FreedomOfSpeechNotReach.l m edAct onConverter(l m edAct onStr ngs)
            )),
          downrank = None,
          avo d = So (Avo d(None)),
           d a nterst  al = None,
          t etV s b l yNudge = None,
          abus veQual y = None,
          appealable = So (Appealable(r, v olat onLevel = v olat onLevel))
        ),
        Evaluated
      )
    }

    def bu ld(
      evaluat onContext: Evaluat onContext,
      featureMap: Map[Feature[_], _]
    ): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(Reason.fromAppealableReason(appealableReason))
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class Avo dAct on(v olat onLevel: V olat onLevel = DefaultV olat onLevel)
      extends FreedomOfSpeechNotReachAct onBu lder[Avo d] {

    overr de def act onType: Class[_] = classOf[Avo d]

    overr de val act onSever y = 1
    pr vate def toRuleResult: Reason => RuleResult =  mo ze { r =>
      RuleResult(Avo d(None), Evaluated)
    }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(Reason.fromAppealableReason(appealableReason))
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class L m edEngage ntsAct on(v olat onLevel: V olat onLevel = DefaultV olat onLevel)
      extends FreedomOfSpeechNotReachAct onBu lder[L m edEngage nts] {

    overr de def act onType: Class[_] = classOf[L m edEngage nts]

    overr de val act onSever y = 6
    pr vate def toRuleResult: Reason => RuleResult =  mo ze { r =>
      RuleResult(L m edEngage nts(L m edEngage ntReason.NonCompl ant, None), Evaluated)
    }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(Reason.fromAppealableReason(appealableReason))
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class  nterst  alL m edEngage ntsAct on(
    v olat onLevel: V olat onLevel = DefaultV olat onLevel)
      extends FreedomOfSpeechNotReachAct onBu lder[ nterst  alL m edEngage nts] {

    overr de def act onType: Class[_] = classOf[ nterst  alL m edEngage nts]

    overr de val act onSever y = 11
    pr vate def toRuleResult: Reason => RuleResult =  mo ze { r =>
      RuleResult( nterst  alL m edEngage nts(r, None), Evaluated)
    }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(Reason.fromAppealableReason(appealableReason))
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class  nterst  alL m edEngage ntsAvo dAct on(
    v olat onLevel: V olat onLevel = DefaultV olat onLevel,
    l m edAct onStr ngs: Opt on[Seq[Str ng]])
      extends FreedomOfSpeechNotReachAct onBu lder[T et nterst  al] {

    overr de def act onType: Class[_] = classOf[ nterst  alL m edEngage ntsAvo dAct on]

    overr de val act onSever y = 14
    pr vate def toRuleResult: AppealableReason => RuleResult =  mo ze { r =>
      RuleResult(
        T et nterst  al(
           nterst  al = So (
             nterst  al(
              reason = FosnrReason(r),
              local zed ssage = None,
            )),
          soft ntervent on = None,
          l m edEngage nts = So (
            L m edEngage nts(
              reason = toL m edEngage ntReason(r),
              pol cy = FreedomOfSpeechNotReach.l m edAct onConverter(l m edAct onStr ngs))),
          downrank = None,
          avo d = So (Avo d(None)),
           d a nterst  al = None,
          t etV s b l yNudge = None
        ),
        Evaluated
      )
    }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              labelType = label,
              v olat onLevel = v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(appealableReason)
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class Soft ntervent onAvo dAct on(v olat onLevel: V olat onLevel = DefaultV olat onLevel)
      extends FreedomOfSpeechNotReachAct onBu lder[T et nterst  al] {

    overr de def act onType: Class[_] = classOf[Soft ntervent onAvo dAct on]

    overr de val act onSever y = 8
    pr vate def toRuleResult: AppealableReason => RuleResult =  mo ze { r =>
      RuleResult(
        T et nterst  al(
           nterst  al = None,
          soft ntervent on = So (
            Soft ntervent on(
              reason = toSoft ntervent onReason(r),
              engage ntNudge = false,
              suppressAutoplay = true,
              warn ng = None,
              deta lsUrl = None,
              d splayType = So (Soft ntervent onD splayType.Fosnr)
            )),
          l m edEngage nts = None,
          downrank = None,
          avo d = So (Avo d(None)),
           d a nterst  al = None,
          t etV s b l yNudge = None,
          abus veQual y = None
        ),
        Evaluated
      )
    }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(appealableReason)
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class Soft ntervent onAvo dL m edEngage ntsAct on(
    v olat onLevel: V olat onLevel = DefaultV olat onLevel,
    l m edAct onStr ngs: Opt on[Seq[Str ng]])
      extends FreedomOfSpeechNotReachAct onBu lder[T et nterst  al] {

    overr de def act onType: Class[_] = classOf[Soft ntervent onAvo dL m edEngage ntsAct on]

    overr de val act onSever y = 13
    pr vate def toRuleResult: AppealableReason => RuleResult =  mo ze { r =>
      RuleResult(
        T et nterst  al(
           nterst  al = None,
          soft ntervent on = So (
            Soft ntervent on(
              reason = toSoft ntervent onReason(r),
              engage ntNudge = false,
              suppressAutoplay = true,
              warn ng = None,
              deta lsUrl = None,
              d splayType = So (Soft ntervent onD splayType.Fosnr)
            )),
          l m edEngage nts = So (
            L m edEngage nts(
              toL m edEngage ntReason(r),
              FreedomOfSpeechNotReach.l m edAct onConverter(l m edAct onStr ngs))),
          downrank = None,
          avo d = So (Avo d(None)),
           d a nterst  al = None,
          t etV s b l yNudge = None,
          abus veQual y = None
        ),
        Evaluated
      )
    }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(appealableReason)
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }

  case class Soft ntervent onAvo dAbus veQual yReplyAct on(
    v olat onLevel: V olat onLevel = DefaultV olat onLevel)
      extends FreedomOfSpeechNotReachAct onBu lder[T et nterst  al] {

    overr de def act onType: Class[_] = classOf[Soft ntervent onAvo dAbus veQual yReplyAct on]

    overr de val act onSever y = 13
    pr vate def toRuleResult: AppealableReason => RuleResult =  mo ze { r =>
      RuleResult(
        T et nterst  al(
           nterst  al = None,
          soft ntervent on = So (
            Soft ntervent on(
              reason = toSoft ntervent onReason(r),
              engage ntNudge = false,
              suppressAutoplay = true,
              warn ng = None,
              deta lsUrl = None,
              d splayType = So (Soft ntervent onD splayType.Fosnr)
            )),
          l m edEngage nts = None,
          downrank = None,
          avo d = So (Avo d(None)),
           d a nterst  al = None,
          t etV s b l yNudge = None,
          abus veQual y = So (Conversat onSect onAbus veQual y)
        ),
        Evaluated
      )
    }

    def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
      val appealableReason =
        FreedomOfSpeechNotReach.extractT etSafetyLabel(featureMap).map(_.labelType) match {
          case So (label) =>
            FreedomOfSpeechNotReach.el g bleT etSafetyLabelTypesToAppealableReason(
              label,
              v olat onLevel)
          case _ =>
            AppealableReason.Unspec f ed(v olat onLevel.level)
        }

      toRuleResult(appealableReason)
    }

    overr de def w hV olat onLevel(v olat onLevel: V olat onLevel) = {
      copy(v olat onLevel = v olat onLevel)
    }
  }
}

object FreedomOfSpeechNotReachRules {

  abstract class OnlyW nAuthorV e rRule(
    act onBu lder: Act onBu lder[_ <: Act on],
    cond  on: Cond  on)
      extends Rule(act onBu lder, And(Not(NonAuthorV e r), cond  on))

  abstract class OnlyW nNonAuthorV e rRule(
    act onBu lder: Act onBu lder[_ <: Act on],
    cond  on: Cond  on)
      extends Rule(act onBu lder, And(NonAuthorV e r, cond  on))

  case class V e r sAuthorAndT etHasV olat onOfLevel(
    v olat onLevel: V olat onLevel,
    overr de val act onBu lder: Act onBu lder[_ <: Act on])
      extends OnlyW nAuthorV e rRule(
        act onBu lder,
        Cond  on.T etHasV olat onOfLevel(v olat onLevel)
      ) {
    overr de lazy val na : Str ng = s"V e r sAuthorAndT etHasV olat onOf$v olat onLevel"

    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableFosnrRuleParam, FosnrRulesEnabledParam)
  }

  case class V e r sFollo rAndT etHasV olat onOfLevel(
    v olat onLevel: V olat onLevel,
    overr de val act onBu lder: Act onBu lder[_ <: Act on])
      extends OnlyW nNonAuthorV e rRule(
        act onBu lder,
        And(
          Cond  on.T etHasV olat onOfLevel(v olat onLevel),
          V e rFollowsAuthorOfFosnrV olat ngT et)
      ) {
    overr de lazy val na : Str ng = s"V e r sFollo rAndT etHasV olat onOf$v olat onLevel"

    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableFosnrRuleParam, FosnrRulesEnabledParam)

    overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
      new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
  }

  case class V e r sNonFollo rNonAuthorAndT etHasV olat onOfLevel(
    v olat onLevel: V olat onLevel,
    overr de val act onBu lder: Act onBu lder[_ <: Act on])
      extends OnlyW nNonAuthorV e rRule(
        act onBu lder,
        And(
          Cond  on.T etHasV olat onOfLevel(v olat onLevel),
          V e rDoesNotFollowAuthorOfFosnrV olat ngT et)
      ) {
    overr de lazy val na : Str ng =
      s"V e r sNonFollo rNonAuthorAndT etHasV olat onOf$v olat onLevel"

    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableFosnrRuleParam, FosnrRulesEnabledParam)

    overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
      new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
  }

  case class V e r sNonAuthorAndT etHasV olat onOfLevel(
    v olat onLevel: V olat onLevel,
    overr de val act onBu lder: Act onBu lder[_ <: Act on])
      extends OnlyW nNonAuthorV e rRule(
        act onBu lder,
        Cond  on.T etHasV olat onOfLevel(v olat onLevel)
      ) {
    overr de lazy val na : Str ng =
      s"V e r sNonAuthorAndT etHasV olat onOf$v olat onLevel"

    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableFosnrRuleParam, FosnrRulesEnabledParam)

    overr de val fallbackAct onBu lder: Opt on[Act onBu lder[_ <: Act on]] = So (
      new ConstantAct onBu lder(Avo d(So (M ghtNotBeSu ableForAds))))
  }

  case object T etHasV olat onOfAnyLevelFallbackDropRule
      extends RuleW hConstantAct on(
        Drop(reason = NotSupportedOnDev ce),
        Cond  on.T etHasV olat onOfAnyLevel
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableFosnrRuleParam, FosnrFallbackDropRulesEnabledParam)
  }
}
