package com.tw ter.ho _m xer.product.scored_t ets.marshaller

 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsResponse
 mport com.tw ter.product_m xer.core.funct onal_component.premarshaller.Doma nMarshaller
 mport com.tw ter.product_m xer.core.model.common. dent f er.Doma nMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls

/**
 * Creates a doma n model of t  Scored T ets product response from t  set of cand dates selected
 */
object ScoredT etsResponseDoma nMarshaller
    extends Doma nMarshaller[ScoredT etsQuery, ScoredT etsResponse] {

  overr de val  dent f er: Doma nMarshaller dent f er =
    Doma nMarshaller dent f er("ScoredT etsResponse")

  overr de def apply(
    query: ScoredT etsQuery,
    select ons: Seq[Cand dateW hDeta ls]
  ): ScoredT etsResponse = ScoredT etsResponse(scoredT ets = select ons)
}
