package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.alert

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.color.RosettaColorMarshaller
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlertColorConf gurat on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}

@S ngleton
class ShowAlertColorConf gurat onMarshaller @ nject() (
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(colorConf gurat on: ShowAlertColorConf gurat on): urt.ShowAlertColorConf gurat on =
    urt.ShowAlertColorConf gurat on(
      background = rosettaColorMarshaller(colorConf gurat on.background),
      text = rosettaColorMarshaller(colorConf gurat on.text),
      border = colorConf gurat on.border.map(rosettaColorMarshaller(_)),
    )
}
