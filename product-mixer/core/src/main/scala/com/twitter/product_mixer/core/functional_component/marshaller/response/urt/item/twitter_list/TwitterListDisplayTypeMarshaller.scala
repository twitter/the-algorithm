package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.tw ter_l st

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.L st
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.L stT le
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.L stW hP n
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.L stW hSubscr be
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.tw ter_l st.Tw terL stD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Tw terL stD splayTypeMarshaller @ nject() () {

  def apply(tw terL stD splayType: Tw terL stD splayType): urt.Tw terL stD splayType =
    tw terL stD splayType match {
      case L st => urt.Tw terL stD splayType.L st
      case L stT le => urt.Tw terL stD splayType.L stT le
      case L stW hP n => urt.Tw terL stD splayType.L stW hP n
      case L stW hSubscr be => urt.Tw terL stD splayType.L stW hSubscr be
    }
}
