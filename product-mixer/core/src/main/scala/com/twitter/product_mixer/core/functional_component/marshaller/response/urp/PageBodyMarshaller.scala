package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.PageBody
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Seg ntedT  l nesPageBody
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.T  l neKeyPageBody
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PageBodyMarshaller @ nject() (
  t  l neKeyMarshaller: T  l neKeyMarshaller,
  seg ntedT  l nesMarshaller: Seg ntedT  l nesMarshaller) {

  def apply(pageBody: PageBody): urp.PageBody = pageBody match {
    case pageBody: T  l neKeyPageBody =>
      urp.PageBody.T  l ne(t  l neKeyMarshaller(pageBody.t  l ne))
    case pageBody: Seg ntedT  l nesPageBody =>
      urp.PageBody.Seg ntedT  l nes(seg ntedT  l nesMarshaller(pageBody))
  }
}
