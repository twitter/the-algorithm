package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContext
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class GeneralContextMarshaller @ nject() (
  generalContextTypeMarshaller: GeneralContextTypeMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(generalContext: GeneralContext): urt.Soc alContext = {
    urt.Soc alContext.GeneralContext(
      urt.GeneralContext(
        contextType = generalContextTypeMarshaller(generalContext.contextType),
        text = generalContext.text,
        url = generalContext.url,
        context mageUrls = generalContext.context mageUrls,
        land ngUrl = generalContext.land ngUrl.map(urlMarshaller(_))
      )
    )
  }
}
