package com.tw ter.product_m xer.component_l brary.s de_effect

 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent. nserter

/**
 * S de effect that wr es to Strato column's  nsert Op. Create an  mple ntat on of t  tra  by
 * def n ng t  `bu ldEvents`  thod and prov d ng a Strato Column  nserter of type
 * (StratoKeyarg, StratoValue) -> Any.
 * See https://docb rd.tw ter.b z/strato/ColumnCatalog.html# nsert for  nformat on about
 * t   nsert operat on  n Strato.
 *
 * @tparam StratoKeyarg Argu nt used as a key for Strato column. Could be Un  for common use-cases.
 * @tparam StratoValue Value that  s  nserted at t  Strato column.
 * @tparam Query P pel neQuery
 * @tparam Doma nResponseType T  l ne response that  s marshalled to doma n model (e.g. URT, Sl ce etc).
 */
tra  Strato nsertS deEffect[
  StratoKeyarg,
  StratoValue,
  Query <: P pel neQuery,
  Doma nResponseType <: HasMarshall ng]
    extends P pel neResultS deEffect[Query, Doma nResponseType] {

  /**
   *  nserter for t   nsertOp on a StratoColumn.  n Strato, t   nsertOp  s represented as
   * (Keyarg, Value) => Key, w re Key represents t  result returned by t   nsert operat on.
   * For t  s de-effect behav or,   do not need t  return value and use Any  nstead.
   */
  val strato nserter:  nserter[StratoKeyarg, StratoValue, Any]

  /**
   * Bu lds t  events that are  nserted to t  Strato column. T   thod supports generat ng
   * mult ple events for a s ngle s de-effect  nvocat on.
   *
   * @param query P pel neQuery
   * @param selectedCand dates Result after Selectors are executed
   * @param rema n ngCand dates Cand dates wh ch  re not selected
   * @param droppedCand dates Cand dates dropped dur ng select on
   * @param response T  l ne response that  s marshalled to doma n model (e.g. URT, Sl ce etc).
   * @return Tuples of (StratoKeyArg, StratoValue) that are used to call t  strato nserter.
   */
  def bu ldEvents(
    query: Query,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: Doma nResponseType
  ): Seq[(StratoKeyarg, StratoValue)]

  f nal overr de def apply(
     nputs: P pel neResultS deEffect. nputs[Query, Doma nResponseType]
  ): St ch[Un ] = {
    val events = bu ldEvents(
      query =  nputs.query,
      selectedCand dates =  nputs.selectedCand dates,
      rema n ngCand dates =  nputs.rema n ngCand dates,
      droppedCand dates =  nputs.droppedCand dates,
      response =  nputs.response
    )

    St ch
      .traverse(events) { case (keyarg, value) => strato nserter. nsert(keyarg, value) }
      .un 
  }
}
