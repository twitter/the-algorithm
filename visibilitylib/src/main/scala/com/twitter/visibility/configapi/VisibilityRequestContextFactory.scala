package com.tw ter.v s b l y.conf gap 

 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.featuresw c s.FSRec p ent
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.t  l nes.conf gap .abdec der.UserRec p entExper  ntContextFactory
 mport com.tw ter.t  l nes.conf gap .featuresw c s.v2.FeatureSw chResultsFeatureContext
 mport com.tw ter.t  l nes.conf gap .FeatureContext
 mport com.tw ter.t  l nes.conf gap .NullExper  ntContext
 mport com.tw ter.t  l nes.conf gap .UseFeatureContextExper  ntContext
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.Un OfD vers on
 mport com.tw ter.v s b l y.models.V e rContext

class V s b l yRequestContextFactory(
  logg ngABDec der: Logg ngABDec der,
  featureSw c s: FeatureSw c s) {
  pr vate val userExper  ntContextFactory = new UserRec p entExper  ntContextFactory(
    logg ngABDec der
  )
  pr vate[t ] def getFeatureContext(
    context: V e rContext,
    safetyLevel: SafetyLevel,
    un sOfD vers on: Seq[Un OfD vers on]
  ): FeatureContext = {
    val uodCustomF elds = un sOfD vers on.map(_.apply)
    val rec p ent = FSRec p ent(
      user d = context.user d,
      guest d = context.guest d,
      userAgent = context.fsUserAgent,
      cl entAppl cat on d = context.cl entAppl cat on d,
      countryCode = context.requestCountryCode,
      dev ce d = context.dev ce d,
      languageCode = context.requestLanguageCode,
       sTwoff ce = So (context. sTwOff ce),
      userRoles = context.userRoles,
    ).w hCustomF elds(("safety_level", safetyLevel.na ), uodCustomF elds: _*)

    val results = featureSw c s.matchRec p ent(rec p ent)
    new FeatureSw chResultsFeatureContext(results)
  }

  def apply(
    context: V e rContext,
    safetyLevel: SafetyLevel,
    un sOfD vers on: Seq[Un OfD vers on] = Seq.empty
  ): V s b l yRequestContext = {
    val exper  ntContextBase =
      context.user d
        .map(user d => userExper  ntContextFactory.apply(user d)).getOrElse(NullExper  ntContext)

    val featureContext = getFeatureContext(context, safetyLevel, un sOfD vers on)

    val exper  ntContext =
      UseFeatureContextExper  ntContext(exper  ntContextBase, featureContext)

    V s b l yRequestContext(
      context.user d,
      context.guest d,
      exper  ntContext,
      featureContext
    )
  }
}
