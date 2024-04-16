package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.conversat on_annotat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.conversat on_annotat on.Conversat onAnnotat on
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext.R chTextMarshaller
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Conversat onAnnotat onMarshaller @ nject() (
  conversat onAnnotat onTypeMarshaller: Conversat onAnnotat onTypeMarshaller,
  r chTextMarshaller: R chTextMarshaller) {

  def apply(conversat onAnnotat on: Conversat onAnnotat on): urt.Conversat onAnnotat on = {
    urt.Conversat onAnnotat on(
      conversat onAnnotat onType =
        conversat onAnnotat onTypeMarshaller(conversat onAnnotat on.conversat onAnnotat onType),
       ader = conversat onAnnotat on. ader.map(r chTextMarshaller(_)),
      descr pt on = conversat onAnnotat on.descr pt on.map(r chTextMarshaller(_))
    )
  }
}
