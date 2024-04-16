package com.tw ter.product_m xer.core.funct onal_component.transfor r

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er

/**
 * A transfor r for transform ng a cand date p pel ne's s ce result type  nto t  parent's
 * m xer ore recom ndat on p pel ne's type.
 * @tparam S ceResult T  type of t  result of t  cand date s ce be ng used.
 * @tparam P pel neResult T  type of t  parent p pel ne's expected
 */
tra  Cand dateP pel neResultsTransfor r[S ceResult, P pel neResult <: Un versalNoun[Any]]
    extends Transfor r[S ceResult, P pel neResult] {

  overr de val  dent f er: Transfor r dent f er =
    Cand dateP pel neResultsTransfor r.DefaultTransfor r d
}

object Cand dateP pel neResultsTransfor r {
  pr vate[core] val DefaultTransfor r d: Transfor r dent f er =
    Transfor r dent f er(Component dent f er.BasedOnParentComponent)
  pr vate[core] val Transfor r dSuff x = "Results"

  /**
   * For use w n bu ld ng a [[Cand dateP pel neResultsTransfor r]]  n a [[com.tw ter.product_m xer.core.p pel ne.P pel neBu lder]]
   * to ensure that t   dent f er  s updated w h t  parent [[com.tw ter.product_m xer.core.p pel ne.P pel ne. dent f er]]
   */
  pr vate[core] def copyW hUpdated dent f er[S ceResult, P pel neResult <: Un versalNoun[Any]](
    resultTransfor r: Cand dateP pel neResultsTransfor r[S ceResult, P pel neResult],
    parent dent f er: Component dent f er
  ): Cand dateP pel neResultsTransfor r[S ceResult, P pel neResult] = {
     f (resultTransfor r. dent f er == DefaultTransfor r d) {
      new Cand dateP pel neResultsTransfor r[S ceResult, P pel neResult] {
        overr de val  dent f er: Transfor r dent f er = Transfor r dent f er(
          s"${parent dent f er.na }$Transfor r dSuff x")

        overr de def transform( nput: S ceResult): P pel neResult =
          resultTransfor r.transform( nput)
      }
    } else {
      resultTransfor r
    }
  }
}
