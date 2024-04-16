package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.alert

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.Nav gate
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.NewT ets
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ShowAlertTypeMarshaller @ nject() () {

  def apply(alertType: ShowAlertType): urt.AlertType = alertType match {
    case NewT ets => urt.AlertType.NewT ets
    case Nav gate => urt.AlertType.Nav gate
  }
}
