package com.tw ter.product_m xer.core.funct onal_component.conf gap 

 mport com.tw ter.t  l nes.conf gap .BaseRequestContext
 mport com.tw ter.t  l nes.conf gap .FeatureContext
 mport com.tw ter.t  l nes.conf gap .Guest d
 mport com.tw ter.t  l nes.conf gap .User d
 mport com.tw ter.t  l nes.conf gap .W hFeatureContext
 mport com.tw ter.t  l nes.conf gap .W hGuest d
 mport com.tw ter.t  l nes.conf gap .W hUser d

/** Represents [[com.tw ter.t  l nes.conf gap ]]'s context  nformat on */
case class RequestContext(
  user d: Opt on[User d],
  guest d: Opt on[Guest d],
  featureContext: FeatureContext)
    extends BaseRequestContext
    w h W hUser d
    w h W hGuest d
    w h W hFeatureContext
