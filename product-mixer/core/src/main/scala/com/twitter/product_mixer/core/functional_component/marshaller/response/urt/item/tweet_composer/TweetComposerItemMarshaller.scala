package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t et_composer

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et_composer.T etComposer em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etComposer emMarshaller @ nject() (
  t etComposerD splayTypeMarshaller: T etComposerD splayTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(t etComposer: T etComposer em): urt.T  l ne emContent =
    urt.T  l ne emContent.T etComposer(
      urt.T etComposer(
        d splayType = t etComposerD splayTypeMarshaller(t etComposer.d splayType),
        text = t etComposer.text,
        url = urlMarshaller(t etComposer.url)
      )
    )
}
