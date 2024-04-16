package com.tw ter.product_m xer.core.funct onal_component.marshaller.request

 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cl entContextUnmarshaller @ nject() () {

  def apply(cl entContext: t.Cl entContext): Cl entContext = {
    Cl entContext(
      user d = cl entContext.user d,
      guest d = cl entContext.guest d,
      guest dAds = cl entContext.guest dAds,
      guest dMarket ng = cl entContext.guest dMarket ng,
      app d = cl entContext.app d,
       pAddress = cl entContext. pAddress,
      userAgent = cl entContext.userAgent,
      countryCode = cl entContext.countryCode,
      languageCode = cl entContext.languageCode,
       sTwoff ce = cl entContext. sTwoff ce,
      userRoles = cl entContext.userRoles.map(_.toSet),
      dev ce d = cl entContext.dev ce d,
      mob leDev ce d = cl entContext.mob leDev ce d,
      mob leDev ceAd d = cl entContext.mob leDev ceAd d,
      l m AdTrack ng = cl entContext.l m AdTrack ng
    )
  }
}
