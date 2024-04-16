package com.tw ter.follow_recom ndat ons.conf gap .cand dates

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.google. nject. nject
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.featuresw c s.{Rec p ent => FeatureSw chRec p ent}
 mport com.tw ter.follow_recom ndat ons.common.constants.Gu ceNa dConstants.PRODUCER_S DE_FEATURE_SW TCHES
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.t  l nes.conf gap .FeatureContext
 mport com.tw ter.t  l nes.conf gap .featuresw c s.v2.FeatureSw chResultsFeatureContext
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
class Cand dateUserContextFactory @ nject() (
  @Na d(PRODUCER_S DE_FEATURE_SW TCHES) featureSw c s: FeatureSw c s,
  dec der: Dec der) {
  def apply(
    cand dateUser: Cand dateUser,
    d splayLocat on: D splayLocat on
  ): Cand dateUserContext = {
    val featureContext = getFeatureContext(cand dateUser, d splayLocat on)

    Cand dateUserContext(So (cand dateUser. d), featureContext)
  }

  pr vate[conf gap ] def getFeatureContext(
    cand dateUser: Cand dateUser,
    d splayLocat on: D splayLocat on
  ): FeatureContext = {

    val rec p ent = getFeatureSw chRec p ent(cand dateUser).w hCustomF elds(
      "d splay_locat on" -> d splayLocat on.toFsNa )
    new FeatureSw chResultsFeatureContext(featureSw c s.matchRec p ent(rec p ent))
  }

  @V s bleForTest ng
  pr vate[conf gap ] def getFeatureSw chRec p ent(
    cand dateUser: Cand dateUser
  ): FeatureSw chRec p ent = {
    FeatureSw chRec p ent(
      user d = So (cand dateUser. d),
      userRoles = None,
      dev ce d = None,
      guest d = None,
      languageCode = None,
      countryCode = None,
       sVer f ed = None,
      cl entAppl cat on d = None,
       sTwoff ce = None
    )
  }
}
