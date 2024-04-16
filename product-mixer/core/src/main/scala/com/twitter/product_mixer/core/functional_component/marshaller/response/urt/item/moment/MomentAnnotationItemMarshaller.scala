package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.mo nt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.mo nt.Mo ntAnnotat on em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Mo ntAnnotat on emMarshaller @ nject() (r chTextMarshaller: R chTextMarshaller) {
  def apply(mo ntAnnotat on em: Mo ntAnnotat on em): urt.T  l ne emContent = {
    urt.T  l ne emContent.Mo ntAnnotat on(
      urt.Mo ntAnnotat on(
        annotat on d = mo ntAnnotat on em. d,
        text = mo ntAnnotat on em.text.map(r chTextMarshaller(_)),
         ader = mo ntAnnotat on em. ader.map(r chTextMarshaller(_)),
      )
    )
  }
}
