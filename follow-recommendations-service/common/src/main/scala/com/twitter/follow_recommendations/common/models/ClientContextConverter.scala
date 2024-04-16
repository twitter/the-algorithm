package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => frs}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext

object Cl entContextConverter {
  def toFRSOffl neCl entContextThr ft(
    productM xerCl entContext: Cl entContext
  ): offl ne.Offl neCl entContext =
    offl ne.Offl neCl entContext(
      productM xerCl entContext.user d,
      productM xerCl entContext.guest d,
      productM xerCl entContext.app d,
      productM xerCl entContext.countryCode,
      productM xerCl entContext.languageCode,
      productM xerCl entContext.guest dAds,
      productM xerCl entContext.guest dMarket ng
    )

  def fromThr ft(cl entContext: frs.Cl entContext): Cl entContext = Cl entContext(
    user d = cl entContext.user d,
    guest d = cl entContext.guest d,
    app d = cl entContext.app d,
     pAddress = cl entContext. pAddress,
    userAgent = cl entContext.userAgent,
    countryCode = cl entContext.countryCode,
    languageCode = cl entContext.languageCode,
     sTwoff ce = cl entContext. sTwoff ce,
    userRoles = cl entContext.userRoles.map(_.toSet),
    dev ce d = cl entContext.dev ce d,
    guest dAds = cl entContext.guest dAds,
    guest dMarket ng = cl entContext.guest dMarket ng,
    mob leDev ce d = None,
    mob leDev ceAd d = None,
    l m AdTrack ng = None
  )

  def toThr ft(cl entContext: Cl entContext): frs.Cl entContext = frs.Cl entContext(
    user d = cl entContext.user d,
    guest d = cl entContext.guest dAds,
    app d = cl entContext.app d,
     pAddress = cl entContext. pAddress,
    userAgent = cl entContext.userAgent,
    countryCode = cl entContext.countryCode,
    languageCode = cl entContext.languageCode,
     sTwoff ce = cl entContext. sTwoff ce,
    userRoles = cl entContext.userRoles,
    dev ce d = cl entContext.dev ce d,
    guest dAds = cl entContext.guest dAds,
    guest dMarket ng = cl entContext.guest dMarket ng
  )
}
