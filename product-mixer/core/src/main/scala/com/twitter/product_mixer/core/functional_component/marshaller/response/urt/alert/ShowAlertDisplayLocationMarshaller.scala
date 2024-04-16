package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.alert

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertD splayLocat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.Top
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.Bottom
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class ShowAlertD splayLocat onMarshaller @ nject() () {

  def apply(alertD splayLocat on: ShowAlertD splayLocat on): urt.ShowAlertD splayLocat on =
    alertD splayLocat on match {
      case Top => urt.ShowAlertD splayLocat on.Top
      case Bottom => urt.ShowAlertD splayLocat on.Bottom
    }

}
