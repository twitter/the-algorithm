package com.tw ter.product_m xer.core.p pel ne.recom ndat on

 mport com.tw ter.product_m xer.component_l brary.selector. nsertAppendResults
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseQueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.common. dent f er.Recom ndat onP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scor ngP pel ne dent f er
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
 mport com.tw ter.product_m xer.core.p pel ne.scor ng.Scor ngP pel neConf g
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorConf g

/**
 *  T   s t  conf gurat on necessary to generate a Recom ndat on P pel ne. Product code should create a
 *  Recom ndat onP pel neConf g, and t n use a Recom ndat onP pel neBu lder to get t  f nal Recom ndat onP pel ne wh ch can
 *  process requests.
 *
 * @tparam Query - T  doma n model for t  query or request
 * @tparam Cand date - T  type of t  cand dates that t  Cand date P pel nes are generat ng
 * @tparam UnmarshalledResultType - T  result type of t  p pel ne, but before marshall ng to a w re protocol l ke URT
 * @tparam Result - T  f nal result that w ll be served to users
 */
tra  Recom ndat onP pel neConf g[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  UnmarshalledResultType <: HasMarshall ng,
  Result]
    extends P pel neConf g {

  overr de val  dent f er: Recom ndat onP pel ne dent f er

  /**
   * Recom ndat on P pel ne Gates w ll be executed before any ot r step ( nclud ng retr eval from cand date
   * p pel nes). T y're executed sequent ally, and any "Stop" result w ll prevent p pel ne execut on.
   */
  def gates: Seq[Gate[Query]] = Seq.empty

  /**
   * A recom ndat on p pel ne can fetch query-level features before cand date p pel nes are executed.
   */
  def fetchQueryFeatures: Seq[BaseQueryFeatureHydrator[Query, _]] = Seq.empty

  /**
   * Cand date p pel nes retr eve cand dates for poss ble  nclus on  n t  result
   */
  def fetchQueryFeaturesPhase2: Seq[BaseQueryFeatureHydrator[Query, _]] = Seq.empty

  /**
   * What cand date p pel nes should t  Recom ndat ons P pel ne get cand date from?
   */
  def cand dateP pel nes: Seq[Cand dateP pel neConf g[Query, _, _, _]]

  /**
   * Dependent cand date p pel nes to retr eve cand dates that depend on t  result of [[cand dateP pel nes]]
   * [[DependentCand dateP pel neConf g]] have access to t  l st of prev ously retr eved & decorated
   * cand dates for use  n construct ng t  query object.
   */
  def dependentCand dateP pel nes: Seq[DependentCand dateP pel neConf g[Query, _, _, _]] = Seq.empty

  /**
   * Takes f nal ranked l st of cand dates & apply any bus ness log c (e.g, dedupl cat ng and  rg ng
   * cand dates before scor ng).
   */
  def postCand dateP pel nesSelectors: Seq[Selector[Query]] = Seq( nsertAppendResults(AllP pel nes))

  /**
   * After selectors are run,   can fetch features for each cand date.
   * T  ex st ng features from prev ous hydrat ons are passed  n as  nputs.   are not expected to
   * put t m  nto t  result ng feature map y self - t y w ll be  rged for   by t  platform.
   */
  def postCand dateP pel nesFeatureHydrat on: Seq[
    BaseCand dateFeatureHydrator[Query, Cand date, _]
  ] =
    Seq.empty

  /**
   * Global f lters to run on all cand dates.
   */
  def globalF lters: Seq[F lter[Query, Cand date]] = Seq.empty

  /**
   * By default, a Recom ndat on P pel ne w ll fa l closed -  f any cand date or scor ng
   * p pel ne fa ls to return a result, t n t  Recom ndat on P pel ne w ll not return a result.
   *   can adjust t  default pol cy, or prov de spec f c pol c es to spec f c p pel nes.
   * Those spec f c pol c es w ll take pr or y.
   *
   * Fa lOpenPol cy.All w ll always fa l open (t  Recom ndat onP pel ne w ll cont nue w hout that p pel ne)
   * Fa lOpenPol cy.Never w ll always fa l closed (t  Recom ndat onP pel ne w ll fa l  f that p pel ne fa ls)
   *
   * T re's a default pol cy, and a spec f c Map of pol c es that takes precedence.
   */
  def defaultFa lOpenPol cy: Fa lOpenPol cy = Fa lOpenPol cy(Set(ClosedGate))
  def cand dateP pel neFa lOpenPol c es: Map[Cand dateP pel ne dent f er, Fa lOpenPol cy] =
    Map.empty
  def scor ngP pel neFa lOpenPol c es: Map[Scor ngP pel ne dent f er, Fa lOpenPol cy] = Map.empty

  /**
   ** [[qual yFactorConf gs]] assoc ates [[Qual yFactorConf g]]s to spec f c cand date p pel nes
   * us ng [[Component dent f er]].
   */
  def qual yFactorConf gs: Map[Component dent f er, Qual yFactorConf g] =
    Map.empty

  /**
   * Scor ng p pel nes for scor ng cand dates.
   * @note T se do not drop or re-order cand dates,   should do those  n t  sub-sequent selectors
   * step based off of t  scores on cand dates set  n those [[Scor ngP pel ne]]s.
   */
  def scor ngP pel nes: Seq[Scor ngP pel neConf g[Query, Cand date]]

  /**
   * Takes f nal ranked l st of cand dates & apply any bus ness log c (e.g, capp ng number
   * of ad accounts or pac ng ad accounts).
   */
  def resultSelectors: Seq[Selector[Query]]

  /**
   * Takes t  f nal selected l st of cand dates and appl es a f nal l st of f lters.
   * Useful for do ng very expens ve f lter ng at t  end of y  p pel ne.
   */
  def postSelect onF lters: Seq[F lter[Query, Cand date]] = Seq.empty

  /**
   * Decorators allow for add ng Presentat ons to cand dates. Wh le t  Presentat on can conta n any
   * arb rary data, Decorators are often used to add a Urt emPresentat on for URT  em support. Most
   * custo rs w ll prefer to set a decorator  n t  r respect ve cand date p pel ne, ho ver, a f nal
   * global one  s ava lable for those that do global decorat on as late poss ble to avo d unnecessary hydrat ons.
   * @note T  decorator can only return an  emPresentat on.
   * @note T  decorator cannot decorate an already decorated cand date from t  pr or decorator
   *       step  n cand date p pel nes.
   */
  def decorator: Opt on[Cand dateDecorator[Query, Cand date]] = None

  /**
   * Doma n marshaller transforms t  select ons  nto t  model expected by t  marshaller
   */
  def doma nMarshaller: Doma nMarshaller[Query, UnmarshalledResultType]

  /**
   * M xer result s de effects that are executed after select on and doma n marshall ng
   */
  def resultS deEffects: Seq[P pel neResultS deEffect[Query, UnmarshalledResultType]] = Seq()

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
   * Alerts can be used to  nd cate t  p pel ne's serv ce level object ves. Alerts and
   * dashboards w ll be automat cally created based on t   nformat on.
   */
  val alerts: Seq[Alert] = Seq.empty

  /**
   * T   thod  s used by t  product m xer fra work to bu ld t  p pel ne.
   */
  pr vate[core] f nal def bu ld(
    parentComponent dent f erStack: Component dent f erStack,
    bu lder: Recom ndat onP pel neBu lderFactory
  ): Recom ndat onP pel ne[Query, Cand date, Result] =
    bu lder.get.bu ld(parentComponent dent f erStack, t )
}

object Recom ndat onP pel neConf g extends P pel neConf gCompan on {
  val qual yFactorStep: P pel neStep dent f er = P pel neStep dent f er("Qual yFactor")
  val gatesStep: P pel neStep dent f er = P pel neStep dent f er("Gates")
  val fetchQueryFeaturesStep: P pel neStep dent f er = P pel neStep dent f er("FetchQueryFeatures")
  val fetchQueryFeaturesPhase2Step: P pel neStep dent f er = P pel neStep dent f er(
    "FetchQueryFeaturesPhase2")
  val cand dateP pel nesStep: P pel neStep dent f er = P pel neStep dent f er("Cand dateP pel nes")
  val dependentCand dateP pel nesStep: P pel neStep dent f er =
    P pel neStep dent f er("DependentCand dateP pel nes")
  val postCand dateP pel nesSelectorsStep: P pel neStep dent f er =
    P pel neStep dent f er("PostCand dateP pel nesSelectors")
  val postCand dateP pel nesFeatureHydrat onStep: P pel neStep dent f er =
    P pel neStep dent f er("PostCand dateP pel nesFeatureHydrat on")
  val globalF ltersStep: P pel neStep dent f er = P pel neStep dent f er("GlobalF lters")
  val scor ngP pel nesStep: P pel neStep dent f er = P pel neStep dent f er("Scor ngP pel nes")
  val resultSelectorsStep: P pel neStep dent f er = P pel neStep dent f er("ResultSelectors")
  val postSelect onF ltersStep: P pel neStep dent f er = P pel neStep dent f er(
    "PostSelect onF lters")
  val decoratorStep: P pel neStep dent f er = P pel neStep dent f er("Decorator")
  val doma nMarshallerStep: P pel neStep dent f er = P pel neStep dent f er("Doma nMarshaller")
  val resultS deEffectsStep: P pel neStep dent f er = P pel neStep dent f er("ResultS deEffects")
  val transportMarshallerStep: P pel neStep dent f er = P pel neStep dent f er(
    "TransportMarshaller")

  /** All t  Steps wh ch are executed by a [[Recom ndat onP pel ne]]  n t  order  n wh ch t y are run */
  overr de val steps nOrder: Seq[P pel neStep dent f er] = Seq(
    qual yFactorStep,
    gatesStep,
    fetchQueryFeaturesStep,
    fetchQueryFeaturesPhase2Step,
    asyncFeaturesStep(cand dateP pel nesStep),
    cand dateP pel nesStep,
    asyncFeaturesStep(dependentCand dateP pel nesStep),
    dependentCand dateP pel nesStep,
    asyncFeaturesStep(postCand dateP pel nesSelectorsStep),
    postCand dateP pel nesSelectorsStep,
    asyncFeaturesStep(postCand dateP pel nesFeatureHydrat onStep),
    postCand dateP pel nesFeatureHydrat onStep,
    asyncFeaturesStep(globalF ltersStep),
    globalF ltersStep,
    asyncFeaturesStep(scor ngP pel nesStep),
    scor ngP pel nesStep,
    asyncFeaturesStep(resultSelectorsStep),
    resultSelectorsStep,
    asyncFeaturesStep(postSelect onF ltersStep),
    postSelect onF ltersStep,
    asyncFeaturesStep(decoratorStep),
    decoratorStep,
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
    postCand dateP pel nesSelectorsStep,
    postCand dateP pel nesFeatureHydrat onStep,
    globalF ltersStep,
    scor ngP pel nesStep,
    resultSelectorsStep,
    postSelect onF ltersStep,
    decoratorStep,
    resultS deEffectsStep,
  )
}
