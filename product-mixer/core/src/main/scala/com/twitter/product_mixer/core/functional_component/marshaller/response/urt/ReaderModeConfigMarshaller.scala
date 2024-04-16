package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ReaderModeConf g
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ReaderModeConf gMarshaller @ nject() (urlMarshaller: UrlMarshaller) {

  def apply(readerModeConf g: ReaderModeConf g): urt.ReaderModeConf g = urt.ReaderModeConf g(
     sReaderModeAva lable = readerModeConf g. sReaderModeAva lable,
    land ngUrl = urlMarshaller(readerModeConf g.land ngUrl)
  )

}
