package com.tw ter.product_m xer.core.p pel ne.scor ng

 mport com.tw ter.product_m xer.component_l brary.selector. nsertAppendResults
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scor ngP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf gCompan on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam

/**
 *  T   s t  conf gurat on necessary to generate a Scor ng P pel ne. Product code should create a
 *  Scor ngP pel neConf g, and t n use a Scor ngP pel neBu lder to get t  f nal Scor ngP pel ne wh ch can
 *  process requests.
 *
 * @tparam Query - T  doma n model for t  query or request
 * @tparam Cand date t  doma n model for t  cand date be ng scored
 */
tra  Scor ngP pel neConf g[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]]
    extends P pel neConf g {

  overr de val  dent f er: Scor ngP pel ne dent f er

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

  /** [[BaseGate]]s that are appl ed sequent ally, t  p pel ne w ll only run  f all t  Gates are open */
  def gates: Seq[BaseGate[Query]] = Seq.empty

  /**
   * Log c for select ng wh ch cand dates to score. Note, t  doesn't drop t  cand dates from
   * t  f nal result, just w t r to score    n t  p pel ne or not.
   */
  def selectors: Seq[Selector[Query]]

  /**
   * After selectors are run,   can fetch features for each cand date.
   * T  ex st ng features from prev ous hydrat ons are passed  n as  nputs.   are not expected to
   * put t m  nto t  result ng feature map y self - t y w ll be  rged for   by t  platform.
   */
  def preScor ngFeatureHydrat onPhase1: Seq[BaseCand dateFeatureHydrator[Query, Cand date, _]] =
    Seq.empty

  /**
   * A second phase of feature hydrat on that can be run after select on and after t  f rst phase
   * of pre-scor ng feature hydrat on.   are not expected to put t m  nto t  result ng
   * feature map y self - t y w ll be  rged for   by t  platform.
   */
  def preScor ngFeatureHydrat onPhase2: Seq[BaseCand dateFeatureHydrator[Query, Cand date, _]] =
    Seq.empty

  /**
   * Ranker Funct on for cand dates. Scorers are executed  n parallel.
   * Note: Order does not matter, t  could be a Set  f Set was covar ant over  's type.
   */
  def scorers: Seq[Scorer[Query, Cand date]]

  /**
   * A p pel ne can def ne a part al funct on to rescue fa lures  re. T y w ll be treated as fa lures
   * from a mon or ng standpo nt, and cancellat on except ons w ll always be propagated (t y cannot be caught  re).
   */
  def fa lureClass f er: Part alFunct on[Throwable, P pel neFa lure] = Part alFunct on.empty

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
    bu lder: Scor ngP pel neBu lderFactory
  ): Scor ngP pel ne[Query, Cand date] =
    bu lder.get.bu ld(parentComponent dent f erStack, t )
}

object Scor ngP pel neConf g extends P pel neConf gCompan on {
  def apply[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    scorer: Scorer[Query, Cand date]
  ): Scor ngP pel neConf g[Query, Cand date] = new Scor ngP pel neConf g[Query, Cand date] {
    overr de val  dent f er: Scor ngP pel ne dent f er = Scor ngP pel ne dent f er(
      s"ScoreAll${scorer. dent f er.na }")

    overr de val selectors: Seq[Selector[Query]] = Seq( nsertAppendResults(AllP pel nes))

    overr de val scorers: Seq[Scorer[Query, Cand date]] = Seq(scorer)
  }

  val gatesStep: P pel neStep dent f er = P pel neStep dent f er("Gates")
  val selectorsStep: P pel neStep dent f er = P pel neStep dent f er("Selectors")
  val preScor ngFeatureHydrat onPhase1Step: P pel neStep dent f er =
    P pel neStep dent f er("PreScor ngFeatureHydrat onPhase1")
  val preScor ngFeatureHydrat onPhase2Step: P pel neStep dent f er =
    P pel neStep dent f er("PreScor ngFeatureHydrat onPhase2")
  val scorersStep: P pel neStep dent f er = P pel neStep dent f er("Scorers")
  val resultStep: P pel neStep dent f er = P pel neStep dent f er("Result")

  /** All t  Steps wh ch are executed by a [[Scor ngP pel ne]]  n t  order  n wh ch t y are run */
  overr de val steps nOrder: Seq[P pel neStep dent f er] = Seq(
    gatesStep,
    selectorsStep,
    preScor ngFeatureHydrat onPhase1Step,
    preScor ngFeatureHydrat onPhase2Step,
    scorersStep,
    resultStep
  )
}
