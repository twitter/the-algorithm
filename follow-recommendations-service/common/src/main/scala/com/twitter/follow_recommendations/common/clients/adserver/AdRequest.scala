package com.tw ter.follow_recom ndat ons.common.cl ents.adserver

 mport com.tw ter.adserver.{thr ftscala => t}
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext

case class AdRequest(
  cl entContext: Cl entContext,
  d splayLocat on: D splayLocat on,
   sTest: Opt on[Boolean],
  prof leUser d: Opt on[Long]) {
  def toThr ft: t.AdRequestParams = {

    val request = t.AdRequest(
      d splayLocat on = d splayLocat on.toAdD splayLocat on.getOrElse(
        throw new M ss ngAdD splayLocat on(d splayLocat on)),
       sTest =  sTest,
      count mpress onsOnCallback = So (true),
      numOrgan c ems = So (AdRequest.DefaultNumOrgan c ems.toShort),
      prof leUser d = prof leUser d
    )

    val cl ent nfo = t.Cl ent nfo(
      cl ent d = cl entContext.app d.map(_.to nt),
      user p = cl entContext. pAddress,
      user d64 = cl entContext.user d,
      guest d = cl entContext.guest d,
      userAgent = cl entContext.userAgent,
      referrer = None,
      dev ce d = cl entContext.dev ce d,
      languageCode = cl entContext.languageCode,
      countryCode = cl entContext.countryCode
    )

    t.AdRequestParams(request, cl ent nfo)
  }
}

object AdRequest {
  val DefaultNumOrgan c ems = 10
}

class M ss ngAdD splayLocat on(d splayLocat on: D splayLocat on)
    extends Except on(
      s"D splay Locat on ${d splayLocat on.toStr ng} has no mapped AdsD splayLocat on set.")
