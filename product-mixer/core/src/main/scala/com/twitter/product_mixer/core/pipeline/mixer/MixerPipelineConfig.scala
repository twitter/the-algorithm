package com.tw ter.product_m xer.core.p pel ne.m xer

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.M xerP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.Fa lOpenPol cy
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf gCompan on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.cand date.DependentCand dateP pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.ClosedGate
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorConf g

/**
 *  T   s t  conf gurat on necessary to generate a M xer P pel ne. Product code should create a
 *  M xerP pel neConf g, and t n use a M xerP pel neBu lder to get t  f nal M xerP pel ne wh ch can
 *  process requests.
 *
 * @tparam Query - T  doma n model for t  query or request
 * @tparam UnmarshalledResultType - T  result type of t  p pel ne, but before marshall ng to a w re protocol l ke URT
 * @tparam Result - T  f nal result that w ll be served to users
 */
tra  M xerP pel neConf g[Query <: P pel neQuery, UnmarshalledResultType <: HasMarshall ng, Result]
    extends P pel neConf g {

  overr de val  dent f er: M xerP pel ne dent f er

  /**
   * M xer P pel ne Gates w ll be executed before any ot r step ( nclud ng retr eval from cand date
   * p pel nes). T y're executed sequent ally, and any "Stop" result w ll prevent p pel ne execut on.
   */
  def gates: Seq[Gate[Query]] = Seq.empty

  /**
   * A m xer p pel ne can fetch query-level features before cand date p pel nes are executed.
   */
  def fetchQueryFeatures: Seq[QueryFeatureHydrator[Query]] = Seq.empty

  /**
   * For query-level features that are dependent on query-level features from [[fetchQueryFeatures]]
   */
  def fetchQueryFeaturesPhase2: Seq[QueryFeatureHydrator[Query]] = Seq.empty

  /**
   * Cand date p pel nes retr eve cand dates for poss ble  nclus on  n t  result
   */
  def cand dateP pel nes: Seq[Cand dateP pel neConf g[Query, _, _, _]]

  /**
   * Dependent cand date p pel nes to retr eve cand dates that depend on t  result of [[cand dateP pel nes]]
   * [[DependentCand dateP pel neConf g]] have access to t  l st of prev ously retr eved & decorated
   * cand dates for use  n construct ng t  query object.
   */
  def dependentCand dateP pel nes: Seq[DependentCand dateP pel neConf g[Query, _, _, _]] = Seq.empty

  /**
   * [[defaultFa lOpenPol cy]]  s t  [[Fa lOpenPol cy]] that w ll be appl ed to any cand date
   * p pel ne that  sn't  n t  [[fa lOpenPol c es]] map. By default Cand date P pel nes w ll fa l
   * open for Closed Gates only.
   */
  def defaultFa lOpenPol cy: Fa lOpenPol cy = Fa lOpenPol cy(Set(ClosedGate))

  /**
   * [[fa lOpenPol c es]] assoc ates [[Fa lOpenPol cy]]s to spec f c cand date p pel nes us ng
   * [[Cand dateP pel ne dent f er]].
   *
   * @note t se [[Fa lOpenPol cy]]s overr de t  [[defaultFa lOpenPol cy]] for a mapped
   *       Cand date P pel ne.
   */
  def fa lOpenPol c es: Map[Cand dateP pel ne dent f er, Fa lOpenPol cy] = Map.empty

  /**
   ** [[qual yFactorConf gs]] assoc ates [[Qual yFactorConf g]]s to spec f c cand date p pel nes
   * us ng [[Cand dateP pel ne dent f er]].
   */
  def qual yFactorConf gs: Map[Cand dateP pel ne dent f er, Qual yFactorConf g] =
    Map.empty

  /**
   * Selectors are executed  n sequent al order to comb ne t  cand dates  nto a result
   */
  def resultSelectors: Seq[Selector[Query]]

  /**
   * M xer result s de effects that are executed after select on and doma n marshall ng
   */
  def resultS deEffects: Seq[P pel neResultS deEffect[Query, UnmarshalledResultType]] = Seq()

  /**
   * Doma n marshaller transforms t  select ons  nto t  model expected by t  marshaller
   */
  def doma nMarshaller: Doma nMarshaller[Query, UnmarshalledResultType]

  /**
   * Transport marshaller transforms t  model  nto   l ne-level AP  l ke URT or JSON
   */
  def transportMarshaller: TransportMarshaller[UnmarshalledResultType, Result]

  /**
   * A p pel ne can def ne a part al funct on to rescue fa lures  re. T y w ll be treated as fa lures
   * from a mon or ng standpo nt, and cancellat on except ons w ll always be propagated (t y cannot be caught  re).
   */
  def fa lureClass f er: Part alFunct on[Throwable, P pel neFa lure] = Part alFunct on.empty

  /**
   * Alert can be used to  nd cate t  p pel ne's serv ce level object ves. Alerts and
   * dashboards w ll be automat cally created based on t   nformat on.
   */
  val alerts: Seq[Alert] = Seq.empty

  /**
   * T   thod  s used by t  product m xer fra work to bu ld t  p pel ne.
   */
  pr vate[core] f nal def bu ld(
    parentComponent dent f erStack: Component dent f erStack,
    bu lder: M xerP pel neBu lderFactory
  ): M xerP pel ne[Query, Result] =
    bu lder.get.bu ld(parentComponent dent f erStack, t )
}

object M xerP pel neConf g extends P pel neConf gCompan on {
  val qual yFactorStep: P pel neStep dent f er = P pel neStep dent f er("Qual yFactor")
  val gatesStep: P pel neStep dent f er = P pel neStep dent f er("Gates")
  val fetchQueryFeaturesStep: P pel neStep dent f er = P pel neStep dent f er("FetchQueryFeatures")
  val fetchQueryFeaturesPhase2Step: P pel neStep dent f er =
    P pel neStep dent f er("FetchQueryFeaturesPhase2")
  val cand dateP pel nesStep: P pel neStep dent f er = P pel neStep dent f er("Cand dateP pel nes")
  val dependentCand dateP pel nesStep: P pel neStep dent f er =
    P pel neStep dent f er("DependentCand dateP pel nes")
  val resultSelectorsStep: P pel neStep dent f er = P pel neStep dent f er("ResultSelectors")
  val doma nMarshallerStep: P pel neStep dent f er = P pel neStep dent f er("Doma nMarshaller")
  val resultS deEffectsStep: P pel neStep dent f er = P pel neStep dent f er("ResultS deEffects")
  val transportMarshallerStep: P pel neStep dent f er = P pel neStep dent f er(
    "TransportMarshaller")

  /** All t  Steps wh ch are executed by a [[M xerP pel ne]]  n t  order  n wh ch t y are run */
  overr de val steps nOrder: Seq[P pel neStep dent f er] = Seq(
    qual yFactorStep,
    gatesStep,
    fetchQueryFeaturesStep,
    fetchQueryFeaturesPhase2Step,
    asyncFeaturesStep(cand dateP pel nesStep),
    cand dateP pel nesStep,
    asyncFeaturesStep(dependentCand dateP pel nesStep),
    dependentCand dateP pel nesStep,
    asyncFeaturesStep(resultSelectorsStep),
    resultSelectorsStep,
    doma nMarshallerStep,
    asyncFeaturesStep(resultS deEffectsStep),
    resultS deEffectsStep,
    transportMarshallerStep
  )

  /**
   * All t  Steps wh ch an [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.AsyncHydrator AsyncHydrator]]
   * can be conf gured to [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.AsyncHydrator.hydrateBefore hydrateBefore]]
   */
  overr de val stepsAsyncFeatureHydrat onCanBeCompletedBy: Set[P pel neStep dent f er] = Set(
    cand dateP pel nesStep,
    dependentCand dateP pel nesStep,
    resultSelectorsStep,
    resultS deEffectsStep
  )
}
