package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.suggest on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.h ghl ght.H ghl ghtedSect on

/**
 * Represents text w h h -h ghl ghts used for return ng search query suggest ons.
 *
 * URT AP  Reference: https://docb rd.tw ter.b z/un f ed_r ch_t  l nes_urt/gen/com/tw ter/t  l nes/render/thr ftscala/TextResult.html
 */
case class TextResult(
  text: Str ng,
  h H ghl ghts: Opt on[Seq[H ghl ghtedSect on]],
  score: Opt on[Double],
  queryS ce: Opt on[Str ng])
