package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.top c

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top c em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top c emMarshaller @ nject() (
  d splayTypeMarshaller: Top cD splayTypeMarshaller,
  funct onal yTypeMarshaller: Top cFunct onal yTypeMarshaller) {

  def apply(top c em: Top c em): urt.T  l ne emContent = {
    urt.T  l ne emContent.Top c(
      urt.Top c(
        top c d = top c em. d.toStr ng,
        top cD splayType = top c em.top cD splayType
          .map(d splayTypeMarshaller(_)).getOrElse(urt.Top cD splayType.Bas c),
        top cFunct onal yType = top c em.top cFunct onal yType
          .map(funct onal yTypeMarshaller(_)).getOrElse(urt.Top cFunct onal yType.Bas c),
        // T   s currently not requ red by users of t  l brary
        react veTr ggers = None
      )
    )
  }
}
