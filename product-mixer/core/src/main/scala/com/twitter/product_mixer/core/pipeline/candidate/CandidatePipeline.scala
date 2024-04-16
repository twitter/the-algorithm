package com.tw ter.product_m xer.core.p pel ne.cand date

 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.Arrow

/**
 * A Cand date P pel ne
 *
 * T   s an abstract class, as   only construct t se v a t  [[Cand dateP pel neBu lder]].
 *
 * A [[Cand dateP pel ne]]  s capable of process ng requests (quer es) and return ng cand dates
 *  n t  form of a [[Cand dateP pel neResult]]
 *
 * @tparam Query t  doma n model for t  query or request
 */
abstract class Cand dateP pel ne[-Query <: P pel neQuery] pr vate[cand date]
    extends P pel ne[Cand dateP pel ne. nputs[Query], Seq[Cand dateW hDeta ls]] {
  overr de pr vate[core] val conf g: BaseCand dateP pel neConf g[Query, _, _, _]
  overr de val arrow: Arrow[Cand dateP pel ne. nputs[Query], Cand dateP pel neResult]
  overr de val  dent f er: Cand dateP pel ne dent f er
}

object Cand dateP pel ne {
  case class  nputs[+Query <: P pel neQuery](
    query: Query,
    ex st ngCand dates: Seq[Cand dateW hDeta ls])
}
