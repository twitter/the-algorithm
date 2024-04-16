package com.tw ter.product_m xer.core.p pel ne.cand date

 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseQueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r._
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf gCompan on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam

sealed tra  BaseCand dateP pel neConf g[
  -Query <: P pel neQuery,
  Cand dateS ceQuery,
  Cand dateS ceResult,
  Result <: Un versalNoun[Any]]
    extends P pel neConf g {

  val  dent f er: Cand dateP pel ne dent f er

  /**
   * A cand date p pel ne can fetch query-level features for use w h n t  cand date s ce.  's
   * generally recom nded to set a hydrator  n t  parent recos or m xer p pel ne  f mult ple
   * cand date p pel nes share t  sa  feature but  f a spec f c query feature hydrator  s used
   * by one p pel ne and   don't want to block t  ot rs,   could expl c ly set    re.
   *  f a feature  s hydrated both  n t  parent p pel ne or  re, t  one takes pr or y.
   */
  def queryFeatureHydrat on: Seq[BaseQueryFeatureHydrator[Query, _]] = Seq.empty

  /**
   * For query-level features that are dependent on query-level features from [[queryFeatureHydrat on]]
   */
  def queryFeatureHydrat onPhase2: Seq[BaseQueryFeatureHydrator[Query, _]] = Seq.empty

  /**
   * W n t se Params are def ned, t y w ll automat cally be added as Gates  n t  p pel ne
   * by t  Cand dateP pel neBu lder
   *
   * T  enabled dec der param can to be used to qu ckly d sable a Cand date P pel ne v a Dec der
   */
  val enabledDec derParam: Opt on[Dec derParam[Boolean]] = None

  /**
   * T  supported cl ent feature sw ch param can be used w h a Feature Sw ch to control t 
   * rollout of a new Cand date P pel ne from dogfood to exper  nt to product on
   */
  val supportedCl entParam: Opt on[FSParam[Boolean]] = None

  /** [[Gate]]s that are appl ed sequent ally, t  p pel ne w ll only run  f all t  Gates are open */
  def gates: Seq[BaseGate[Query]] = Seq.empty

  /**
   * A pa r of transforms to adapt t  underly ng cand date s ce to t  p pel ne's query and result types
   * Complex use cases such as those that need access to features should construct t  r own transfor r, but
   * for s mple use cases,   can pass  n an anonymous funct on.
   * @example
   * {{{ overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[Query, Cand dateS ceQuery] = { query =>
   *   query.toExampleThr ft
   *  }
   * }}}
   */
  def queryTransfor r: BaseCand dateP pel neQueryTransfor r[
    Query,
    Cand dateS ceQuery
  ]

  /** S ce for Cand dates for t  P pel ne */
  def cand dateS ce: BaseCand dateS ce[Cand dateS ceQuery, Cand dateS ceResult]

  /**
   * [[Cand dateFeatureTransfor r]] allow   to def ne [[com.tw ter.product_m xer.core.feature.Feature]] extract on log c from y  [[Cand dateS ce]] results.
   *  f y  cand date s ces return [[com.tw ter.product_m xer.core.feature.Feature]]s alongs de t  cand date that m ght be useful later on,
   * add transfor rs for construct ng feature maps.
   *
   * @note  f mult ple transfor rs extract t  sa  feature, t  last one takes pr or y and  s kept.
   */
  def featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[Cand dateS ceResult]
  ] = Seq.empty

  /**
   * a result Transfor r may throw P pel neFa lure for cand dates that are malfor d and
   * should be removed. T  should be except onal behav or, and not a replace nt for add ng a F lter.
   * Complex use cases such as those that need access to features should construct t  r own transfor r, but
   * for s mple use cases,   can pass  n an anonymous funct on.
   * @example
   * {{{ overr de val queryTransfor r: Cand dateP pel neResultsTransfor r[Cand dateS ceResult, Result] = { s ceResult =>
   *   ExampleCand date(s ceResult. d)
   *  }
   * }}}
   *
   */
  val resultTransfor r: Cand dateP pel neResultsTransfor r[Cand dateS ceResult, Result]

  /**
   * Before f lters are run,   can fetch features for each cand date.
   *
   * Uses St ch, so   enc aged to use a work ng St ch Adaptor to batch bet en cand dates.
   *
   * T  ex st ng features (from t  cand date s ce) are passed  n as an  nput.   are not expected
   * to put t m  nto t  result ng feature map y self - t y w ll be  rged for   by t  platform.
   *
   * T  AP   s l kely to change w n Product M xer does managed feature hydrat on
   */
  val preF lterFeatureHydrat onPhase1: Seq[BaseCand dateFeatureHydrator[Query, Result, _]] =
    Seq.empty

  /**
   * A second phase of feature hydrat on that can be run before f lter ng and after t  f rst phase
   * of [[preF lterFeatureHydrat onPhase1]].   are not expected to put t m  nto t  result ng
   * feature map y self - t y w ll be  rged for   by t  platform.
   */
  val preF lterFeatureHydrat onPhase2: Seq[BaseCand dateFeatureHydrator[Query, Result, _]] =
    Seq.empty

  /** A l st of f lters to apply. F lters w ll be appl ed  n sequent al order. */
  def f lters: Seq[F lter[Query, Result]] = Seq.empty

  /**
   * After f lters are run,   can fetch features for each cand date.
   *
   * Uses St ch, so   enc aged to use a work ng St ch Adaptor to batch bet en cand dates.
   *
   * T  ex st ng features (from t  cand date s ce) & pre-f lter ng are passed  n as an  nput.
   *   are not expected to put t m  nto t  result ng feature map y self -
   * t y w ll be  rged for   by t  platform.
   *
   * T  AP   s l kely to change w n Product M xer does managed feature hydrat on
   */
  val postF lterFeatureHydrat on: Seq[BaseCand dateFeatureHydrator[Query, Result, _]] = Seq.empty

  /**
   * Decorators allow for add ng Presentat ons to cand dates. Wh le t  Presentat on can conta n any
   * arb rary data, Decorators are often used to add a Urt emPresentat on for URT  em support, or
   * a UrtModulePresentat on for group ng t  cand dates  n a URT module.
   */
  val decorator: Opt on[Cand dateDecorator[Query, Result]] = None

  /**
   * A cand date p pel ne can def ne a part al funct on to rescue fa lures  re. T y w ll be treated as fa lures
   * from a mon or ng standpo nt, and cancellat on except ons w ll always be propagated (t y cannot be caught  re).
   */
  def fa lureClass f er: Part alFunct on[Throwable, P pel neFa lure] = Part alFunct on.empty

  /**
   * Scorers for cand dates. Scorers are executed  n parallel. Order does not matter.
   */
  def scorers: Seq[Scorer[Query, Result]] = Seq.empty

  /**
   * Alerts can be used to  nd cate t  p pel ne's serv ce level object ves. Alerts and
   * dashboards w ll be automat cally created based on t   nformat on.
   */
  val alerts: Seq[Alert] = Seq.empty

  /**
   * T   thod  s used by t  product m xer fra work to bu ld t  p pel ne.
   */
  pr vate[core] f nal def bu ld(
    parentComponent dent f erStack: Component dent f erStack,
    factory: Cand dateP pel neBu lderFactory
  ): Cand dateP pel ne[Query] = {
    factory.get.bu ld(parentComponent dent f erStack, t )
  }
}

tra  Cand dateP pel neConf g[
  -Query <: P pel neQuery,
  Cand dateS ceQuery,
  Cand dateS ceResult,
  Result <: Un versalNoun[Any]]
    extends BaseCand dateP pel neConf g[
      Query,
      Cand dateS ceQuery,
      Cand dateS ceResult,
      Result
    ] {
  overr de val gates: Seq[Gate[Query]] = Seq.empty

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    Query,
    Cand dateS ceQuery
  ]
}

tra  DependentCand dateP pel neConf g[
  -Query <: P pel neQuery,
  Cand dateS ceQuery,
  Cand dateS ceResult,
  Result <: Un versalNoun[Any]]
    extends BaseCand dateP pel neConf g[
      Query,
      Cand dateS ceQuery,
      Cand dateS ceResult,
      Result
    ]

/**
 * Conta ns [[P pel neStep dent f er]]s for t  Steps that are ava lable for all [[BaseCand dateP pel neConf g]]s
 */
object Cand dateP pel neConf g extends P pel neConf gCompan on {
  val gatesStep: P pel neStep dent f er = P pel neStep dent f er("Gates")
  val fetchQueryFeaturesStep: P pel neStep dent f er = P pel neStep dent f er("FetchQueryFeatures")
  val fetchQueryFeaturesPhase2Step: P pel neStep dent f er = P pel neStep dent f er(
    "FetchQueryFeaturesPhase2")
  val cand dateS ceStep: P pel neStep dent f er = P pel neStep dent f er("Cand dateS ce")
  val preF lterFeatureHydrat onPhase1Step: P pel neStep dent f er =
    P pel neStep dent f er("PreF lterFeatureHydrat on")
  val preF lterFeatureHydrat onPhase2Step: P pel neStep dent f er =
    P pel neStep dent f er("PreF lterFeatureHydrat onPhase2")
  val f ltersStep: P pel neStep dent f er = P pel neStep dent f er("F lters")
  val postF lterFeatureHydrat onStep: P pel neStep dent f er =
    P pel neStep dent f er("PostF lterFeatureHydrat on")
  val scorersStep: P pel neStep dent f er = P pel neStep dent f er("Scorer")
  val decoratorStep: P pel neStep dent f er = P pel neStep dent f er("Decorator")
  val resultStep: P pel neStep dent f er = P pel neStep dent f er("Result")

  /** All t  steps wh ch are executed by a [[Cand dateP pel ne]]  n t  order  n wh ch t y are run */
  overr de val steps nOrder: Seq[P pel neStep dent f er] = Seq(
    gatesStep,
    fetchQueryFeaturesStep,
    fetchQueryFeaturesPhase2Step,
    asyncFeaturesStep(cand dateS ceStep),
    cand dateS ceStep,
    asyncFeaturesStep(preF lterFeatureHydrat onPhase1Step),
    preF lterFeatureHydrat onPhase1Step,
    asyncFeaturesStep(preF lterFeatureHydrat onPhase2Step),
    preF lterFeatureHydrat onPhase2Step,
    asyncFeaturesStep(f ltersStep),
    f ltersStep,
    asyncFeaturesStep(postF lterFeatureHydrat onStep),
    postF lterFeatureHydrat onStep,
    asyncFeaturesStep(scorersStep),
    scorersStep,
    asyncFeaturesStep(decoratorStep),
    decoratorStep,
    resultStep
  )

  overr de val stepsAsyncFeatureHydrat onCanBeCompletedBy: Set[P pel neStep dent f er] = Set(
    cand dateS ceStep,
    preF lterFeatureHydrat onPhase1Step,
    preF lterFeatureHydrat onPhase2Step,
    f ltersStep,
    postF lterFeatureHydrat onStep,
    scorersStep,
    decoratorStep
  )
}
