package com.tw ter.product_m xer.core.p pel ne.scor ng

 mport com.tw ter.product_m xer.core.funct onal_component.scorer.ScoredCand dateResult
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scor ngP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.Arrow

/**
 * A Scor ng P pel ne
 *
 * T   s an abstract class, as   only construct t se v a t  [[Scor ngP pel neBu lder]].
 *
 * A [[Scor ngP pel ne]]  s capable of pre-f lter ng cand dates for scor ng, perform ng t  scor ng
 * t n runn ng select on  ur st cs (rank ng, dropp ng, etc) based off of t  score.
 * @tparam Query t  doma n model for t  query or request
 * @tparam Cand date t  doma n model for t  cand date be ng scored
 */
abstract class Scor ngP pel ne[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]]
    extends P pel ne[Scor ngP pel ne. nputs[Query], Seq[ScoredCand dateResult[Cand date]]] {
  overr de pr vate[core] val conf g: Scor ngP pel neConf g[Query, Cand date]
  overr de val arrow: Arrow[Scor ngP pel ne. nputs[Query], Scor ngP pel neResult[Cand date]]
  overr de val  dent f er: Scor ngP pel ne dent f er
}

object Scor ngP pel ne {
  case class  nputs[+Query <: P pel neQuery](
    query: Query,
    cand dates: Seq[ emCand dateW hDeta ls])
}
