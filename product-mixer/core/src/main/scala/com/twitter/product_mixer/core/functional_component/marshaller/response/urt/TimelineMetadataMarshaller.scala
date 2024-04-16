package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne tadata
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l ne tadataMarshaller @ nject() (
  t  l neScr beConf gMarshaller: T  l neScr beConf gMarshaller,
  readerModeConf gMarshaller: ReaderModeConf gMarshaller) {

  def apply(t  l ne tadata: T  l ne tadata): urt.T  l ne tadata = urt.T  l ne tadata(
    t le = t  l ne tadata.t le,
    scr beConf g = t  l ne tadata.scr beConf g.map(t  l neScr beConf gMarshaller(_)),
    readerModeConf g = t  l ne tadata.readerModeConf g.map(readerModeConf gMarshaller(_))
  )
}
