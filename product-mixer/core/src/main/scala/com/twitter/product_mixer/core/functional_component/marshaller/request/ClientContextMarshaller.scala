package com.tw ter.product_m xer.core.funct onal_component.marshaller.request

 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.{thr ftscala => t}

object Cl entContextMarshaller {

  def apply(cl entContext: Cl entContext): t.Cl entContext = {
    t.Cl entContext(
      user d = cl entContext.user d,
      guest d = cl entContext.guest d,
      app d = cl entContext.app d,
       pAddress = cl entContext. pAddress,
      userAgent = cl entContext.userAgent,
      countryCode = cl entContext.countryCode,
      languageCode = cl entContext.languageCode,
       sTwoff ce = cl entContext. sTwoff ce,
      userRoles = cl entContext.userRoles,
      dev ce d = cl entContext.dev ce d,
      mob leDev ce d = cl entContext.mob leDev ce d,
      mob leDev ceAd d = cl entContext.mob leDev ceAd d,
      l m AdTrack ng = cl entContext.l m AdTrack ng,
      guest dAds = cl entContext.guest dAds,
      guest dMarket ng = cl entContext.guest dMarket ng
    )
  }
}
