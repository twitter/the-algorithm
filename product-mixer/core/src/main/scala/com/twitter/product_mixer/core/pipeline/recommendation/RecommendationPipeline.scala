package com.tw ter.product_m xer.core.p pel ne.recom ndat on

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Recom ndat onP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.Arrow

/**
 * A Recom ndat on P pel ne
 *
 * T   s an abstract class, as   only construct t se v a t  [[Recom ndat onP pel neBu lder]].
 *
 * A [[Recom ndat onP pel ne]]  s capable of process ng requests (quer es) and return ng responses (results)
 *  n t  correct format to d rectly send to users.
 *
 * @tparam Query t  doma n model for t  query or request
 * @tparam Cand date t  type of t  cand dates
 * @tparam Result t  f nal marshalled result type
 */
abstract class Recom ndat onP pel ne[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any],
  Result]
    extends P pel ne[Query, Result] {
  overr de pr vate[core] val conf g: Recom ndat onP pel neConf g[Query, Cand date, _, Result]
  overr de val arrow: Arrow[Query, Recom ndat onP pel neResult[Cand date, Result]]
  overr de val  dent f er: Recom ndat onP pel ne dent f er
}
