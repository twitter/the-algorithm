package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.T  l neScr beConf gMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Seg ntedT  l ne
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Seg ntedT  l neMarshaller @ nject() (
  t  l neKeyMarshaller: T  l neKeyMarshaller,
  t  l neScr beConf gMarshaller: T  l neScr beConf gMarshaller) {

  def apply(seg ntedT  l ne: Seg ntedT  l ne): urp.Seg ntedT  l ne = urp.Seg ntedT  l ne(
     d = seg ntedT  l ne. d,
    labelText = seg ntedT  l ne.labelText,
    t  l ne = t  l neKeyMarshaller(seg ntedT  l ne.t  l ne),
    scr beConf g = seg ntedT  l ne.scr beConf g.map(t  l neScr beConf gMarshaller(_)),
    refresh ntervalSec = seg ntedT  l ne.refresh ntervalSec
  )
}
