package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.operat on.CursorOperat onMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neOperat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l neOperat onMarshaller @ nject() (
  cursorOperat onMarshaller: CursorOperat onMarshaller) {

  def apply(operat on: T  l neOperat on): urt.T  l neOperat on = operat on match {
    case cursorOperat on: CursorOperat on => cursorOperat onMarshaller(cursorOperat on)
    case _ =>
      throw new UnsupportedT  l neOperat onExcept on(operat on)
  }
}

class UnsupportedT  l neOperat onExcept on(operat on: T  l neOperat on)
    extends UnsupportedOperat onExcept on(
      "Unsupported t  l ne operat on " + TransportMarshaller.getS mpleNa (operat on.getClass))
