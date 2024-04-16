package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.M sconf guredFeatureMapFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lureCategory

/**
 * [[Fa lOpenPol cy]] determ nes what should happen  n t  event that a cand date p pel ne fa ls
 * to execute successfully.
 *
 * Exerc se caut on w n creat ng new fa l open pol c es. Product M xer w ll fa l open by default  n
 * certa n error cases (e.g. closed gate on a cand date p pel ne) but t se m ght  nadvertently be
 * excluded by a new pol cy.
 */
tra  Fa lOpenPol cy {
  def apply(fa lureCategory: P pel neFa lureCategory): Boolean
}

object Fa lOpenPol cy {

  /**
   * Always fa l open on cand date p pel ne fa lures except
   * for [[M sconf guredFeatureMapFa lure]]s because  's a program r error
   * and should always fa l loudly, even w h an [[Always]] p[[Fa lOpenPol cy]]
   */
  val Always: Fa lOpenPol cy = (category: P pel neFa lureCategory) => {
    category != M sconf guredFeatureMapFa lure
  }

  /**
   * Never fa l open on cand date p pel ne fa lures.
   *
   * @note t   s more restr ct ve than t  default behav or wh ch  s to allow gate closed
   *       fa lures.
   */
  val Never: Fa lOpenPol cy = (_: P pel neFa lureCategory) => false

  // Bu ld a pol cy that w ll fa l open for a g ven set of categor es
  def apply(categor es: Set[P pel neFa lureCategory]): Fa lOpenPol cy =
    (fa lureCategory: P pel neFa lureCategory) =>
      categor es
        .conta ns(fa lureCategory)
}
