package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.alert

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.DownArrow
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlert con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.UpArrow
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class ShowAlert conMarshaller @ nject() () {

  def apply(alert con: ShowAlert con): urt.ShowAlert con = alert con match {
    case UpArrow => urt.ShowAlert con.UpArrow
    case DownArrow => urt.ShowAlert con.DownArrow
  }
}
