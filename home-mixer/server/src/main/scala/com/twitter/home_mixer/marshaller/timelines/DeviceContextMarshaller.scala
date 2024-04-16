package com.tw ter.ho _m xer.marshaller.t  l nes

 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.t  l neserv ce.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Dev ceContextMarshaller @ nject() () {

  def apply(dev ceContext: Dev ceContext, cl entContext: Cl entContext): t.Dev ceContext = {
    t.Dev ceContext(
      countryCode = cl entContext.countryCode,
      languageCode = cl entContext.languageCode,
      cl entApp d = cl entContext.app d,
       pAddress = cl entContext. pAddress,
      guest d = cl entContext.guest d,
      userAgent = cl entContext.userAgent,
      dev ce d = cl entContext.dev ce d,
       sPoll ng = dev ceContext. sPoll ng,
      requestContext = dev ceContext.requestContext,
      referrer = None,
      tfeAuth ader = None,
      mob leDev ce d = cl entContext.mob leDev ce d,
       sSess onStart = None,
      latestControlAva lable = dev ceContext.latestControlAva lable,
      guest dMarket ng = cl entContext.guest dMarket ng,
       s nternalOrTwoff ce = cl entContext. sTwoff ce,
      guest dAds = cl entContext.guest dAds,
       sUrtRequest = So (true)
    )
  }
}
