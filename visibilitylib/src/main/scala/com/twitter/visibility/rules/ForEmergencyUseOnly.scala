package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.common.act ons.Compl anceT etNot ceEventType
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableSearch p SafeSearchW houtUser nQueryDropRule
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.features.T etSafetyLabels
 mport com.tw ter.v s b l y.models.LabelS ce.Str ngS ce
 mport com.tw ter.v s b l y.models.LabelS ce.parseStr ngS ce
 mport com.tw ter.v s b l y.models.T etSafetyLabel
 mport com.tw ter.v s b l y.models.T etSafetyLabelType
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.LoggedOutOrV e rOpt nF lter ng
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Cond  on.SearchQueryHasUser
 mport com.tw ter.v s b l y.rules.Cond  on.T etHasLabel
 mport com.tw ter.v s b l y.rules.Reason.Unspec f ed

object E rgencyDynam c nterst  alAct onBu lder
    extends Act onBu lder[E rgencyDynam c nterst  al] {

  def act onType: Class[_] = classOf[E rgencyDynam c nterst  al]

  overr de val act onSever y = 11
  overr de def bu ld(
    evaluat onContext: Evaluat onContext,
    featureMap: Map[Feature[_], _]
  ): RuleResult = {
    val label = featureMap(T etSafetyLabels)
      .as nstanceOf[Seq[T etSafetyLabel]]
      .f nd(slv => slv.labelType == T etSafetyLabelType.ForE rgencyUseOnly)

    label.flatMap(_.s ce) match {
      case So (Str ngS ce(na )) =>
        val (copy, l nkOpt) = parseStr ngS ce(na )
        RuleResult(E rgencyDynam c nterst  al(copy, l nkOpt), State.Evaluated)

      case _ =>
        Rule.EvaluatedRuleResult
    }
  }
}

object E rgencyDynam cCompl anceT etNot ceAct onBu lder
    extends Act onBu lder[Compl anceT etNot cePreEnr ch nt] {

  def act onType: Class[_] = classOf[Compl anceT etNot cePreEnr ch nt]

  overr de val act onSever y = 2
  overr de def bu ld(
    evaluat onContext: Evaluat onContext,
    featureMap: Map[Feature[_], _]
  ): RuleResult = {
    val label = featureMap(T etSafetyLabels)
      .as nstanceOf[Seq[T etSafetyLabel]]
      .f nd(slv => slv.labelType == T etSafetyLabelType.ForE rgencyUseOnly)

    label.flatMap(_.s ce) match {
      case So (Str ngS ce(na )) =>
        val (copy, l nkOpt) = parseStr ngS ce(na )
        RuleResult(
          Compl anceT etNot cePreEnr ch nt(
            reason = Unspec f ed,
            compl anceT etNot ceEventType = Compl anceT etNot ceEventType.Publ c nterest,
            deta ls = So (copy),
            extendedDeta lsUrl = l nkOpt
          ),
          State.Evaluated
        )

      case _ =>
        Rule.EvaluatedRuleResult
    }
  }
}

object E rgencyDynam c nterst  alRule
    extends Rule(
      E rgencyDynam c nterst  alAct onBu lder,
      T etHasLabel(T etSafetyLabelType.ForE rgencyUseOnly)
    )

object E rgencyDropRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      T etHasLabel(T etSafetyLabelType.ForE rgencyUseOnly)
    )

object SearchEd SafeSearchW houtUser nQueryDropRule
    extends RuleW hConstantAct on(
      Drop(Unspec f ed),
      And(
        T etHasLabel(T etSafetyLabelType.ForE rgencyUseOnly),
        LoggedOutOrV e rOpt nF lter ng,
        Not(SearchQueryHasUser)
      )
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableSearch p SafeSearchW houtUser nQueryDropRule)
}
