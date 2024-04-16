package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D rectSponsorsh pType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted. nd rectSponsorsh pType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.NoSponsorsh pSponsorsh pType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Sponsorsh pType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Sponsorsh pTypeMarshaller @ nject() () {

  def apply(sponsorsh pType: Sponsorsh pType): urt.Sponsorsh pType = sponsorsh pType match {
    case D rectSponsorsh pType => urt.Sponsorsh pType.D rect
    case  nd rectSponsorsh pType => urt.Sponsorsh pType. nd rect
    case NoSponsorsh pSponsorsh pType => urt.Sponsorsh pType.NoSponsorsh p
  }
}
