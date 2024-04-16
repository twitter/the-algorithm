package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t et

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T  l nesScore nfo
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l nesScore nfoMarshaller @ nject() () {

  def apply(t  l nesScore nfo: T  l nesScore nfo): urt.T  l nesScore nfo =
    urt.T  l nesScore nfo(score = t  l nesScore nfo.score)
}
