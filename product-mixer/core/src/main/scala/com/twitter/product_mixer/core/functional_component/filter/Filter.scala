package com.tw ter.product_m xer.core.funct onal_component.f lter

 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter.SupportsCond  onally
 mport com.tw ter.product_m xer.core.model.common
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Takes a sequence of cand dates and can f lter so  out
 *
 * @note  f   want to cond  onally run a [[F lter]]   can use t  m x n [[F lter.Cond  onally]]
 *       or to gate on a [[com.tw ter.t  l nes.conf gap .Param]]   can use [[com.tw ter.product_m xer.component_l brary.f lter.ParamGatedF lter]]
 *
 * @tparam Query T  doma n model for t  query or request
 * @tparam Cand date T  type of t  cand dates
 */
tra  F lter[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]]
    extends Component
    w h SupportsCond  onally[Query, Cand date] {

  /** @see [[F lter dent f er]] */
  overr de val  dent f er: F lter dent f er

  /**
   * F lter t  l st of cand dates
   *
   * @return a F lterResult  nclud ng both t  l st of kept cand date and t  l st of removed cand dates
   */
  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]]
}

object F lter {

  /**
   * M x n for w n   want to cond  onally run a [[F lter]]
   *
   * T   s a th n wrapper around [[common.Cond  onally]] expos ng a n cer AP  for t  [[F lter]] spec f c use-case.
   */
  tra  Cond  onally[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]]
      extends common.Cond  onally[ nput[Query, Cand date]] { _: F lter[Query, Cand date] =>

    /** @see [[common.Cond  onally.only f]] */
    def only f(
      query: Query,
      cand dates: Seq[Cand dateW hFeatures[Cand date]]
    ): Boolean

    overr de f nal def only f( nput:  nput[Query, Cand date]): Boolean =
      only f( nput.query,  nput.cand dates)
  }

  /** Type al as to obscure [[F lter. nput]] from custo rs */
  type SupportsCond  onally[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]] =
    common.SupportsCond  onally[ nput[Query, Cand date]]

  /** A case class represent ng t   nput argu nts to a [[F lter]], mostly for  nternal use */
  case class  nput[+Query <: P pel neQuery, +Cand date <: Un versalNoun[Any]](
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]])
}
