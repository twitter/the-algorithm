package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.t et_composer

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et_composer.Reply
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et_composer.T etComposerD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et_composer.T etComposerSelfThread
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T etComposerD splayTypeMarshaller @ nject() () {

  def apply(d splayType: T etComposerD splayType): urt.T etComposerD splayType =
    d splayType match {
      case T etComposerSelfThread => urt.T etComposerD splayType.SelfThread
      case Reply => urt.T etComposerD splayType.Reply
    }
}
