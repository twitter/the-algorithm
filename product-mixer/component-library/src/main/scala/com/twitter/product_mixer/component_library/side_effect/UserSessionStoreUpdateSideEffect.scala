package com.tw ter.product_m xer.component_l brary.s de_effect

 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.user_sess on_store.ReadWr eUserSess onStore
 mport com.tw ter.user_sess on_store.Wr eRequest

/**
 * A [[P pel neResultS deEffect]] that wr es to a [[ReadWr eUserSess onStore]]
 */
tra  UserSess onStoreUpdateS deEffect[
  Request <: Wr eRequest,
  Query <: P pel neQuery,
  ResponseType <: HasMarshall ng]
    extends P pel neResultS deEffect[Query, ResponseType] {

  /**
   * Bu ld t  wr e request from t  query
   * @param query P pel neQuery
   * @return Wr eRequest
   */
  def bu ldWr eRequest(query: Query): Opt on[Request]

  val userSess onStore: ReadWr eUserSess onStore

  f nal overr de def apply(
     nputs: P pel neResultS deEffect. nputs[Query, ResponseType]
  ): St ch[Un ] = {
    bu ldWr eRequest( nputs.query)
      .map(userSess onStore.wr e)
      .getOrElse(St ch.Un )
  }
}
