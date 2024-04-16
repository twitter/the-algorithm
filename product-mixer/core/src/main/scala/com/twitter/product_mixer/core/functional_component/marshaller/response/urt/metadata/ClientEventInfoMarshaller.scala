package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cl entEvent nfoMarshaller @ nject() (
  cl entEventDeta lsMarshaller: Cl entEventDeta lsMarshaller) {

  def apply(cl entEvent nfo: Cl entEvent nfo): urt.Cl entEvent nfo = {
    urt.Cl entEvent nfo(
      component = cl entEvent nfo.component,
      ele nt = cl entEvent nfo.ele nt,
      deta ls = cl entEvent nfo.deta ls.map(cl entEventDeta lsMarshaller(_)),
      act on = cl entEvent nfo.act on,
      ent yToken = cl entEvent nfo.ent yToken
    )
  }
}
