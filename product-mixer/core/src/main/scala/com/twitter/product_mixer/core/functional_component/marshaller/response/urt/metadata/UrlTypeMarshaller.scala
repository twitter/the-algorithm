package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.DeepL nk
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.ExternalUrl
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.UrlType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.UrtEndpo nt
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UrlTypeMarshaller @ nject() () {

  def apply(urlType: UrlType): urt.UrlType = urlType match {
    case ExternalUrl => urt.UrlType.ExternalUrl
    case DeepL nk => urt.UrlType.DeepL nk
    case UrtEndpo nt => urt.UrlType.UrtEndpo nt
  }
}
