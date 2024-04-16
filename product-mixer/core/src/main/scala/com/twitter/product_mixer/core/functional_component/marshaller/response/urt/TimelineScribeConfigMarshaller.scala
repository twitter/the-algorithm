package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neScr beConf g
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l neScr beConf gMarshaller @ nject() () {

  def apply(t  l neScr beConf g: T  l neScr beConf g): urt.T  l neScr beConf g =
    urt.T  l neScr beConf g(
      page = t  l neScr beConf g.page,
      sect on = t  l neScr beConf g.sect on,
      ent yToken = t  l neScr beConf g.ent yToken
    )
}
