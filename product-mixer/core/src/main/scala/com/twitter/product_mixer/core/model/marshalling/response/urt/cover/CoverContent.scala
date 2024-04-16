package com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.D sm ss nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageVar ant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText

sealed tra  CoverContent

case class FullCoverContent(
  d splayType: FullCoverD splayType,
  pr maryText: R chText,
  pr maryCoverCta: CoverCta,
  secondaryCoverCta: Opt on[CoverCta],
  secondaryText: Opt on[R chText],
   mageVar ant: Opt on[ mageVar ant],
  deta ls: Opt on[R chText],
  d sm ss nfo: Opt on[D sm ss nfo],
   mageD splayType: Opt on[ mageD splayType],
   mpress onCallbacks: Opt on[L st[Callback]])
    extends CoverContent

case class HalfCoverContent(
  d splayType: HalfCoverD splayType,
  pr maryText: R chText,
  pr maryCoverCta: CoverCta,
  secondaryCoverCta: Opt on[CoverCta],
  secondaryText: Opt on[R chText],
   mpress onCallbacks: Opt on[L st[Callback]],
  d sm ss ble: Opt on[Boolean],
  cover mage: Opt on[Cover mage],
  d sm ss nfo: Opt on[D sm ss nfo])
    extends CoverContent
