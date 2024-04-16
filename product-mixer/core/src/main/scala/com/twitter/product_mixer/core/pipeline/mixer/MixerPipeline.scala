package com.tw ter.product_m xer.core.p pel ne.m xer

 mport com.tw ter.product_m xer.core.model.common. dent f er.M xerP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.Arrow

/**
 * A M xer P pel ne
 *
 * T   s an abstract class, as   only construct t se v a t  [[M xerP pel neBu lder]].
 *
 * A [[M xerP pel ne]]  s capable of process ng requests (quer es) and return ng responses (results)
 *  n t  correct format to d rectly send to users.
 *
 * @tparam Query t  doma n model for t  query or request
 * @tparam Result t  f nal marshalled result type
 */
abstract class M xerP pel ne[Query <: P pel neQuery, Result] pr vate[m xer]
    extends P pel ne[Query, Result] {
  overr de pr vate[core] val conf g: M xerP pel neConf g[Query, _, Result]
  overr de val arrow: Arrow[Query, M xerP pel neResult[Result]]
  overr de val  dent f er: M xerP pel ne dent f er
}
