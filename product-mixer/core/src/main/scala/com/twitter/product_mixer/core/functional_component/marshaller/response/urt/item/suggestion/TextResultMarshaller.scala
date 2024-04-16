package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.suggest on

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.h ghl ght.H ghl ghtedSect onMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.h ghl ght.H ghl ghtedSect on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.suggest on.TextResult
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class TextResultMarshaller @ nject() (h ghl ghtedSect onMarshaller: H ghl ghtedSect onMarshaller) {

  def apply(textResult: TextResult): urt.TextResult = {
    val h H ghl ghts = textResult.h H ghl ghts.map {
      h ghl ghtedSect ons: Seq[H ghl ghtedSect on] =>
        h ghl ghtedSect ons.map(h ghl ghtedSect onMarshaller(_))
    }

    urt.TextResult(
      text = textResult.text,
      h H ghl ghts = h H ghl ghts,
      score = textResult.score,
      queryS ce = textResult.queryS ce)
  }
}
