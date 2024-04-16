package com.tw ter.follow_recom ndat ons.conf gap .cand dates

 mport com.tw ter.t  l nes.conf gap .BaseRequestContext
 mport com.tw ter.t  l nes.conf gap .FeatureContext
 mport com.tw ter.t  l nes.conf gap .NullFeatureContext
 mport com.tw ter.t  l nes.conf gap .W hFeatureContext
 mport com.tw ter.t  l nes.conf gap .W hUser d

/**
 * represent t  context for a recom ndat on cand date (producer s de)
 * @param user d  d of t  recom nded user
 * @param featureContext feature context
 */
case class Cand dateUserContext(
  overr de val user d: Opt on[Long],
  featureContext: FeatureContext = NullFeatureContext)
    extends BaseRequestContext
    w h W hUser d
    w h W hFeatureContext
