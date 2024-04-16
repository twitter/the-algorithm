package com.tw ter.product_m xer.core.funct onal_component.s de_effect

 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect. nputs
 mport com.tw ter.product_m xer.core.model.common
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * A s de-effect that can be run w h a p pel ne result before transport marshall ng
 *
 * @see S deEffect
 *
 * @tparam Query p pel ne query
 * @tparam ResultType response after doma n marshall ng
 */
tra  P pel neResultS deEffect[-Query <: P pel neQuery, -ResultType <: HasMarshall ng]
    extends S deEffect[ nputs[Query, ResultType]]
    w h P pel neResultS deEffect.SupportsCond  onally[Query, ResultType]

object P pel neResultS deEffect {

  /**
   * M x n for w n   want to cond  onally run a [[P pel neResultS deEffect]]
   *
   * T   s a th n wrapper around [[common.Cond  onally]] expos ng a n cer AP  for t  [[P pel neResultS deEffect]] spec f c use-case.
   */
  tra  Cond  onally[-Query <: P pel neQuery, -ResultType <: HasMarshall ng]
      extends common.Cond  onally[ nputs[Query, ResultType]] {
    _: P pel neResultS deEffect[Query, ResultType] =>

    /** @see [[common.Cond  onally.only f]] */
    def only f(
      query: Query,
      selectedCand dates: Seq[Cand dateW hDeta ls],
      rema n ngCand dates: Seq[Cand dateW hDeta ls],
      droppedCand dates: Seq[Cand dateW hDeta ls],
      response: ResultType
    ): Boolean

    overr de f nal def only f( nput:  nputs[Query, ResultType]): Boolean =
      only f(
         nput.query,
         nput.selectedCand dates,
         nput.rema n ngCand dates,
         nput.droppedCand dates,
         nput.response)

  }

  type SupportsCond  onally[-Query <: P pel neQuery, -ResultType <: HasMarshall ng] =
    common.SupportsCond  onally[ nputs[Query, ResultType]]

  case class  nputs[+Query <: P pel neQuery, +ResultType <: HasMarshall ng](
    query: Query,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: ResultType)
}
