package com.tw ter.follow_recom ndat ons.conf gap 

 mport com.tw ter.t  l nes.conf gap .BaseRequestContext
 mport com.tw ter.t  l nes.conf gap .FeatureContext
 mport com.tw ter.t  l nes.conf gap .NullFeatureContext
 mport com.tw ter.t  l nes.conf gap .Guest d
 mport com.tw ter.t  l nes.conf gap .User d
 mport com.tw ter.t  l nes.conf gap .W hFeatureContext
 mport com.tw ter.t  l nes.conf gap .W hGuest d
 mport com.tw ter.t  l nes.conf gap .W hUser d

case class RequestContext(
  user d: Opt on[User d],
  guest d: Opt on[Guest d],
  featureContext: FeatureContext = NullFeatureContext)
    extends BaseRequestContext
    w h W hUser d
    w h W hGuest d
    w h W hFeatureContext
