package com.tw ter.product_m xer.core.p pel ne.product

 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.AccessPol cy
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.ProductP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf g
 mport com.tw ter.product_m xer.core.p pel ne.P pel neConf gCompan on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorConf g
 mport com.tw ter.t  l nes.conf gap .Params

tra  ProductP pel neConf g[TRequest <: Request, Query <: P pel neQuery, Response]
    extends P pel neConf g {

  overr de val  dent f er: ProductP pel ne dent f er

  val product: Product
  val paramConf g: ProductParamConf g

  /**
   * Product P pel ne Gates w ll be executed before any ot r step ( nclud ng retr eval from m xer
   * p pel nes). T y're executed sequent ally, and any "Stop" result w ll prevent p pel ne execut on.
   */
  def gates: Seq[Gate[Query]] = Seq.empty

  def p pel neQueryTransfor r(request: TRequest, params: Params): Query

  /**
   * A l st of all p pel nes that po r t  product d rectly (t re  s no need to  nclude p pel nes
   * called by those p pel nes).
   *
   * Only p pel ne from t  l st should referenced from t  p pel neSelector
   */
  def p pel nes: Seq[P pel neConf g]

  /**
   * A p pel ne selector selects a p pel ne (from t  l st  n `def p pel nes`) to handle t 
   * current request.
   */
  def p pel neSelector(query: Query): Component dent f er

  /**
   ** [[qual yFactorConf gs]] assoc ates [[Qual yFactorConf g]]s to spec f c p pel nes
   * us ng [[Component dent f er]].
   */
  def qual yFactorConf gs: Map[Component dent f er, Qual yFactorConf g] =
    Map.empty

  /**
   * By default (for safety), product m xer p pel nes do not allow logged out requests.
   * A "DenyLoggedOutUsersGate" w ll be generated and added to t  p pel ne.
   *
   *   can d sable t  behav or by overr d ng `denyLoggedOutUsers` w h False.
   */
  val denyLoggedOutUsers: Boolean = true

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
   * Access Pol c es can be used to gate who can query a product from Product M xer's query tool
   * (go/turntable).
   *
   * T  w ll typ cally be gated by an LDAP group assoc ated w h y  team. For example:
   *
   * {{{
   *   overr de val debugAccessPol c es: Set[AccessPol cy] = Set(Allo dLdapGroups("NAME"))
   * }}}
   *
   *   can d sable all quer es by us ng t  [[com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.BlockEveryth ng]] pol cy.
   */
  val debugAccessPol c es: Set[AccessPol cy]
}

object ProductP pel neConf g extends P pel neConf gCompan on {
  val p pel neQueryTransfor rStep: P pel neStep dent f er = P pel neStep dent f er(
    "P pel neQueryTransfor r")
  val qual yFactorStep: P pel neStep dent f er = P pel neStep dent f er("Qual yFactor")
  val gatesStep: P pel neStep dent f er = P pel neStep dent f er("Gates")
  val p pel neSelectorStep: P pel neStep dent f er = P pel neStep dent f er("P pel neSelector")
  val p pel neExecut onStep: P pel neStep dent f er = P pel neStep dent f er("P pel neExecut on")

  /** All t  Steps wh ch are executed by a [[ProductP pel ne]]  n t  order  n wh ch t y are run */
  overr de val steps nOrder: Seq[P pel neStep dent f er] = Seq(
    p pel neQueryTransfor rStep,
    qual yFactorStep,
    gatesStep,
    p pel neSelectorStep,
    p pel neExecut onStep
  )
}
