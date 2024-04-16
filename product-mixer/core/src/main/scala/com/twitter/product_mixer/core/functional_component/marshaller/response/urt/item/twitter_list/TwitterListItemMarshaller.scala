package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.tw ter_l st

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.Tw terL st em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Tw terL st emMarshaller @ nject() (
  tw terL stD splayTypeMarshaller: Tw terL stD splayTypeMarshaller) {

  def apply(tw terL st em: Tw terL st em): urt.T  l ne emContent =
    urt.T  l ne emContent.Tw terL st(
      urt.Tw terL st(
         d = tw terL st em. d,
        d splayType = tw terL st em.d splayType.map(tw terL stD splayTypeMarshaller(_))
      )
    )
}
