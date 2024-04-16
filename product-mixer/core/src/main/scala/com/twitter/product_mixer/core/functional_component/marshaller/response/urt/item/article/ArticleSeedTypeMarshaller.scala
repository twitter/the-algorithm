package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.art cle

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Art cleSeedType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Follow ngL stSeed
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.Fr endsOfFr endsSeed
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.art cle.L st dSeed
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Art cleSeedTypeMarshaller @ nject() () {

  def apply(art cleSeedType: Art cleSeedType): urt.Art cleSeedType =
    art cleSeedType match {
      case Follow ngL stSeed => urt.Art cleSeedType.Follow ngL st
      case Fr endsOfFr endsSeed => urt.Art cleSeedType.Fr endsOfFr ends
      case L st dSeed => urt.Art cleSeedType.L st d
    }
}
