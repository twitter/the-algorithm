package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Cl ckTrack ng nfo
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cl ckTrack ng nfoMarshaller @ nject() (
  urlOverr deTypeMarshaller: UrlOverr deTypeMarshaller) {

  def apply(cl ckTrack ng nfo: Cl ckTrack ng nfo): urt.Cl ckTrack ng nfo =
    urt.Cl ckTrack ng nfo(
      urlParams = cl ckTrack ng nfo.urlParams,
      urlOverr de = cl ckTrack ng nfo.urlOverr de,
      urlOverr deType = cl ckTrack ng nfo.urlOverr deType.map(urlOverr deTypeMarshaller(_))
    )
}
