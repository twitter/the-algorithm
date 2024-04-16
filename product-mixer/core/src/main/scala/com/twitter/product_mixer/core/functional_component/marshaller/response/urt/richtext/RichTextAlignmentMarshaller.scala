package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.r chtext

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Center
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.Natural
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextAl gn nt
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class R chTextAl gn ntMarshaller @ nject() () {

  def apply(al gn nt: R chTextAl gn nt): urt.R chTextAl gn nt = al gn nt match {
    case Natural => urt.R chTextAl gn nt.Natural
    case Center => urt.R chTextAl gn nt.Center
  }
}
