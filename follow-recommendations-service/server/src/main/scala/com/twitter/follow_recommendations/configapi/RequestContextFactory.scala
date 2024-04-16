package com.tw ter.follow_recom ndat ons.conf gap 

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.google. nject. nject
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.featuresw c s.{Rec p ent => FeatureSw chRec p ent}
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t  l nes.conf gap .FeatureContext
 mport com.tw ter.t  l nes.conf gap .FeatureValue
 mport com.tw ter.t  l nes.conf gap .ForcedFeatureContext
 mport com.tw ter.t  l nes.conf gap .OrElseFeatureContext
 mport com.tw ter.t  l nes.conf gap .featuresw c s.v2.FeatureSw chResultsFeatureContext
 mport javax. nject.S ngleton

/*
 * Request Context Factory  s used to bu ld RequestContext objects wh ch are used
 * by t  conf g ap  to determ ne t  param overr des to apply to t  request.
 * T  param overr des are determ ned per request by conf gs wh ch spec fy wh ch
 * FS/Dec ders/AB translate to what param overr des.
 */
@S ngleton
class RequestContextFactory @ nject() (featureSw c s: FeatureSw c s, dec der: Dec der) {
  def apply(
    cl entContext: Cl entContext,
    d splayLocat on: D splayLocat on,
    featureOverr des: Map[Str ng, FeatureValue]
  ): RequestContext = {
    val featureContext = getFeatureContext(cl entContext, d splayLocat on, featureOverr des)
    RequestContext(cl entContext.user d, cl entContext.guest d, featureContext)
  }

  pr vate[conf gap ] def getFeatureContext(
    cl entContext: Cl entContext,
    d splayLocat on: D splayLocat on,
    featureOverr des: Map[Str ng, FeatureValue]
  ): FeatureContext = {
    val rec p ent =
      getFeatureSw chRec p ent(cl entContext)
        .w hCustomF elds("d splay_locat on" -> d splayLocat on.toFsNa )

    // userAgeOpt  s go ng to be set to None for logged out users and defaulted to So ( nt.MaxValue) for non-snowflake users
    val userAgeOpt = cl entContext.user d.map { user d =>
      Snowflake d.t  From dOpt(user d).map(_.unt lNow. nDays).getOrElse( nt.MaxValue)
    }
    val rec p entW hAccountAge =
      userAgeOpt
        .map(age => rec p ent.w hCustomF elds("account_age_ n_days" -> age)).getOrElse(rec p ent)

    val results = featureSw c s.matchRec p ent(rec p entW hAccountAge)
    OrElseFeatureContext(
      ForcedFeatureContext(featureOverr des),
      new FeatureSw chResultsFeatureContext(results))
  }

  @V s bleForTest ng
  pr vate[conf gap ] def getFeatureSw chRec p ent(
    cl entContext: Cl entContext
  ): FeatureSw chRec p ent = {
    FeatureSw chRec p ent(
      user d = cl entContext.user d,
      userRoles = cl entContext.userRoles,
      dev ce d = cl entContext.dev ce d,
      guest d = cl entContext.guest d,
      languageCode = cl entContext.languageCode,
      countryCode = cl entContext.countryCode,
       sVer f ed = None,
      cl entAppl cat on d = cl entContext.app d,
       sTwoff ce = cl entContext. sTwoff ce
    )
  }
}
