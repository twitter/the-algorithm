package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UrlMarshaller @ nject() (
  urlTypeMarshaller: UrlTypeMarshaller,
  urtEndpo ntOpt onsMarshaller: UrtEndpo ntOpt onsMarshaller) {

  def apply(url: Url): urt.Url = urt.Url(
    urlType = urlTypeMarshaller(url.urlType),
    url = url.url,
    urtEndpo ntOpt ons = url.urtEndpo ntOpt ons.map(urtEndpo ntOpt onsMarshaller(_))
  )
}
