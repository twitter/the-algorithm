package com.tw ter.product_m xer.component_l brary.s de_effect

 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.st ch.St ch

/**
 * A [[P pel neResultS deEffect]] that logs [[Thr ft]] data that's already ava lable to Scr be
 */
tra  Scr beLogEventS deEffect[
  Thr ft <: Thr ftStruct,
  Query <: P pel neQuery,
  ResponseType <: HasMarshall ng]
    extends P pel neResultS deEffect[Query, ResponseType] {

  /**
   * Bu ld t  log events from query, select ons and response
   * @param query P pel neQuery
   * @param selectedCand dates Result after Selectors are executed
   * @param rema n ngCand dates Cand dates wh ch  re not selected
   * @param droppedCand dates Cand dates dropped dur ng select on
   * @param response Result after Unmarshall ng
   * @return LogEvent  n thr ft
   */
  def bu ldLogEvents(
    query: Query,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: ResponseType
  ): Seq[Thr ft]

  val logP pel nePubl s r: EventPubl s r[Thr ft]

  f nal overr de def apply(
     nputs: P pel neResultS deEffect. nputs[Query, ResponseType]
  ): St ch[Un ] = {
    val logEvents = bu ldLogEvents(
      query =  nputs.query,
      selectedCand dates =  nputs.selectedCand dates,
      rema n ngCand dates =  nputs.rema n ngCand dates,
      droppedCand dates =  nputs.droppedCand dates,
      response =  nputs.response
    )

    St ch
      .collect(
        logEvents
          .map { logEvent =>
            St ch.callFuture(logP pel nePubl s r.publ sh(logEvent))
          }
      ).un 
  }
}
