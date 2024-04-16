package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.forward_p vot

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.RosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Badge
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageVar ant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText

case class ForwardP vot(
  text: R chText,
  land ngUrl: Url,
  d splayType: ForwardP votD splayType,
   con mageVar ant: Opt on[ mageVar ant],
  stateBadge: Opt on[Badge],
  subtext: Opt on[R chText],
  backgroundColorNa : Opt on[RosettaColor],
  engage ntNudge: Opt on[Boolean],
  soft ntervent onD splayType: Opt on[Soft ntervent onD splayType])
