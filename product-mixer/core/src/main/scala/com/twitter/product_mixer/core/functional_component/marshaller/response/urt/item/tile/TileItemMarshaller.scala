package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t le

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageVar antMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.T le em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T le emMarshaller @ nject() (
  t leContentMarshaller: T leContentMarshaller,
  urlMarshaller: UrlMarshaller,
   mageVar antMarshaller:  mageVar antMarshaller) {

  def apply(t le em: T le em): urt.T  l ne emContent = {
    urt.T  l ne emContent.T le(
      urt.T le(
        t le = t le em.t le,
        support ngText = t le em.support ngText,
        url = t le em.url.map(urlMarshaller(_)),
         mage = t le em. mage.map( mageVar antMarshaller(_)),
        badge = None,
        content = t leContentMarshaller(t le em.content)
      )
    )
  }
}
