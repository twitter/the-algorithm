package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.tombstone

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tombstone.Tombstone nfo
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Tombstone nfoMarshaller @ nject() (
  r chTextMarshaller: R chTextMarshaller) {

  def apply(tombstone nfo: Tombstone nfo): urt.Tombstone nfo = urt.Tombstone nfo(
    text = tombstone nfo.text,
    r chText = tombstone nfo.r chText.map(r chTextMarshaller(_)),
    r chRevealText = tombstone nfo.r chRevealText.map(r chTextMarshaller(_))
  )
}
