package com.tw ter.v s b l y.rules

 mport com.tw ter.guano.commons.thr ftscala.Pol cy nV olat on
 mport com.tw ter.spam.rtf.thr ftscala.SafetyResultReason
 mport com.tw ter.ut l. mo ze
 mport com.tw ter.ut l.T  
 mport com.tw ter.v s b l y.common.act ons.Compl anceT etNot ceEventType
 mport com.tw ter.v s b l y.common.act ons.L m edEngage ntReason
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableSearch p SafeSearchW houtUser nQueryDropRule
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.features.T etSafetyLabels
 mport com.tw ter.v s b l y.models.T etSafetyLabel
 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.LoggedOutOrV e rOpt nF lter ng
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Cond  on.Or
 mport com.tw ter.v s b l y.rules.Cond  on.SearchQueryHasUser
 mport com.tw ter.v s b l y.rules.Cond  on.T etComposedAfter
 mport com.tw ter.v s b l y.rules.Cond  on.T etHasLabel
 mport com.tw ter.v s b l y.rules.Reason._
 mport com.tw ter.v s b l y.rules.State.Evaluated

object Publ c nterest {
  object Pol cyConf g {
    val LowQual yProxyLabelStart: T   = T  .fromM ll seconds(1554076800000L)
    val DefaultReason: (Reason, Opt on[L m edEngage ntReason]) =
      (OneOff, So (L m edEngage ntReason.NonCompl ant))
    val DefaultPol cy nV olat on: Pol cy nV olat on = Pol cy nV olat on.OneOff
  }

  val pol cy nV olat onToReason: Map[Pol cy nV olat on, Reason] = Map(
    Pol cy nV olat on.AbusePol cyEp sod c -> AbuseEp sod c,
    Pol cy nV olat on.AbusePol cyEp sod cEnc ageSelfharm -> AbuseEp sod cEnc ageSelfHarm,
    Pol cy nV olat on.AbusePol cyEp sod cHatefulConduct -> AbuseEp sod cHatefulConduct,
    Pol cy nV olat on.AbusePol cyGratu ousGore -> AbuseGratu ousGore,
    Pol cy nV olat on.AbusePol cyGlor f cat onofV olence -> AbuseGlor f cat onOfV olence,
    Pol cy nV olat on.AbusePol cyEnc ageMobHarass nt -> AbuseMobHarass nt,
    Pol cy nV olat on.AbusePol cyMo ntofDeathDeceasedUser -> AbuseMo ntOfDeathOrDeceasedUser,
    Pol cy nV olat on.AbusePol cyPr vate nformat on -> AbusePr vate nformat on,
    Pol cy nV olat on.AbusePol cyR ghttoPr vacy -> AbuseR ghtToPr vacy,
    Pol cy nV olat on.AbusePol cyThreattoExpose -> AbuseThreatToExpose,
    Pol cy nV olat on.AbusePol cyV olentSexualConduct -> AbuseV olentSexualConduct,
    Pol cy nV olat on.AbusePol cyV olentThreatsHatefulConduct -> AbuseV olentThreatHatefulConduct,
    Pol cy nV olat on.AbusePol cyV olentThreatorBounty -> AbuseV olentThreatOrBounty,
    Pol cy nV olat on.OneOff -> OneOff,
    Pol cy nV olat on.AbusePol cyElect on nterference -> Vot ngM s nformat on,
    Pol cy nV olat on.M s nformat onVot ng -> Vot ngM s nformat on,
    Pol cy nV olat on.HackedMater als -> HackedMater als,
    Pol cy nV olat on.Scam -> Scams,
    Pol cy nV olat on.PlatformMan pulat on -> PlatformMan pulat on,
    Pol cy nV olat on.M s nformat onC v c -> M s nfoC v c,
    Pol cy nV olat on.AbusePol cyUkra neCr s sM s nformat on -> M s nfoCr s s,
    Pol cy nV olat on.M s nformat onGener c -> M s nfoGener c,
    Pol cy nV olat on.M s nformat on d cal -> M s nfo d cal,
  )

  val reasonToPol cy nV olat on: Map[Reason, Pol cy nV olat on] = Map(
    AbuseEp sod c -> Pol cy nV olat on.AbusePol cyEp sod c,
    AbuseEp sod cEnc ageSelfHarm -> Pol cy nV olat on.AbusePol cyEp sod cEnc ageSelfharm,
    AbuseEp sod cHatefulConduct -> Pol cy nV olat on.AbusePol cyEp sod cHatefulConduct,
    AbuseGratu ousGore -> Pol cy nV olat on.AbusePol cyGratu ousGore,
    AbuseGlor f cat onOfV olence -> Pol cy nV olat on.AbusePol cyGlor f cat onofV olence,
    AbuseMobHarass nt -> Pol cy nV olat on.AbusePol cyEnc ageMobHarass nt,
    AbuseMo ntOfDeathOrDeceasedUser -> Pol cy nV olat on.AbusePol cyMo ntofDeathDeceasedUser,
    AbusePr vate nformat on -> Pol cy nV olat on.AbusePol cyPr vate nformat on,
    AbuseR ghtToPr vacy -> Pol cy nV olat on.AbusePol cyR ghttoPr vacy,
    AbuseThreatToExpose -> Pol cy nV olat on.AbusePol cyThreattoExpose,
    AbuseV olentSexualConduct -> Pol cy nV olat on.AbusePol cyV olentSexualConduct,
    AbuseV olentThreatHatefulConduct -> Pol cy nV olat on.AbusePol cyV olentThreatsHatefulConduct,
    AbuseV olentThreatOrBounty -> Pol cy nV olat on.AbusePol cyV olentThreatorBounty,
    OneOff -> Pol cy nV olat on.OneOff,
    Vot ngM s nformat on -> Pol cy nV olat on.M s nformat onVot ng,
    HackedMater als -> Pol cy nV olat on.HackedMater als,
    Scams -> Pol cy nV olat on.Scam,
    PlatformMan pulat on -> Pol cy nV olat on.PlatformMan pulat on,
    M s nfoC v c -> Pol cy nV olat on.M s nformat onC v c,
    M s nfoCr s s -> Pol cy nV olat on.AbusePol cyUkra neCr s sM s nformat on,
    M s nfoGener c -> Pol cy nV olat on.M s nformat onGener c,
    M s nfo d cal -> Pol cy nV olat on.M s nformat on d cal,
  )

  val ReasonToSafetyResultReason: Map[Reason, SafetyResultReason] = Map(
    AbuseEp sod c -> SafetyResultReason.Ep sod c,
    AbuseEp sod cEnc ageSelfHarm -> SafetyResultReason.AbuseEp sod cEnc ageSelfHarm,
    AbuseEp sod cHatefulConduct -> SafetyResultReason.AbuseEp sod cHatefulConduct,
    AbuseGratu ousGore -> SafetyResultReason.AbuseGratu ousGore,
    AbuseGlor f cat onOfV olence -> SafetyResultReason.AbuseGlor f cat onOfV olence,
    AbuseMobHarass nt -> SafetyResultReason.AbuseMobHarass nt,
    AbuseMo ntOfDeathOrDeceasedUser -> SafetyResultReason.AbuseMo ntOfDeathOrDeceasedUser,
    AbusePr vate nformat on -> SafetyResultReason.AbusePr vate nformat on,
    AbuseR ghtToPr vacy -> SafetyResultReason.AbuseR ghtToPr vacy,
    AbuseThreatToExpose -> SafetyResultReason.AbuseThreatToExpose,
    AbuseV olentSexualConduct -> SafetyResultReason.AbuseV olentSexualConduct,
    AbuseV olentThreatHatefulConduct -> SafetyResultReason.AbuseV olentThreatHatefulConduct,
    AbuseV olentThreatOrBounty -> SafetyResultReason.AbuseV olentThreatOrBounty,
    OneOff -> SafetyResultReason.OneOff,
    Vot ngM s nformat on -> SafetyResultReason.Vot ngM s nformat on,
    HackedMater als -> SafetyResultReason.HackedMater als,
    Scams -> SafetyResultReason.Scams,
    PlatformMan pulat on -> SafetyResultReason.PlatformMan pulat on,
    M s nfoC v c -> SafetyResultReason.M s nfoC v c,
    M s nfoCr s s -> SafetyResultReason.M s nfoCr s s,
    M s nfoGener c -> SafetyResultReason.M s nfoGener c,
    M s nfo d cal -> SafetyResultReason.M s nfo d cal,
     p Develop ntOnly -> SafetyResultReason.Develop ntOnlyPubl c nterest
  )

  val Reasons: Set[Reason] = ReasonToSafetyResultReason.keySet
  val SafetyResultReasons: Set[SafetyResultReason] = ReasonToSafetyResultReason.values.toSet

  val SafetyResultReasonToReason: Map[SafetyResultReason, Reason] =
    ReasonToSafetyResultReason.map(t => t._2 -> t._1)

  val El g bleT etSafetyLabelTypes: Seq[T etSafetyLabelType] = Seq(
    T etSafetyLabelType.LowQual y,
    T etSafetyLabelType.M s nfoC v c,
    T etSafetyLabelType.M s nfoGener c,
    T etSafetyLabelType.M s nfo d cal,
    T etSafetyLabelType.M s nfoCr s s,
    T etSafetyLabelType. p Develop ntOnly
  )

  pr vate val El g bleT etSafetyLabelTypesSet = El g bleT etSafetyLabelTypes.toSet

  def extractT etSafetyLabel(featureMap: Map[Feature[_], _]): Opt on[T etSafetyLabel] = {
    val t etSafetyLabels = featureMap(T etSafetyLabels)
      .as nstanceOf[Seq[T etSafetyLabel]]
      .flatMap { tsl =>
         f (Publ c nterest.El g bleT etSafetyLabelTypesSet.conta ns(tsl.labelType)) {
          So (tsl.labelType -> tsl)
        } else {
          None
        }
      }
      .toMap

    Publ c nterest.El g bleT etSafetyLabelTypes.flatMap(t etSafetyLabels.get). adOpt on
  }

  def pol cyToReason(pol cy: Pol cy nV olat on): Reason =
    pol cy nV olat onToReason.get(pol cy).getOrElse(Pol cyConf g.DefaultReason._1)

  def reasonToPol cy(reason: Reason): Pol cy nV olat on =
    reasonToPol cy nV olat on.get(reason).getOrElse(Pol cyConf g.DefaultPol cy nV olat on)
}

class Publ c nterestAct onBu lder[T <: Act on]() extends Act onBu lder[T] {
  def act onType: Class[_] = classOf[ nterst  alL m edEngage nts]

  overr de val act onSever y = 11
  def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
    val (reason, l m edEngage ntReason) =
      Publ c nterest.extractT etSafetyLabel(featureMap).map { t etSafetyLabel =>
        (t etSafetyLabel.labelType, t etSafetyLabel.s ce)
      } match {
        case So ((T etSafetyLabelType.LowQual y, s ce)) =>
          s ce match {
            case So (s ce) =>
              SafetyResultReason.valueOf(s ce.na ) match {
                case So (matc dReason)
                     f Publ c nterest.SafetyResultReasonToReason.conta ns(matc dReason) =>
                  (
                    Publ c nterest.SafetyResultReasonToReason(matc dReason),
                    So (L m edEngage ntReason.NonCompl ant))
                case _ => Publ c nterest.Pol cyConf g.DefaultReason
              }
            case _ => Publ c nterest.Pol cyConf g.DefaultReason
          }


        case So ((T etSafetyLabelType.M s nfoC v c, s ce)) =>
          (Reason.M s nfoC v c, L m edEngage ntReason.fromStr ng(s ce.map(_.na )))

        case So ((T etSafetyLabelType.M s nfoCr s s, s ce)) =>
          (Reason.M s nfoCr s s, L m edEngage ntReason.fromStr ng(s ce.map(_.na )))

        case So ((T etSafetyLabelType.M s nfoGener c, s ce)) =>
          (Reason.M s nfoGener c, L m edEngage ntReason.fromStr ng(s ce.map(_.na )))

        case So ((T etSafetyLabelType.M s nfo d cal, s ce)) =>
          (Reason.M s nfo d cal, L m edEngage ntReason.fromStr ng(s ce.map(_.na )))

        case So ((T etSafetyLabelType. p Develop ntOnly, _)) =>
          (Reason. p Develop ntOnly, So (L m edEngage ntReason.NonCompl ant))

        case _ =>
          Publ c nterest.Pol cyConf g.DefaultReason
      }

    RuleResult( nterst  alL m edEngage nts(reason, l m edEngage ntReason), Evaluated)
  }
}

class Publ c nterestCompl anceT etNot ceAct onBu lder
    extends Act onBu lder[Compl anceT etNot cePreEnr ch nt] {

  overr de def act onType: Class[_] = classOf[Compl anceT etNot cePreEnr ch nt]

  overr de val act onSever y = 2
  def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
    val reason =
      Publ c nterest.extractT etSafetyLabel(featureMap).map { t etSafetyLabel =>
        (t etSafetyLabel.labelType, t etSafetyLabel.s ce)
      } match {
        case So ((T etSafetyLabelType.LowQual y, s ce)) =>
          s ce match {
            case So (s ce) =>
              SafetyResultReason.valueOf(s ce.na ) match {
                case So (matc dReason)
                     f Publ c nterest.SafetyResultReasonToReason.conta ns(matc dReason) =>
                  Publ c nterest.SafetyResultReasonToReason(matc dReason)
                case _ => Publ c nterest.Pol cyConf g.DefaultReason._1
              }
            case _ => Publ c nterest.Pol cyConf g.DefaultReason._1
          }


        case So ((T etSafetyLabelType.M s nfoC v c, _)) =>
          Reason.M s nfoC v c

        case So ((T etSafetyLabelType.M s nfoCr s s, _)) =>
          Reason.M s nfoCr s s

        case So ((T etSafetyLabelType.M s nfoGener c, _)) =>
          Reason.M s nfoGener c

        case So ((T etSafetyLabelType.M s nfo d cal, _)) =>
          Reason.M s nfo d cal

        case So ((T etSafetyLabelType. p Develop ntOnly, _)) =>
          Reason. p Develop ntOnly

        case _ =>
          Publ c nterest.Pol cyConf g.DefaultReason._1
      }

    RuleResult(
      Compl anceT etNot cePreEnr ch nt(reason, Compl anceT etNot ceEventType.Publ c nterest),
      Evaluated)
  }
}

class Publ c nterestDropAct onBu lder extends Act onBu lder[Drop] {

  overr de def act onType: Class[_] = classOf[Drop]

  overr de val act onSever y = 16
  pr vate def toRuleResult: Reason => RuleResult =  mo ze { r => RuleResult(Drop(r), Evaluated) }

  def bu ld(evaluat onContext: Evaluat onContext, featureMap: Map[Feature[_], _]): RuleResult = {
    val reason = Publ c nterest.extractT etSafetyLabel(featureMap).map(_.labelType) match {
      case So (T etSafetyLabelType.LowQual y) =>
        Reason.OneOff

      case So (T etSafetyLabelType.M s nfoC v c) =>
        Reason.M s nfoC v c

      case So (T etSafetyLabelType.M s nfoCr s s) =>
        Reason.M s nfoCr s s

      case So (T etSafetyLabelType.M s nfoGener c) =>
        Reason.M s nfoGener c

      case So (T etSafetyLabelType.M s nfo d cal) =>
        Reason.M s nfo d cal

      case _ =>
        Reason.OneOff
    }

    toRuleResult(reason)
  }
}

object Publ c nterestRules {

  object AbusePol cyEp sod cT etLabel nterst  alRule
      extends Rule(
        new Publ c nterestAct onBu lder(),
        And(
          T etComposedAfter(Publ c nterest.Pol cyConf g.LowQual yProxyLabelStart),
          Or(
            Publ c nterest.El g bleT etSafetyLabelTypes.map(T etHasLabel(_)): _*
          )
        )
      )

  object AbusePol cyEp sod cT etLabelCompl anceT etNot ceRule
      extends Rule(
        new Publ c nterestCompl anceT etNot ceAct onBu lder(),
        And(
          T etComposedAfter(Publ c nterest.Pol cyConf g.LowQual yProxyLabelStart),
          Or(
            Publ c nterest.El g bleT etSafetyLabelTypes.map(T etHasLabel(_)): _*
          )
        )
      )

  object AbusePol cyEp sod cT etLabelDropRule
      extends Rule(
        new Publ c nterestDropAct onBu lder(),
        And(
          T etComposedAfter(Publ c nterest.Pol cyConf g.LowQual yProxyLabelStart),
          Or(
            Publ c nterest.El g bleT etSafetyLabelTypes.map(T etHasLabel(_)): _*
          )
        )
      )

  object Search p SafeSearchW houtUser nQueryDropRule
      extends Rule(
        new Publ c nterestDropAct onBu lder(),
        And(
          T etComposedAfter(Publ c nterest.Pol cyConf g.LowQual yProxyLabelStart),
          Or(
            Publ c nterest.El g bleT etSafetyLabelTypes.map(T etHasLabel(_)): _*
          ),
          LoggedOutOrV e rOpt nF lter ng,
          Not(SearchQueryHasUser)
        )
      ) {
    overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
      EnableSearch p SafeSearchW houtUser nQueryDropRule)
  }
}
