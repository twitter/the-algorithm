package com.tw ter.v s b l y.conf gap 

 mport com.tw ter.t  l nes.conf gap ._

case class V s b l yRequestContext(
  user d: Opt on[Long],
  guest d: Opt on[Long],
  exper  ntContext: Exper  ntContext = NullExper  ntContext,
  featureContext: FeatureContext = NullFeatureContext)
    extends BaseRequestContext
    w h W hUser d
    w h W hGuest d
    w h W hExper  ntContext
    w h W hFeatureContext
