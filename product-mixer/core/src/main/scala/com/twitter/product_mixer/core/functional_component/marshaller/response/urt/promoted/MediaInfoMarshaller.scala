package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted. d a nfo
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  d a nfoMarshaller @ nject() (
  callToAct onMarshaller: CallToAct onMarshaller,
  v deoVar antsMarshaller: V deoVar antsMarshaller) {
  def apply( d a nfo:  d a nfo): urt. d a nfo = {
    urt. d a nfo(
      uu d =  d a nfo.uu d,
      publ s r d =  d a nfo.publ s r d,
      callToAct on =  d a nfo.callToAct on.map(callToAct onMarshaller(_)),
      durat onM ll s =  d a nfo.durat onM ll s,
      v deoVar ants =  d a nfo.v deoVar ants.map(v deoVar antsMarshaller(_)),
      advert serNa  =  d a nfo.advert serNa ,
      renderAdByAdvert serNa  =  d a nfo.renderAdByAdvert serNa ,
      advert serProf le mageUrl =  d a nfo.advert serProf le mageUrl
    )
  }
}
