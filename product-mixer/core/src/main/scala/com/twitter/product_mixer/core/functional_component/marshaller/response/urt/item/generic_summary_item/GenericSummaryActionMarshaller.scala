package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.gener c_summary_ em

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.gener c_summary.Gener cSummaryAct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Gener cSummaryAct onMarshaller @ nject() (
  urlMarshaller: UrlMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller) {

  def apply(gener cSummary emAct on: Gener cSummaryAct on): urt.Gener cSummaryAct on =
    urt.Gener cSummaryAct on(
      url = urlMarshaller(gener cSummary emAct on.url),
      cl entEvent nfo = gener cSummary emAct on.cl entEvent nfo.map(cl entEvent nfoMarshaller(_))
    )
}
