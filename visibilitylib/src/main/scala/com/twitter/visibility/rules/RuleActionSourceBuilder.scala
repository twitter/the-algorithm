package com.tw ter.v s b l y.rules

 mport com.tw ter.esc rb rd.thr ftscala.T etEnt yAnnotat on
 mport com.tw ter.g zmoduck.thr ftscala.Label
 mport com.tw ter.spam.rtf.thr ftscala.BotMakerAct on
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLabelS ce
 mport com.tw ter.spam.rtf.thr ftscala.Semant cCoreAct on
 mport com.tw ter.v s b l y.common.act ons.Esc rb rdAnnotat on
 mport com.tw ter.v s b l y.common.act ons.Soft ntervent onReason
 mport com.tw ter.v s b l y.conf gap .conf gs.Dec derKey
 mport com.tw ter.v s b l y.features.AuthorUserLabels
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.features.T etSafetyLabels
 mport com.tw ter.v s b l y.logg ng.thr ftscala.Act onS ce
 mport com.tw ter.v s b l y.models.LabelS ce._
 mport com.tw ter.v s b l y.models.T etSafetyLabel
 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.v s b l y.models.UserLabel
 mport com.tw ter.v s b l y.models.UserLabelValue

sealed tra  RuleAct onS ceBu lder {
  def bu ld(resolvedFeatureMap: Map[Feature[_], Any], verd ct: Act on): Opt on[Act onS ce]

}

object RuleAct onS ceBu lder {

  case class T etSafetyLabelS ceBu lder(t etSafetyLabelType: T etSafetyLabelType)
      extends RuleAct onS ceBu lder {
    overr de def bu ld(
      resolvedFeatureMap: Map[Feature[_], Any],
      verd ct: Act on
    ): Opt on[Act onS ce] = {
      resolvedFeatureMap
        .getOrElse(T etSafetyLabels, Seq.empty[T etSafetyLabel])
        .as nstanceOf[Seq[T etSafetyLabel]]
        .f nd(_.labelType == t etSafetyLabelType)
        .flatMap(_.safetyLabelS ce)
        .map(Act onS ce.SafetyLabelS ce(_))
    }
  }

  case class UserSafetyLabelS ceBu lder(userLabel: UserLabelValue)
      extends RuleAct onS ceBu lder {
    overr de def bu ld(
      resolvedFeatureMap: Map[Feature[_], Any],
      verd ct: Act on
    ): Opt on[Act onS ce] = {
      resolvedFeatureMap
        .getOrElse(AuthorUserLabels, Seq.empty[Label])
        .as nstanceOf[Seq[Label]]
        .map(UserLabel.fromThr ft)
        .f nd(_.labelValue == userLabel)
        .flatMap(_.s ce)
        .collect {
          case BotMakerRule(rule d) =>
            Act onS ce.SafetyLabelS ce(SafetyLabelS ce.BotMakerAct on(BotMakerAct on(rule d)))
        }
    }
  }

  case class Semant cCoreAct onS ceBu lder() extends RuleAct onS ceBu lder {
    overr de def bu ld(
      resolvedFeatureMap: Map[Feature[_], Any],
      verd ct: Act on
    ): Opt on[Act onS ce] = {
      verd ct match {
        case soft ntervent on: Soft ntervent on =>
          getSemant cCoreAct onS ceOpt on(soft ntervent on)
        case t et nterst  al: T et nterst  al =>
          t et nterst  al.soft ntervent on.flatMap(getSemant cCoreAct onS ceOpt on)
        case _ => None
      }
    }

    def getSemant cCoreAct onS ceOpt on(
      soft ntervent on: Soft ntervent on
    ): Opt on[Act onS ce] = {
      val s Reason = soft ntervent on.reason
        .as nstanceOf[Soft ntervent onReason.Esc rb rdAnnotat ons]
      val f rstAnnotat on: Opt on[Esc rb rdAnnotat on] =
        s Reason.esc rb rdAnnotat ons. adOpt on

      f rstAnnotat on.map { annotat on =>
        Act onS ce.SafetyLabelS ce(
          SafetyLabelS ce.Semant cCoreAct on(Semant cCoreAct on(
            T etEnt yAnnotat on(annotat on.group d, annotat on.doma n d, annotat on.ent y d))))
      }
    }
  }
}

tra  DoesLogVerd ct {}

tra  DoesLogVerd ctDec dered extends DoesLogVerd ct {
  def verd ctLogDec derKey: Dec derKey.Value
}
