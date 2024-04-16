package com.tw ter.product_m xer.component_l brary.s de_effect

 mport com.tw ter.abdec der.Scr b ngABDec derUt l
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.scr bel b.marshallers
 mport com.tw ter.scr bel b.marshallers.Cl entDataProv der
 mport com.tw ter.scr bel b.marshallers.LogEventMarshaller

/**
 * S de effect to log cl ent events server-s de. Create an  mple ntat on of t  tra  by
 * def n ng t  `bu ldCl entEvents`  thod, and t  `page` val.
 * T  Cl entEvent w ll be automat cally converted  nto a [[LogEvent]] and scr bed.
 */
tra  Scr beCl entEventS deEffect[
  Query <: P pel neQuery,
  UnmarshalledResponseType <: HasMarshall ng]
    extends Scr beLogEventS deEffect[LogEvent, Query, UnmarshalledResponseType] {

  /**
   * T  page wh ch w ll be def ned  n t  na space. T   s typ cally t  serv ce na  that's scr b ng
   */
  val page: Str ng

  /**
   * Bu ld t  cl ent events from query, select ons and response
   *
   * @param query P pel neQuery
   * @param selectedCand dates Result after Selectors are executed
   * @param rema n ngCand dates Cand dates wh ch  re not selected
   * @param droppedCand dates Cand dates dropped dur ng select on
   * @param response Result after Unmarshall ng
   */
  def bu ldCl entEvents(
    query: Query,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: UnmarshalledResponseType
  ): Seq[Scr beCl entEventS deEffect.Cl entEvent]

  f nal overr de def bu ldLogEvents(
    query: Query,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: UnmarshalledResponseType
  ): Seq[LogEvent] = {
    bu ldCl entEvents(
      query = query,
      selectedCand dates = selectedCand dates,
      rema n ngCand dates = rema n ngCand dates,
      droppedCand dates = droppedCand dates,
      response = response).flatMap { event =>
      val cl entData = cl entContextToCl entDataProv der(query)

      val cl entNa  = Scr b ngABDec derUt l.cl entForApp d(cl entData.cl entAppl cat on d)

      val na spaceMap: Map[Str ng, Str ng] = Map(
        "cl ent" -> So (cl entNa ),
        "page" -> So (page),
        "sect on" -> event.na space.sect on,
        "component" -> event.na space.component,
        "ele nt" -> event.na space.ele nt,
        "act on" -> event.na space.act on
      ).collect { case (k, So (v)) => k -> v }

      val data: Map[Any, Any] = Seq(
        event.eventValue.map("event_value" -> _),
        event.latencyMs.map("latency_ms" -> _)
      ).flatten.toMap

      val cl entEventData = data +
        ("event_na space" -> na spaceMap) +
        (marshallers.CategoryKey -> "cl ent_event")

      LogEventMarshaller.marshal(
        data = cl entEventData,
        cl entData = cl entData
      )
    }
  }

  /**
   * Makes a [[Cl entDataProv der]] from t  [[P pel neQuery.cl entContext]] from t  [[query]]
   */
  pr vate def cl entContextToCl entDataProv der(query: Query): Cl entDataProv der = {
    new Cl entDataProv der {
      overr de val user d = query.cl entContext.user d
      overr de val guest d = query.cl entContext.guest d
      overr de val personal zat on d = None
      overr de val dev ce d = query.cl entContext.dev ce d
      overr de val cl entAppl cat on d = query.cl entContext.app d
      overr de val parentAppl cat on d = None
      overr de val countryCode = query.cl entContext.countryCode
      overr de val languageCode = query.cl entContext.languageCode
      overr de val userAgent = query.cl entContext.userAgent
      overr de val  sSsl = None
      overr de val referer = None
      overr de val externalReferer = None
    }
  }
}

object Scr beCl entEventS deEffect {
  case class EventNa space(
    sect on: Opt on[Str ng] = None,
    component: Opt on[Str ng] = None,
    ele nt: Opt on[Str ng] = None,
    act on: Opt on[Str ng] = None)

  case class Cl entEvent(
    na space: EventNa space,
    eventValue: Opt on[Long] = None,
    latencyMs: Opt on[Long] = None)
}
