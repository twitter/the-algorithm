package com.tw ter.product_m xer.core.funct onal_component.conf gap 

 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.featuresw c s.UserAgent
 mport com.tw ter.featuresw c s.{Rec p ent => FeatureSw chRec p ent}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.tw ter.t  l nes.conf gap .featuresw c s.v2.FeatureSw chResultsFeatureContext
 mport com.tw ter.t  l nes.conf gap .FeatureContext
 mport com.tw ter.t  l nes.conf gap .FeatureValue
 mport com.tw ter.t  l nes.conf gap .ForcedFeatureContext
 mport com.tw ter.t  l nes.conf gap .OrElseFeatureContext
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Request Context Factory  s used to bu ld RequestContext objects wh ch are used
 * by t  conf g ap  to determ ne t  param overr des to apply to t  request.
 * T  param overr des are determ ned per request by conf gs wh ch spec fy wh ch
 * FS/Dec ders/AB translate to what param overr des.
 */
@S ngleton
class RequestContextBu lder @ nject() (featureSw c s: FeatureSw c s) {

  /**
   * @param `fsCustomMap nput` allows   to set custom f elds on y  feature sw c s.
   * T  feature  sn't d rectly supported by product m xer yet, so us ng t  argu nt
   * w ll l kely result  n future cleanup work.
   *
   */
  def bu ld(
    cl entContext: Cl entContext,
    product: Product,
    featureOverr des: Map[Str ng, FeatureValue],
    fsCustomMap nput: Map[Str ng, Any]
  ): RequestContext = {
    val featureContext =
      getFeatureContext(cl entContext, product, featureOverr des, fsCustomMap nput)

    RequestContext(cl entContext.user d, cl entContext.guest d, featureContext)
  }

  pr vate[conf gap ] def getFeatureContext(
    cl entContext: Cl entContext,
    product: Product,
    featureOverr des: Map[Str ng, FeatureValue],
    fsCustomMap nput: Map[Str ng, Any]
  ): FeatureContext = {
    val rec p ent = getFeatureSw chRec p ent(cl entContext)
      .w hCustomF elds("product" -> product. dent f er.toStr ng, fsCustomMap nput.toSeq: _*)

    val results = featureSw c s.matchRec p ent(rec p ent)
    OrElseFeatureContext(
      ForcedFeatureContext(featureOverr des),
      new FeatureSw chResultsFeatureContext(results))
  }

  pr vate[conf gap ] def getFeatureSw chRec p ent(
    cl entContext: Cl entContext
  ): FeatureSw chRec p ent = FeatureSw chRec p ent(
    user d = cl entContext.user d,
    userRoles = cl entContext.userRoles,
    dev ce d = cl entContext.dev ce d,
    guest d = cl entContext.guest d,
    languageCode = cl entContext.languageCode,
    countryCode = cl entContext.countryCode,
    userAgent = cl entContext.userAgent.flatMap(UserAgent.apply),
     sVer f ed = None,
    cl entAppl cat on d = cl entContext.app d,
     sTwoff ce = cl entContext. sTwoff ce
  )
}
