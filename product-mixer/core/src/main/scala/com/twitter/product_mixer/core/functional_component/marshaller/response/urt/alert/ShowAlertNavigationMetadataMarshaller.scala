package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.alert

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertNav gat on tadata
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ShowAlertNav gat on tadataMarshaller @ nject() () {

  def apply(alertNav gat on tadata: ShowAlertNav gat on tadata): urt.ShowAlertNav gat on tadata =
    urt.ShowAlertNav gat on tadata(nav gateToEntry d =
      So (alertNav gat on tadata.nav gateToEntry d))
}
