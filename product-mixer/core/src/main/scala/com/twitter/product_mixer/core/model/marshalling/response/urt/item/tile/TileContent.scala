package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.CtaButton
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Badge
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText

sealed tra  T leContent

case class StandardT leContent(
  t le: Str ng,
  support ngText: Str ng,
  badge: Opt on[Badge])
    extends T leContent

case class CallToAct onT leContent(
  text: Str ng,
  r chText: Opt on[R chText],
  ctaButton: Opt on[CtaButton])
    extends T leContent

//todo: Add ot r T leContent types later
