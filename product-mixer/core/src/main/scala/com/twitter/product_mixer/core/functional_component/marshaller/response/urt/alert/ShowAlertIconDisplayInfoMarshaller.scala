package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.alert

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.color.RosettaColorMarshaller
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.alert.ShowAlert conD splay nfo

@S ngleton
class ShowAlert conD splay nfoMarshaller @ nject() (
  showAlert conMarshaller: ShowAlert conMarshaller,
  rosettaColorMarshaller: RosettaColorMarshaller,
) {

  def apply(alert conD splay nfo: ShowAlert conD splay nfo): urt.ShowAlert conD splay nfo =
    urt.ShowAlert conD splay nfo(
       con = showAlert conMarshaller(alert conD splay nfo. con),
      t nt = rosettaColorMarshaller(alert conD splay nfo.t nt),
    )

}
