package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cl entEventDeta lsMarshaller @ nject() (
  conversat onDeta lsMarshaller: Conversat onDeta lsMarshaller,
  t  l nesDeta lsMarshaller: T  l nesDeta lsMarshaller,
  art cleDeta lsMarshaller: Art cleDeta lsMarshaller,
  l veEventDeta lsMarshaller: L veEventDeta lsMarshaller,
  com rceDeta lsMarshaller: Com rceDeta lsMarshaller) {

  def apply(cl entEventDeta ls: Cl entEventDeta ls): urt.Cl entEventDeta ls = {
    urt.Cl entEventDeta ls(
      conversat onDeta ls =
        cl entEventDeta ls.conversat onDeta ls.map(conversat onDeta lsMarshaller(_)),
      t  l nesDeta ls = cl entEventDeta ls.t  l nesDeta ls.map(t  l nesDeta lsMarshaller(_)),
      art cleDeta ls = cl entEventDeta ls.art cleDeta ls.map(art cleDeta lsMarshaller(_)),
      l veEventDeta ls = cl entEventDeta ls.l veEventDeta ls.map(l veEventDeta lsMarshaller(_)),
      com rceDeta ls = cl entEventDeta ls.com rceDeta ls.map(com rceDeta lsMarshaller(_))
    )
  }
}
