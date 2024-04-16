package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.conversat on_annotat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.conversat on_annotat on.Conversat onAnnotat onType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.conversat on_annotat on.Large
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.conversat on_annotat on.Pol  cal

 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Conversat onAnnotat onTypeMarshaller @ nject() () {

  def apply(
    conversat onAnnotat onType: Conversat onAnnotat onType
  ): urt.Conversat onAnnotat onType = conversat onAnnotat onType match {
    case Large => urt.Conversat onAnnotat onType.Large
    case Pol  cal => urt.Conversat onAnnotat onType.Pol  cal
  }
}
