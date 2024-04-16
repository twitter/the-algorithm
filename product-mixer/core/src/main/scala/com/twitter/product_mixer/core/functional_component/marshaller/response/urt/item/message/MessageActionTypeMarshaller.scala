package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage.FollowAll ssageAct onType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageAct onType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  ssageAct onTypeMarshaller @ nject() () {

  def apply( ssageAct onType:  ssageAct onType): urt. ssageAct onType =  ssageAct onType match {
    case FollowAll ssageAct onType => urt. ssageAct onType.FollowAll
  }
}
